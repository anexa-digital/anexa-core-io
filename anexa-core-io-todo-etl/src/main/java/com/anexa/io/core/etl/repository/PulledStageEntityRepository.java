package com.anexa.io.core.etl.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.anexa.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.anexa.io.core.etl.domain.PulledStageEntity;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;

@NoRepositoryBean
public interface PulledStageEntityRepository<T extends PulledStageEntity> extends IdentifiedDomainObjectRepository<T, Long> {

	List<T> findAllBySecuencia(long sequence);
	
	List<T> findAllByEstadoIn(List<EstadoStageEntityType> estados);

	List<T> findAllByFechaUltimoCambioEnOrigenBetweenAndOperacionInAndEstadoIn(LocalDateTime desde, LocalDateTime hasta,
			List<OperacionType> operaciones, List<EstadoStageEntityType> estados);

}