package com.anexa.io.core.etl.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.anexa.io.core.stage.dto.StageEntityDto;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PulledStageEntityDto extends StageEntityDto {
	private static final long serialVersionUID = 1L;

	private Long secuencia;

	@NotNull
	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaUltimoCambioEnOrigen;

	@NotNull
	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaExtraccion;

	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaTransformacion;

	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaCargue;

	@Builder
	public PulledStageEntityDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull OperacionType operacion, @NotNull EstadoStageEntityType estado,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId,
			@NotNull @Size(max = 50) String estadoOrigenId, @NotNull String datos, int entradasEnCola, Long secuencia,
			@NotNull LocalDateTime fechaUltimoCambioEnOrigen, @NotNull LocalDateTime fechaExtraccion,
			LocalDateTime fechaTransformacion, LocalDateTime fechaCargue) {
		super(id, version, fechaCreacion, fechaModificacion, operacion, estado, integracion, origenId, estadoOrigenId,
				datos, entradasEnCola);
		this.secuencia = secuencia;
		this.fechaUltimoCambioEnOrigen = fechaUltimoCambioEnOrigen;
		this.fechaExtraccion = fechaExtraccion;
		this.fechaTransformacion = fechaTransformacion;
		this.fechaCargue = fechaCargue;
	}
}
