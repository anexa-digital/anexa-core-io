package com.anexa.io.core.stage.pull.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.io.core.stage.dto.PulledStageEntityDto;

@Transactional(readOnly = true)
public interface TransformationService<M extends PulledStageEntityDto> {

	String getIntegracionType();

	@Transactional
	List<M> transformRows(long sequence);
}