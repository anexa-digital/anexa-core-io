package com.anexa.core.io.stage.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.io.dto.IOErrorDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class StageErrorDto extends IOErrorDto {

	@NotNull
	@Size(max = 50)
	private String integracion;

	@NotNull
	@Size(max = 50)
	private String origenId;

	@Builder
	public StageErrorDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 1024) String mensaje,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId) {
		super(id, version, fechaCreacion, fechaModificacion, codigo, mensaje);
		this.integracion = integracion;
		this.origenId = origenId;
	}
}