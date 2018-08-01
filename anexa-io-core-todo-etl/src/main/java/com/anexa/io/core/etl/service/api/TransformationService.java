package com.anexa.io.core.etl.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.io.core.etl.domain.RegistroStageEntity;


@Transactional(readOnly = true)
public interface TransformationService<T extends RegistroStageEntity> {

	List<Long> getArchivosPendientes();

	@Transactional
	void transformar(Long archivoId);
}
