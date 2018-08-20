package com.anexa.io.core.stage.repository;

import java.util.List;
import java.util.Optional;

import com.anexa.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.anexa.io.core.stage.domain.Entrada;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;

public interface EntradaRepository extends IdentifiedDomainObjectRepository<Entrada, Long> {

	Optional<Entrada> findByIntegracionAndOrigenId(String integracion, String origenId);

	List<Entrada> findAllByEstadoIn(List<EstadoStageEntityType> estados);
}
