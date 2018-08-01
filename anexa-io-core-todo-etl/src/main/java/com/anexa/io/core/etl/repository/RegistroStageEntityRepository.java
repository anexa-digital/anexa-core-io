package com.anexa.io.core.etl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.anexa.io.core.etl.domain.RegistroStageEntity;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;

@NoRepositoryBean
public interface RegistroStageEntityRepository<T extends RegistroStageEntity> extends JpaRepository<T, Long> {
	List<T> findAllByIdArchivoAndEstadoIn(Long id, List<EstadoStageEntityType> estados);

	List<T> findAllByIdArchivoAndEstadoNotIn(Long id, List<EstadoStageEntityType> estados);

	List<Long> findAllArchivoIdByEstadoIn(List<EstadoStageEntityType> estados);
}
