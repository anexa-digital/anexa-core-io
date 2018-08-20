package com.anexa.io.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.anexa.io.domain.XBaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@AttributeOverride(name = "id", column = @Column(name = "errorId"))
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public abstract class IOError extends XBaseEntity<Long> {

	@Column(name = "codigo", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String codigo;

	@Column(name = "mensaje", length = 1024, nullable = false)
	@NotNull
	@Size(max = 1024)
	private String mensaje;

	public IOError(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 1024) String mensaje) {
		super(id, version, fechaCreacion, fechaModificacion);
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
}