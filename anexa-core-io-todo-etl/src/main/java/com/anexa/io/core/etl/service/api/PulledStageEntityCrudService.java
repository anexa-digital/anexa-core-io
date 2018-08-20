package com.anexa.io.core.etl.service.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.core.services.crud.api.CrudService;
import com.anexa.io.core.stage.dto.StageEntityDto;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;

@Transactional(readOnly = true)
public interface PulledStageEntityCrudService<M extends StageEntityDto> extends CrudService<M, Long> {

	String getIntegracionType();
	
	Long getNextValueOfSequence();
	
	List<M> findBySecuencia(long sequence);
	
	List<Long> getSequencesOfCorrectedRecords();
	
	List<M> findAllByOptionConsola(LocalDateTime fechaDesde, LocalDateTime fechaHasta, List<OperacionType> operaciones, List<EstadoStageEntityType> estados);
}