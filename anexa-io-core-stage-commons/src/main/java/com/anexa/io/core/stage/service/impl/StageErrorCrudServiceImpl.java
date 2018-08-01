package com.anexa.io.core.stage.service.impl;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.left;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexa.core.services.crud.impl.CrudServiceImpl;
import com.anexa.io.core.stage.domain.StageError;
import com.anexa.io.core.stage.dto.StageErrorDto;
import com.anexa.io.core.stage.repository.StageErrorRepository;
import com.anexa.io.core.stage.service.api.StageErrorCrudService;

import lombok.val;

@Service
public class StageErrorCrudServiceImpl extends CrudServiceImpl<StageError, StageErrorDto, Long>
		implements StageErrorCrudService {

	@Autowired
	private StageErrorRepository repository;

	@Override
	protected StageErrorRepository getRepository() {
		return repository;
	}

	@Override
	protected StageErrorDto asModel(StageError entity) {
		StageErrorDto model = new StageErrorDto();

		model.setId(entity.getId());
		model.setVersion(entity.getVersion());
		model.setFechaCreacion(entity.getFechaCreacion());
		model.setFechaModificacion(entity.getFechaModificacion());

		model.setCodigo(entity.getCodigo());
		model.setMensaje(entity.getMensaje());

		model.setIntegracion(entity.getIntegracion());
		model.setOrigenId(entity.getOrigenId());

		return model;
	}

	@Override
	protected StageError asEntity(StageErrorDto model, StageError entity) {
		entity.setVersion(model.getVersion());

		entity.setCodigo(model.getCodigo());
		entity.setMensaje(model.getMensaje());

		entity.setIntegracion(model.getIntegracion());
		entity.setOrigenId(model.getOrigenId());

		return entity;
	}

	@Override
	protected StageError newEntity() {
		return new StageError();
	}

	@Override
	public List<StageErrorDto> findAllByIdAndIntegracion(Long id, String integracion) {
		val entities = getRepository().findAllByIntegracionAndId(integracion, id);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<StageErrorDto> findAllErrorsByIdInAndIntegracion(List<Long> ids, String integracion) {
		val entities = getRepository().findAllByIntegracionAndIdIn(integracion,ids);
		val result = asModels(entities);
		return result;
	}

	@Override
	public StageErrorDto error(String integracion, String origenId, Throwable t) {
		// @formatter:off
		val model = StageErrorDto
					.builder()
					.integracion(integracion)
					.origenId(origenId)
					.codigo(left(t.getClass().getSimpleName(), 50)) 
					.mensaje(left(t.getMessage(), 1024))
					.build();

		// @formatter:on
		val result = create(model);
		return result;
	}
}