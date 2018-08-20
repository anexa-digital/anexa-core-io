package com.anexa.io.core.stage.pull.service.impl.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityLoadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String codigo;

	public EntityLoadException() {
		super();
	}

	public EntityLoadException(String message) {
		super(message);
	}
}