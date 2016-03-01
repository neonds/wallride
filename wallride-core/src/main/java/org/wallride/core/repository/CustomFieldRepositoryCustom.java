package org.wallride.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.wallride.core.domain.CustomField;
import org.wallride.core.model.CustomFieldSearchRequest;

public interface CustomFieldRepositoryCustom {

	Page<CustomField> search(CustomFieldSearchRequest request);
	Page<CustomField> search(CustomFieldSearchRequest request, Pageable pageable);
}
