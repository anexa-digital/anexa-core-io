package com.anexa.io.core.stage.repository;

import com.anexa.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.anexa.core.data.jpa.repository.QueryByCodigo;
import com.anexa.io.core.stage.domain.Integracion;

public interface IntegracionRepository extends IdentifiedDomainObjectRepository<Integracion,Long> , QueryByCodigo<Integracion, Long> {

}
