package com.anexa.core.io.stage.service.api;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.core.io.stage.dto.IntegracionDto;
import com.anexa.core.services.crud.api.CrudService;
import com.anexa.core.services.crud.api.QueryByCodigoService;

@Transactional(readOnly = true)
public interface IntegracionCrudService extends CrudService<IntegracionDto, Long>, QueryByCodigoService<IntegracionDto, Long> {

	Optional<IntegracionDto> findByCodigo(String codigo);

}
