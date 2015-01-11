/*
 * Copyright 2014 Tagbangers, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wallride;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.ServletContextApplicationContextInitializer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponentsBuilder;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import org.wallride.config.CoreConfig;
import org.wallride.core.support.WallRideProperties;
import org.wallride.web.WebAdminConfig;
import org.wallride.web.WebGuestConfig;
import org.wallride.web.support.ExtendedUrlRewriteFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

@Configuration
@EnableConfigurationProperties(WallRideProperties.class)
@EnableAutoConfiguration(exclude = {
		DispatcherServletAutoConfiguration.class,
		WebMvcAutoConfiguration.class,
//		ThymeleafAutoConfiguration.class,
		BatchAutoConfiguration.class,
})
@ComponentScan(basePackageClasses = CoreConfig.class, includeFilters = @ComponentScan.Filter(Configuration.class))
public class Application extends SpringBootServletInitializer {

	public static final String WALLRIDE_HOME_PROPERTY = "wallride.home";

	public static final String GUEST_SERVLET_NAME = "guestServlet";
	public static final String GUEST_SERVLET_PATH = "";

	public static final String ADMIN_SERVLET_NAME = "adminServlet";
	public static final String ADMIN_SERVLET_PATH = "/_admin";

	public static void main(String[] args) throws Exception {
		ResourceLoader resourceLoader = createResourceLoader();
		String configLocation = UriComponentsBuilder.fromPath(System.getProperty(WALLRIDE_HOME_PROPERTY))
				.path(WallRideProperties.CONFIG_PATH)
				.buildAndExpand().toUriString();
		System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, configLocation);

		new SpringApplicationBuilder(Application.class)
				.contextClass(AnnotationConfigEmbeddedWebApplicationContext.class)
				.resourceLoader(resourceLoader)
				.run(args);
	}

	public static ResourceLoader createResourceLoader() {
		ClientConfiguration configuration = new ClientConfiguration();
		configuration.setMaxConnections(1000);
		AmazonS3 amazonS3 = new AmazonS3Client(configuration);

		SimpleStorageResourceLoader resourceLoader = new SimpleStorageResourceLoader(amazonS3);
		return new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, resourceLoader);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class)
				.resourceLoader(createResourceLoader());
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		SpringApplicationBuilder application = new SpringApplicationBuilder();
		application.initializers(new ServletContextApplicationContextInitializer(servletContext));
		application.contextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
		application = configure(application);
		// Ensure error pages are registered
//		application.sources(ErrorPageFilter.class);
		return (WebApplicationContext) application.run();
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setName("characterEncodingFilter");
		registration.setFilter(characterEncodingFilter);
		registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setName("hiddenHttpMethodFilter");
		registration.setFilter(hiddenHttpMethodFilter);
		registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
		registration.addUrlPatterns("/*");
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public FilterRegistrationBean urlRewriteFilter() {
		UrlRewriteFilter urlRewriteFilter = new ExtendedUrlRewriteFilter();

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setName("urlRewriteFilter");
		registration.setFilter(urlRewriteFilter);
		registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
		registration.addUrlPatterns("/*");
		registration.setOrder(3);
		registration.getInitParameters().put("confPath", "classpath:/urlrewrite.xml");
		return registration;
	}

	@Bean
	public ServletRegistrationBean registerAdminServlet() {
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		context.register(WebAdminConfig.class);

		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.setName(ADMIN_SERVLET_NAME);
		registration.setLoadOnStartup(1);
		registration.addUrlMappings(ADMIN_SERVLET_PATH + "/*");
		return registration;
	}

	@Bean
	public ServletRegistrationBean registerGuestServlet() {
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		context.register(WebGuestConfig.class);

		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		registration.setName(GUEST_SERVLET_NAME);
		registration.setLoadOnStartup(2);
		registration.addUrlMappings(GUEST_SERVLET_PATH + "/*");
		return registration;
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

//	public static class ExtendedAnnotationConfigEmbeddedWebApplicationContext extends AnnotationConfigEmbeddedWebApplicationContext {
//
//		@Override
//		public Resource getResource(String location) {
//			Assert.notNull(location, "Location must not be null");
//			if (location.startsWith(AmazonS3ResourceLoader.S3_URL_PREFIX)) {
//				String path = location.substring(AmazonS3ResourceLoader.S3_URL_PREFIX.length());
//				int pos = path.indexOf('/');
//				String bucketName = "";
//				String key = "";
//				if (pos != -1) {
//					bucketName = path.substring(0, pos);
//					key = path.substring(pos + 1);
//				} else {
//					bucketName = path;
//				}
//				return new AmazonS3Resource(getBean(AmazonS3Client.class), bucketName, key);
//			} else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
//				return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
//			} else {
//				try {
//					// Try to parse the location as a URL...
//					URL url = new URL(location);
//					return new UrlResource(url);
//				} catch (MalformedURLException ex) {
//					// No URL -> resolve as resource path.
//					return getResourceByPath(location);
//				}
//			}
//		}
//	}
}
