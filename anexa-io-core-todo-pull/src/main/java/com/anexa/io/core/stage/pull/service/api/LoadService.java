package com.anexa.io.core.stage.pull.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.io.core.stage.domain.PulledStageEntity;
import com.anexa.io.core.stage.dto.PulledStageEntityDto;

public interface LoadService<M extends PulledStageEntityDto, E extends PulledStageEntity> {

	String getIntegracionType();

	@Transactional
	List<M> load(long secuencia);
}