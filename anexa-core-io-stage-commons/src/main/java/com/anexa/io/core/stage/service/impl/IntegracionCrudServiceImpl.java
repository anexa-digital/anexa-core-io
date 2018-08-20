package com.anexa.io.core.stage.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexa.core.services.crud.impl.CrudServiceImpl;
import com.anexa.io.core.stage.domain.Integracion;
import com.anexa.io.core.stage.dto.IntegracionDto;
import com.anexa.io.core.stage.repository.IntegracionRepository;
import com.anexa.io.core.stage.service.api.IntegracionCrudService;

import lombok.val;

@Service
public class IntegracionCrudServiceImpl extends CrudServiceImpl<Integracion, IntegracionDto, Long>
		implements IntegracionCrudService {

	@Autowired
	private IntegracionRepository repository;

	@Override
	protected IntegracionRepository getRepository() {
		return repository;
	}

	@Override
	protected IntegracionDto asModel(Integracion entity) {
		val model = new IntegracionDto();

		model.setId(entity.getId());
		model.setVersion(entity.getVersion());
		model.setFechaCreacion(entity.getFechaCreacion());
		model.setFechaModificacion(entity.getFechaModificacion());

		model.setCodigo(entity.getCodigo());
		model.setNombre(entity.getNombre());
		model.setDescripcion(entity.getDescripcion());
		model.setFechaUltimaConsulta(entity.getFechaUltimaConsulta());

		return model;
	}

	@Override
	protected Integracion asEntity(IntegracionDto model, Integracion entity) {

		entity.setVersion(model.getVersion());

		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setFechaUltimaConsulta(model.getFechaUltimaConsulta());

		return entity;
	}

	@Override
	protected Integracion newEntity() {
		return new Integracion();
	}

	@Override
	public Optional<IntegracionDto> findByCodigo(String codigo) {
		val optional = getRepository().findByCodigo(codigo);

		val result = asModel(optional);
		return result;
	}

}