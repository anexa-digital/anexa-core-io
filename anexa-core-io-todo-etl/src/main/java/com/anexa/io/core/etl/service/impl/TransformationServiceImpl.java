package com.anexa.io.core.etl.service.impl;

import static com.anexa.io.core.stage.enums.EstadoStageEntityType.CORREGIDO;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.DESCARTADO;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.ERROR_ENRIQUECIMIENTO;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.ERROR_HOMOLOGACION;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.ERROR_VALIDACION;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.ESTRUCTURA_VALIDA;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.HOMOLOGADO;
import static com.anexa.io.core.stage.enums.EstadoStageEntityType.VALIDADO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.anexa.io.core.etl.domain.RegistroStageEntity;
import com.anexa.io.core.etl.dto.CampoDto;
import com.anexa.io.core.etl.repository.RegistroStageEntityRepository;
import com.anexa.io.core.etl.service.api.TransformationService;
import com.anexa.io.core.stage.enums.EstadoStageEntityType;
import com.anexa.io.dto.IOErrorDto;

import lombok.val;

public abstract class TransformationServiceImpl<T extends RegistroStageEntity> implements TransformationService<T> {

	protected List<EstadoStageEntityType> ESTADOS_REGISTROS = asList(ESTRUCTURA_VALIDA, CORREGIDO);

//	//@Autowired
//	private ArchivoLocalService archivoLocalService;
//
//	protected ArchivoLocalService getArchivoLocalService() {
//		return archivoLocalService;
//	}
//
//	//@Autowired
//	private TipoArchivoLocalService tipoArchivoLocalService;
//
//	protected TipoArchivoLocalService getTipoArchivoLocalService() {
//		return tipoArchivoLocalService;
//	}
//
//	//@Autowired
//	private MapaLocalService mapaLocalService;
//
//	protected MapaLocalService getMapaLocalService() {
//		return mapaLocalService;
//	}

	abstract protected RegistroStageEntityRepository<T> getRepository();

	protected List<EstadoStageEntityType> getEstados() {
		return ESTADOS_REGISTROS;
	}

	@Override
	public List<Long> getArchivosPendientes() {
		val result = getRepository().findAllArchivoIdByEstadoIn(getEstados());
		return result;
	}

	@Override
	final public void transformar(Long archivoId) {
//		val archivo = getArchivoLocalService().get(archivoId);
//		val campos = getTipoArchivoLocalService().getCamposByTipoArchivo(archivo.getIdTipoArchivo());
//		val errores = new ArrayList<IOErrorDto>();
//
//		try {
//			val registros = getRepository().findAllByIdArchivoAndEstadoNotIn(archivoId, asList(PROCESADO));
//
//			beforeTranslateRows(registros, errores, campos);
//			discard(registros);
//
//			translateRows(registros, errores, campos);
//			discard(registros);
//
//			beforeValidateRows(registros, errores, campos);
//			discard(registros);
//
//			validateRows(registros, errores, campos);
//			discard(registros);
//
//			getRepository().saveAll(registros);
//		} catch (RuntimeException e) {
//			//errores.add(IOErrorDto.error(archivoId, e));
//		} finally {
//			getArchivoLocalService().transformado(archivo, errores);
//		}
	}

	// -------------------------------------------------------------------------------------
	// BEFORE TRANSLATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void beforeTranslateRows(List<T> registros, List<IOErrorDto> errores, List<CampoDto> campos) {
		registros.parallelStream().forEach(registro -> {
			switch (registro.getEstado()) {
			case PROCESADO:
			case DESCARTADO:
				return;
			default:
				break;
			}

			boolean success = true;
			try {
				success &= beforeTranslateRow(registro, errores, campos);
			} catch (RuntimeException e) {
//				val error = IOErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}

			if (!success) {
				registro.setEstado(ERROR_ENRIQUECIMIENTO);
			}
		});
	}

	// -------------------------------------------------------------------------------------
	// TRANSLATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void translateRows(List<T> registros, List<IOErrorDto> errores, List<CampoDto> campos) {
		// @formatter:off
		val camposHomologables = campos
				.stream()
				.filter(a -> a.getOrdinalTransformacion() > 0)
				.sorted((a,b) -> a.getOrdinalTransformacion().compareTo(b.getOrdinalTransformacion()))
				.collect(Collectors.toList());
		// @formatter:on

		registros.parallelStream().forEach(registro -> {
			switch (registro.getEstado()) {
			case PROCESADO:
			case DESCARTADO:
			case ERROR_ENRIQUECIMIENTO:
				return;
			default:
				break;
			}

			boolean success = true;
			try {
				success &= translateRow(registro, errores, camposHomologables);
			} catch (RuntimeException e) {
//				val error = IOErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}

			if (success) {
				registro.setEstado(HOMOLOGADO);
			} else {
				registro.setEstado(ERROR_HOMOLOGACION);
			}
		});
	}

