package com.anexa.io.core.stage.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.core.stage.enums.OperacionType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Entradas")
@DynamicUpdate
@AttributeOverride(name = "id", column = @Column(name = "entradaId"))
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Entrada extends StageEntity {

	@Column(nullable = false)
	@NotNull
	private String datos;

	@Column(nullable = false)
	private int entradasEnCola;

	@Builder
	public Entrada(Long id, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			@NotNull OperacionType operacion, @NotNull EstadoStageEntityType estado,
			@NotNull @Size(max = 50) String integracion, @NotNull @Size(max = 50) String origenId,
			@NotNull @Size(max = 50) String estadoOrigenId, @NotNull String datos, int entradasEnCola) {
		super(id, version, fechaCreacion, fechaModificacion, operacion, estado, integracion, origenId, estadoOrigenId);
		this.datos = datos;
		this.entradasEnCola = entradasEnCola;
	}
}