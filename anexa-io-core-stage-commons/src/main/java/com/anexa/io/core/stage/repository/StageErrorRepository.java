package com.anexa.io.core.stage.repository;

import java.util.List;

import com.anexa.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.anexa.io.core.stage.domain.StageError;

public interface StageErrorRepository extends IdentifiedDomainObjectRepository<StageError, Long> {
	List<StageError> findAllByIntegracionAndId(String integracion, Long id);

	List<StageError> findAllByIntegracionAndIdIn(String integracion, List<Long> ids);
}