	final protected boolean translateRow(T registro, List<IOErrorDto> errores, List<CampoDto> campos) {
		boolean result = true;

		for (val campo : campos) {
			boolean success = true;
			try {
				val codigo = registro.getHomologablePropertyValue(campo.getCodigo());
				val value = getValueFromMapOrDefault(campo, codigo);
				translateField(registro, campo, value);
			} catch (RuntimeException e) {
//				val error = IOErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}

			result &= success;
		}

		return result;
	}

	final protected String getValueFromMapOrDefault(CampoDto campo, String codigo) {
		String result = codigo;
//		val id = campo.getIdMapa();
//		if (id != null) {
//			result = getMapaLocalService().findMapaValorByMapaIdAndMapaClave(id, codigo);
//			if (result == null) {
//				result = codigo;
//			}
//		}
		return result;
	}

	// -------------------------------------------------------------------------------------
	// BEFORE VALIDATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void beforeValidateRows(List<T> registros, List<IOErrorDto> errores, List<CampoDto> campos) {
		registros.parallelStream().forEach(registro -> {
			switch (registro.getEstado()) {
			case PROCESADO:
			case DESCARTADO:				
			case ERROR_ENRIQUECIMIENTO:
			case ERROR_HOMOLOGACION:
				return;
			default:
				break;
			}

			boolean success = true;
			try {
				success &= beforeValidateRow(registro, errores, campos);
			} catch (RuntimeException e) {
//				val error = IOErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}

			if (!success) {
				registro.setEstado(ERROR_ENRIQUECIMIENTO);
			}
		});
	}

	// -------------------------------------------------------------------------------------
	// VALIDATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void validateRows(List<T> registros, List<IOErrorDto> errores, List<CampoDto> campos) {
		val requeridos = campos.stream().filter(a -> !a.isIgnorar() && a.isObligatorioValidacion()).collect(toList());

		registros.parallelStream().forEach(registro -> {
			switch (registro.getEstado()) {
			case PROCESADO:
			case DESCARTADO:				
			case ERROR_ENRIQUECIMIENTO:
			case ERROR_HOMOLOGACION:
				return;
			default:
				break;
			}

			boolean success = true;
			try {
				success &= validateRequiredFields(registro, errores, requeridos);
				success &= validateRow(registro, errores, campos);
			} catch (RuntimeException e) {
//				val error = IOErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}

			if (success) {
				registro.setEstado(VALIDADO);
			} else {
				registro.setEstado(ERROR_VALIDACION);
			}
		});
	}

	final protected boolean validateRequiredFields(T registro, List<IOErrorDto> errores, List<CampoDto> campos) {
		boolean success = true;

		for (val campo : campos) {
			boolean isNullOrEmpty;

			if (campo.getIdMapa() == null && campo.getOrdinalTransformacion() == 0) {
				isNullOrEmpty = registro.propertyIsNullOrEmpty(campo.getCodigo());
			} else {
				boolean hasBeenHomologated = registro.propertyHasBeenHomologated(campo.getCodigo());
				if (hasBeenHomologated) {
					isNullOrEmpty = false;
				} else {
					isNullOrEmpty = true;
				}
			}
			// TODO
			if (isNullOrEmpty) {
				val sb = new StringBuilder();

				String valor;
				if (campo.getOrdinalTransformacion() == 0) {
					valor = String.valueOf(registro.getObjectValueFromProperty(campo.getCodigo()));
				} else {
					valor = registro.getHomologablePropertyValue(campo.getCodigo());
				}

				sb.append(campo.getCodigo()).append(":");

				if (StringUtils.isEmpty(valor)) {
					sb.append("Este campo no admite valores vacíos.");
				} else {
					if (campo.getIdMapa() != null) {
						sb.append(String.format(
								"Verifique que el valor [%s] exista en el mapa de homologación asociado a este campo.",
								valor));
					} else {
						sb.append(String.format("Este campo no admite el valor [%s].", valor));
					}
				}

				sb.append("Solicite a su coordinador escalar este caso al área de tecnología.");
//				val error = IOErrorDto.error(registro.getIdArchivo(), sb.toString(), registro.getNumeroLinea(),
//						registro.toString());
//				errores.add(error);
				success = false;
			}
		}
		return success;
	}

	// -------------------------------------------------------------------------------------
	//
	// -------------------------------------------------------------------------------------
	protected boolean beforeTranslateRow(T registro, List<IOErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	abstract protected void translateField(T registro, CampoDto campo, String value);

	protected boolean beforeValidateRow(T registro, List<IOErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	protected boolean validateRow(T registro, List<IOErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	// -------------------------------------------------------------------------------------
	//
	// -------------------------------------------------------------------------------------
	public void discard(List<T> registros) {
		// @formatter:off
		val errores = registros
				.stream()
				.filter(row -> row.hasErrors())
				.map(RegistroStageEntity::correlacion)
				.distinct()
				.collect(toList());
		// @formatter:on

		if (errores.size() == 0) {
			return;
		}

		// @formatter:off
		val ok = registros
				.stream()
				.filter(row -> !row.hasErrors())
				.collect(toList());
		// @formatter:on

		ok.forEach(row -> {
			if (errores.stream().anyMatch(a -> a.equals(row.correlacion()))) {
				row.setEstado(DESCARTADO);
			}
		});
	}

	protected <ID> void verificarErrorHomologacion(ID id, CampoDto campo, String valor) {
		if (id == null) {
			throw new RuntimeException(String.format(
					"%s: El valor [%s] no es un valor valido que pueda ser homologado.", campo.getCodigo(), valor));
		}
	}
}