package com.anexa.io.core.etl.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.anexa.io.core.stage.domain.StageEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
abstract public class PulledStageEntity extends StageEntity {
	
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
}
