package com.anexa.io.core.etl.service.impl;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.anexa.core.services.crud.impl.CrudServiceImpl;
import com.anexa.io.core.etl.domain.PulledStageEntity;
import com.anexa.io.core.etl.dto.PulledStageEntityDto;
import com.anexa.io.core.etl.repository.PulledStageEntityRepository;
import com.anexa.io.core.etl.service.api.PulledStageEntityCrudService;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;
import com.anexa.io.core.stage.service.api.StageErrorCrudService;

import lombok.val;

public abstract class PulledStageEntityCrudServiceImpl<E extends PulledStageEntity, M extends PulledStageEntityDto>
		extends CrudServiceImpl<E, M, Long> implements PulledStageEntityCrudService<M> {

	protected final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	public PulledStageEntityCrudServiceImpl() {
		super();
	}

	@Override
	abstract protected PulledStageEntityRepository<E> getRepository();

	@Autowired
	private StageErrorCrudService stageErrorCrudService;

	protected StageErrorCrudService getStageErrorCrudService() {
		return stageErrorCrudService;
	}

	@Override
	protected M asModel(E entity) {
		M model = getNewModel();

		model.setId(entity.getId());
		model.setOrigenId(entity.getOrigenId());
		model.setOperacion(entity.getOperacion());
		model.setEstado(entity.getEstado());

		model.setSecuencia(entity.getSecuencia());
		model.setFechaUltimoCambioEnOrigen(entity.getFechaUltimoCambioEnOrigen());
		model.setFechaExtraccion(entity.getFechaExtraccion());
		model.setFechaTransformacion(entity.getFechaTransformacion());
		model.setFechaCargue(entity.getFechaCargue());
		
		model.setFechaCreacion(entity.getFechaCreacion());
		model.setFechaModificacion(entity.getFechaModificacion());		

		return model;
	}

	@Override
	protected E asEntity(M model, E entity) {

		entity.setOrigenId(model.getOrigenId());
		entity.setOperacion(model.getOperacion());
		entity.setEstado(model.getEstado());

		entity.setSecuencia(model.getSecuencia());
		entity.setFechaUltimoCambioEnOrigen(model.getFechaUltimoCambioEnOrigen());
		entity.setFechaExtraccion(model.getFechaExtraccion());
		entity.setFechaTransformacion(model.getFechaTransformacion());
		entity.setFechaCargue(model.getFechaCargue());	
		
		return entity;
	}

	@Override
	protected E beforeDelete(E entity) {
		E result = super.beforeDelete(entity);

		val errors = getStageErrorCrudService().findAllByIdAndIntegracion(entity.getId(), getIntegracionType());
		val ids = errors.stream().map(a -> a.getId()).collect(toList());
		getStageErrorCrudService().delete(ids);

		return result;
	}

	@Override
	public Long getNextValueOfSequence() {
		
		val result = Long.valueOf(LocalDateTime.now().format(formatter));
		return result;
	}

	@Override
	public List<M> findBySecuencia(long sequence) {
		val entities = getRepository().findAllBySecuencia(sequence);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<Long> getSequencesOfCorrectedRecords() {
		val estados = Arrays.asList(EstadoStageEntityType.CORREGIDO);
		val entities = getRepository().findAllByEstadoIn(estados);
		val result = entities.stream().map(E::getSecuencia).distinct().sorted().collect(toList());
		return result;
	}

	@Override
	public List<M> findAllByOptionConsola(LocalDateTime desde, LocalDateTime hasta, List<OperacionType> operaciones,
			List<EstadoStageEntityType> estados) {
		val entities = getRepository().findAllByFechaUltimoCambioEnOrigenBetweenAndOperacionInAndEstadoIn(desde, hasta,
				operaciones, estados);
		val result = asModels(entities);
		return result;
	}
	
	abstract protected M getNewModel();
}