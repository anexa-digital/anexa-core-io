package com.anexa.core.io.stage.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.core.io.stage.enums.EstadoStageEntityType;
import com.anexa.core.io.stage.enums.OperacionType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EntradaDto extends StageEntityDto {

	@NotNull
	private String datos;

	private int entradasEnCola;

	@Builder
	public EntradaDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull OperacionType operacion, @NotNull EstadoStageEntityType estado,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId,
			@NotNull @Size(max = 50) String estadoOrigenId, @NotNull String datos, int entradasEnCola) {
		super(id, version, fechaCreacion, fechaModificacion, operacion, estado, integracion, origenId, estadoOrigenId);
		this.datos = datos;
		this.entradasEnCola = entradasEnCola;
	}
}
