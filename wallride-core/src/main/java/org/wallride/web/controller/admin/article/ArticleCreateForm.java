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

package org.wallride.web.controller.admin.article;

import org.springframework.format.annotation.DateTimeFormat;
import org.wallride.core.domain.CustomField;
import org.wallride.core.model.ArticleCreateRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class ArticleCreateForm implements Serializable {

	public ArticleCreateForm() {
	}

	public ArticleCreateForm(List<CustomField> customFields) {
		for (CustomField field : customFields) {
			CustomFieldValueEditForm valueForm = new CustomFieldValueEditForm();
			valueForm.setCustomFieldId(field.getId());
			valueForm.setName(field.getName());
			valueForm.setDescription(field.getDescription());
			valueForm.setFieldType(field.getFieldType());
			valueForm.setOptions(field.getOptions());
			customFieldValues.add(valueForm);
		}
	}

	interface GroupPublish {}

	private String code;

	private String coverId;

	@NotNull(groups=GroupPublish.class)
	private String title;

	@NotNull(groups=GroupPublish.class)
	private String body;

	private Long authorId;

//	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm")
	private LocalDateTime date;

	private Set<Long> categoryIds = new HashSet<>();
	private String tags;
	private Set<Long> relatedPostIds = new HashSet<>();

	private String seoTitle;
	private String seoDescription;
	private String seoKeywords;

	private Set<CustomFieldValueEditForm> customFieldValues = new HashSet<>();

	@NotNull
	private String language;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCoverId() {
		return coverId;
	}

	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Set<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Set<Long> getRelatedPostIds() {
		return relatedPostIds;
	}

	public void setRelatedPostIds(Set<Long> relatedPostIds) {
		this.relatedPostIds = relatedPostIds;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public Set<CustomFieldValueEditForm> getCustomFieldValues() {
		return customFieldValues;
	}

	public void setCustomFieldValues(Set<CustomFieldValueEditForm> customFieldValues) {
		this.customFieldValues = customFieldValues;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ArticleCreateRequest buildArticleCreateRequest() {
//		Set<CustomFieldValue> customFieldValues_ = null;
//		if (!CollectionUtils.isEmpty(customFieldValues)) {
//			customFieldValues_ = new HashSet<>();
//			for (CustomFieldValueForm valueForm : customFieldValues) {
//				CustomFieldValue value = new CustomFieldValue();
//				value.setCustomFieldId(valueForm.getCustomFieldId());
//				value.setStringValue(valueForm.getStringValue());
//				value.setNumberValue(valueForm.getNumberValue());
//				value.setDatetimeValue(valueForm.getDatetimeValue());
//				value.setDateValue(valueForm.getDateValue());
//				customFieldValues_.add(value);
//			}
//		}

		ArticleCreateRequest.Builder builder = new ArticleCreateRequest.Builder();
		return builder
				.code(code)
				.coverId(coverId)
				.title(title)
				.body(body)
				.authorId(authorId)
				.date(date)
				.categoryIds(categoryIds)
				.tags(tags)
				.relatedPostIds(relatedPostIds)
				.seoTitle(seoTitle)
				.seoDescription(seoDescription)
				.seoKeywords(seoKeywords)
				.customFieldValues(customFieldValues)
				.language(language)
				.build();
	}
}
