package com.anexa.core.io.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
abstract public class IOErrorDto extends EntityDto<Long> {

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 1024)
	private String mensaje;

	public IOErrorDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 1024) String mensaje) {
		super(id, version, fechaCreacion, fechaModificacion);
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
}