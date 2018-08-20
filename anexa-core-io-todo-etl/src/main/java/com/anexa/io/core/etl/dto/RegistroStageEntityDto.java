package com.anexa.io.core.etl.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.anexa.core.domain.IdentifiedDomainObject;
import com.anexa.io.core.stage.dto.StageEntityDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegistroStageEntityDto<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends StageEntityDto {

	private static final long serialVersionUID = 1L;

	private Long archivoId;

	private int numeroLinea;

	@Setter
	@NonNull
	private String linea;

	@NonNull
	private Map<String, String> matrix = new HashMap<>();


}
