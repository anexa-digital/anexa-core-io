package com.anexa.io.core.etl.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.anexa.io.core.stage.domain.StageEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
abstract public class RegistroStageEntity extends StageEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_archivo")
	private Long idArchivo;

	@Column(name = "numero_linea")
	private int numeroLinea;
	
	abstract public String correlacion();
	

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public boolean propertyIsNullOrEmpty(String property) {
		boolean result;
		val value = getObjectValueFromProperty(property);

		if (value == null) {
			result = true;
		} else {
			if (value instanceof String) {
				result = "".equals(((String) value).trim());
			} else {
				result = false;
			}
		}

		return result;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public String getHomologablePropertyValue(String property) {
		if (!isHomologableProperty(property)) {
			throw new IllegalArgumentException("El campo \"" + property + "\", no es homologable.");
		}

		String result = getStringValueFromHomologableProperty(property);
		if (result == null) {
			result = "";
		}
		return result;
	}

	abstract public boolean isHomologableProperty(String property);

	abstract public Object getObjectValueFromProperty(String property);

	abstract protected String getStringValueFromHomologableProperty(String property);

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public boolean propertyHasBeenHomologated(String property) {
		boolean result = false;
		if (isHomologableProperty(property)) {
			val value = getObjectValueFromHomologousProperty(property);
			result = (value != null);
		}
		return result;
	}

	abstract protected Object getObjectValueFromHomologousProperty(String property);
}