package com.anexa.core.io.stage.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.anexa.core.io.stage.dto.StageEntityDto;
import com.anexa.io.domain.IOError;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Entity
@Table(name = "Errores")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class StageError extends IOError {

	@Column(name = "integracion", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String integracion;

	@Column(name = "origenId", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String origenId;

	public StageError error(String integracion,
			StageEntityDto row, String codigo, String mensaje) {
		// @formatter:off
 		val result = StageError
				.builder()
				.integracion(integracion)
				.origenId(row.getOrigenId())
				.codigo(codigo)
				.mensaje(mensaje)
				.build();
		// @formatter:on
		return result;
	}
	
	@Builder
	public StageError(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 1024) String mensaje,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId) {
		super(id, version, fechaCreacion, fechaModificacion, codigo, mensaje);
		this.integracion = integracion;
		this.origenId = origenId;
	}
}