package com.anexa.io.core.stage.pull.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.anexa.io.core.stage.dto.EntradaDto;
import com.anexa.io.core.stage.pull.service.api.ExtractionService;
import com.anexa.io.core.stage.service.api.EntradaCrudService;
import com.anexa.io.core.stage.service.api.IntegracionCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ExtractionServiceImpl implements ExtractionService {

	public static final String PARAMETER_NAME_FECHA_DESDE = "fechaDesde";

	public static final String PARAMETER_NAME_FECHA_HASTA = "fechaHasta";

	@Autowired
	private IntegracionCrudService integracionCrudService;

	@Autowired
	private EntradaCrudService entradaCrudService;

	abstract protected NamedParameterJdbcTemplate getSourceJdbcTemplate();

	// -------------------------------------------------------------------------------------
	// Extract rows
	// -------------------------------------------------------------------------------------
	@Override
	public int extractRows() {
		log.debug("{}:Inicio de la extracción de registros desde la base de datos origen", getIntegracionType());

		val integracion = integracionCrudService.findOneByCodigo(getIntegracionType());
		val fechaDesde = integracion.getFechaUltimaConsulta();
		val fechaHasta = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		integracion.setFechaUltimaConsulta(fechaHasta);
		integracionCrudService.update(integracion);

		log.debug("{}:Intervalo=[{} ... {}]", getIntegracionType(), fechaDesde, fechaHasta);

		val entradas = getRowsFromSource(fechaDesde, fechaHasta);
		entradaCrudService.enqueue(entradas);

		log.debug("{}:Fin de la extracción de registros desde la base de datos origen", getIntegracionType());

		val result = entradas.size();
		return result;
	}

	// -------------------------------------------------------------------------------------
	// PULL ROWS FROM SOURCE
	// -------------------------------------------------------------------------------------
	protected List<EntradaDto> getRowsFromSource(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
		log.debug("{}:Consultando los registros en el intervalo [{} ... {}]", getIntegracionType(), fechaDesde,
				fechaHasta);

		val sql = getSqlSelectFromSource();
		val parametros = getParameters();
		parametros.addValue(getParameterNameFechaDesde(), fechaDesde);
		parametros.addValue(getParameterNameFechaHasta(), fechaHasta);

		val result = getSourceJdbcTemplate().query(sql, parametros, getRowMapper());
		log.debug("{}:Se encontraron {} registros", getIntegracionType(), result.size());

		return result;
	}

	protected MapSqlParameterSource getParameters() {
		return new MapSqlParameterSource();
	}

	protected String getParameterNameFechaHasta() {
		return PARAMETER_NAME_FECHA_HASTA;
	}

	protected String getParameterNameFechaDesde() {
		return PARAMETER_NAME_FECHA_DESDE;
	}

	abstract protected String getSqlSelectFromSource();

	abstract protected RowMapper<EntradaDto> getRowMapper();
}