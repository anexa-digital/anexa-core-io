package com.anexa.io.core.stage.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;
import com.anexa.io.dto.EntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
abstract public class StageEntityDto extends EntityDto<Long> {

	@NotNull
	private OperacionType operacion;

	@NotNull
	private EstadoStageEntityType estado;

	@NotNull
	@Size(max = 50)
	private String integracion;

	@NotNull
	@Size(max = 50)
	private String origenId;

	@NotNull
	@Size(max = 50)
	private String estadoOrigenId;

	public boolean hasErrors() {
		switch (getEstado()) {
		case ERROR_ESTRUCTURA:
		case ERROR_ENRIQUECIMIENTO:
		case ERROR_HOMOLOGACION:
		case ERROR_VALIDACION:
		case ERROR_CARGUE:
			return true;
		default:
			return false;
		}
	}

	public StageEntityDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull OperacionType operacion, @NotNull EstadoStageEntityType estado,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId,
			@NotNull @Size(max = 50) String estadoOrigenId) {
		super(id, version, fechaCreacion, fechaModificacion);
		this.operacion = operacion;
		this.estado = estado;
		this.integracion = integracion;
		this.origenId = origenId;
		this.estadoOrigenId = estadoOrigenId;
	}
}
