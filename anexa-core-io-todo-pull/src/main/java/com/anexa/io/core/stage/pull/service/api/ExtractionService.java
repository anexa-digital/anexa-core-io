package com.anexa.io.core.stage.pull.service.api;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ExtractionService {

	String getIntegracionType();
	
	@Transactional
	int extractRows();
}