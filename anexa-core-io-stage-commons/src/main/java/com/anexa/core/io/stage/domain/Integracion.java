package com.anexa.core.io.stage.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.anexa.core.domain.ObjectWithCode;
import com.anexa.io.domain.XBaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Integraciones")
@DynamicUpdate
@AttributeOverride(name = "id", column = @Column(name = "integracionId"))
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Integracion extends XBaseEntity<Long> implements ObjectWithCode<Long> {

	@Column(name = "codigo", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String codigo;

	@Column(name = "nombre", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String nombre;

	@Column(name = "descripcion", length = 200, nullable = false)
	@NotNull
	@Size(max = 200)
	private String descripcion;

	@Column(name = "fechaUltimaConsulta", nullable = false)
	@NotNull
	private LocalDateTime fechaUltimaConsulta;
}
