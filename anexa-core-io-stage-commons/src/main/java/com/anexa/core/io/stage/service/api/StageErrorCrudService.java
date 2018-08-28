package com.anexa.core.io.stage.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.core.io.stage.dto.StageErrorDto;
import com.anexa.core.services.crud.api.CrudService;

@Transactional(readOnly = true)
public interface StageErrorCrudService extends CrudService<StageErrorDto, Long> {

	List<StageErrorDto> findAllByIdAndIntegracion(Long id, String integracion);

	List<StageErrorDto> findAllErrorsByIdInAndIntegracion(List<Long> ids, String integracion);

	@Transactional
	StageErrorDto error(String integracion, String origenId, Throwable t);
}
