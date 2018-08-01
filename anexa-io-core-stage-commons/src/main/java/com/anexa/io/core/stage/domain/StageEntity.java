package com.anexa.io.core.stage.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;
import com.anexa.io.domain.XBaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
abstract public class StageEntity extends XBaseEntity<Long> {

	@Column(name = "operacion", length = 1, nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	private OperacionType operacion;

	@Column(name = "estado", length = 50, nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	private EstadoStageEntityType estado;

	@Column(name = "integracion", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String integracion;

	@Column(name = "origenId", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String origenId;

	@Column(name = "estadoOrigenId", length = 50, nullable = false)
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

	public StageEntity(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
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
