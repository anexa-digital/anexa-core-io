package com.anexa.core.io.stage.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexa.core.io.stage.domain.Entrada;
import com.anexa.core.io.stage.dto.EntradaDto;
import com.anexa.core.io.stage.enums.EstadoStageEntityType;
import com.anexa.core.io.stage.repository.EntradaRepository;
import com.anexa.core.io.stage.service.api.EntradaCrudService;
import com.anexa.core.services.crud.impl.CrudServiceImpl;

import lombok.val;

@Service
public class EntradaCrudServiceImpl extends CrudServiceImpl<Entrada, EntradaDto, Long> implements EntradaCrudService {

	@Autowired
	private EntradaRepository repository;

	@Override
	protected EntradaRepository getRepository() {
		return repository;
	}

	@Override
	protected EntradaDto asModel(Entrada entity) {
		val model = new EntradaDto();

		model.setId(entity.getId());
		model.setVersion(entity.getVersion());
		model.setFechaCreacion(entity.getFechaCreacion());
		model.setFechaModificacion(entity.getFechaModificacion());

		model.setOperacion(entity.getOperacion());
		model.setEstado(entity.getEstado());
		model.setIntegracion(entity.getIntegracion());
		model.setOrigenId(entity.getOrigenId());
		model.setEstadoOrigenId(entity.getEstadoOrigenId());
		model.setDatos(entity.getDatos());
		model.setEntradasEnCola(entity.getEntradasEnCola());

		return model;
	}

	@Override
	protected Entrada asEntity(EntradaDto model, Entrada entity) {

		entity.setVersion(model.getVersion());

		entity.setOperacion(model.getOperacion());
		entity.setEstado(model.getEstado());
		entity.setIntegracion(model.getIntegracion());
		entity.setOrigenId(model.getOrigenId());
		entity.setEstadoOrigenId(model.getEstadoOrigenId());
		entity.setDatos(model.getDatos());
		entity.setEntradasEnCola(model.getEntradasEnCola());

		return entity;
	}

	@Override
	protected Entrada newEntity() {
		return new Entrada();
	}

	@Override
	public void enqueue(EntradaDto entrada) {
		val optional = getRepository().findByIntegracionAndOrigenId(entrada.getIntegracion(), entrada.getOrigenId());
		if (optional.isPresent()) {
			val entity = optional.get();
			entity.setEntradasEnCola(entity.getEntradasEnCola() + 1);
			getRepository().saveAndFlush(entity);
		} else {
			create(entrada);
		}
	}

	@Override
	public void enqueue(List<EntradaDto> entradas) {
		for (val entrada : entradas) {
			enqueue(entrada);
		}
	}

	@Override
	public List<EntradaDto> findAllNoProcesadas() {
		val estados = Arrays.asList(EstadoStageEntityType.NO_PROCESADO, EstadoStageEntityType.CORREGIDO);
		val entities = getRepository().findAllByEstadoIn(estados);
		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<EntradaDto> findByIntegracionAndOrigenId(String integracion, String origenId) {
		val optional = getRepository().findByIntegracionAndOrigenId(integracion, origenId);
		val result = asModel(optional);
		return result;
	}
}