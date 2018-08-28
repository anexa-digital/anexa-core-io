package com.anexa.core.io.stage.repository;

import com.anexa.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.anexa.core.data.jpa.repository.QueryByCodigo;
import com.anexa.core.io.stage.domain.Integracion;

public interface IntegracionRepository extends IdentifiedDomainObjectRepository<Integracion,Long> , QueryByCodigo<Integracion, Long> {

}
