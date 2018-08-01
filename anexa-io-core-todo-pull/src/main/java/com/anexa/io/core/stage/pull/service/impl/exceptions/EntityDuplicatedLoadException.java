package com.anexa.io.core.stage.pull.service.impl.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.anexa.io.core.stage.domain.PulledStageEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityDuplicatedLoadException extends EntityLoadException {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private static final long serialVersionUID = 1L;

	public EntityDuplicatedLoadException(String integracionType, PulledStageEntity entity) {
		super(message(integracionType, entity));
		codigo = "error_try_create_but_already_exist";
	}

	private static String message(String integracionType, PulledStageEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("Se solicitó crear una nueva entidad del flujo ");
		sb.append(integracionType.toString());
		sb.append(", pero ya existe en la base de datos.");

		LocalDateTime ldt;
		ldt = entity.getFechaUltimoCambioEnOrigen();
		{
			String mensaje = "\nLa última vez que esta entidad fue modificada en el origen fue el %s.";
			sb.append(String.format(mensaje, ldt.format(formatter)));
		}

		ldt = entity.getFechaExtraccion();
		{
			String mensaje = "\\nLa última vez que esta entidad fue extraida desde el origen fue el %s.";
			sb.append(String.format(mensaje, ldt.format(formatter)));
		}
		//TODO
		ldt = entity.getFechaCargue();
		if (ldt != null) {
			String mensaje = "La última vez que esta entidad fue sincronizada en el destino fue el %s.";
			sb.append(String.format(mensaje, ldt.format(formatter)));
		} else {
			String mensaje = "Esta entidad actualmente se encuentra en proceso de sincronización hacia el destino y tiene %d sincronizaciones pendientes";
			//sb.append(String.format(mensaje, entity.getSincronizacionesEnCola()));
			sb.append(String.format(mensaje, 0));
		}

		String result = sb.toString();

		return result;
	}
}