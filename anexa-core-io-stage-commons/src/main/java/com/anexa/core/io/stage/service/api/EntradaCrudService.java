package com.anexa.core.io.stage.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.anexa.core.io.stage.dto.EntradaDto;
import com.anexa.core.services.crud.api.CrudService;

@Transactional(readOnly = true)
public interface EntradaCrudService extends CrudService<EntradaDto, Long> {

	@Transactional(readOnly = false)
	void enqueue(EntradaDto model);

	@Transactional(readOnly = false)
	void enqueue(List<EntradaDto> models);

	List<EntradaDto> findAllNoProcesadas();

	Optional<EntradaDto> findByIntegracionAndOrigenId(String integracion, String origenId);
}
