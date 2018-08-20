package com.anexa.io.core.stage.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.core.domain.ObjectWithCode;
import com.anexa.io.dto.EntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IntegracionDto extends EntityDto<Long> implements ObjectWithCode<Long>{

	@NotNull
	@Size(max = 50)
	private String codigo;
	
	@NotNull
	@Size(max = 100)
	private String nombre;
	
	@NotNull
	@Size(max = 200)
	private String descripcion;
	
	@NotNull
	private LocalDateTime fechaUltimaConsulta;

	@Builder
	public IntegracionDto(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 100) String nombre,
			@NotNull @Size(max = 200) String descripcion, @NotNull LocalDateTime fechaUltimaConsulta) {
		super(id, version, fechaCreacion, fechaModificacion);
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaUltimaConsulta = fechaUltimaConsulta;
	}
}
