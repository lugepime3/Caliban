package com.morlock.caliban.controllers.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morlock.caliban.controllers.MutantController;
import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.services.MutantService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class MutantControllerImpl implements MutantController {
	private static final Logger logger = LoggerFactory
			.getLogger(com.morlock.caliban.controllers.impl.MutantControllerImpl.class);
	private static final String UNEXPECTED_ERROR = "An unexpected error occurred: ";

	@Autowired
	@Qualifier("MutantService")
	MutantService mutantService;

	@Autowired(required = false)
	ObjectMapper mapper;

	@ApiOperation(value = "Agregar una secuencia de ADN para ser Analizada", notes = " <b>Sabras si es un humano o es un mutante, si encuentras más de una secuencia de cuatro letras\n"
			+ "iguales , de forma oblicua, horizontal o vertical.</b>")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "<b>Este codigo corresponde a un Mutante !!!!.</b>"),
			@ApiResponse(code = 201, message = "Creado"),
			@ApiResponse(code = 401, message = "No Autorizado"),
			@ApiResponse(code = 403, message = "Este codigo corresponde solo a un Humano ....."),
			@ApiResponse(code = 404, message = "No Encontrado."),
			@ApiResponse(code = 500, message = "Un Error Inesperado a Ocurrido."), })
	@PostMapping("/mutant/")
	@Override
	public ResponseEntity<Mutant> addMutant(@RequestBody Mutant mutant) {

		try {
			mutant = mutantService.saveMutant(mutant);
			if (mutant.isConfirmed()) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

		} catch (Exception e) {
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Estadisticas del ADN Mutante y Humano", notes = " <b>Estadisticas del ADN Mutante y Humano</b>.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "<b>Todo Funciona Correcto</b>"),
			@ApiResponse(code = 201, message = "Creado"),
			@ApiResponse(code = 401, message = "No Autorizado"),
			@ApiResponse(code = 403, message = "Este codigo corresponde solo a un Humano ....."),
			@ApiResponse(code = 404, message = "No Encontrado."),
			@ApiResponse(code = 500, message = "Un Error Inesperado a Ocurrido."), })
	@GetMapping("/stats")
	@Override
	public @ResponseBody String stats() throws JsonProcessingException {
		Map<String, Object> map;
		map = mutantService.stats();
		return mapper.writeValueAsString(map);
	}

	@ApiOperation(value = "Mostrar Todos los analisis de ADN Realizados", notes = " <b>Mostrar Todos los analisis de ADN Realizados</b>")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "<b>Todo Funciona Correcto</b>"),
			@ApiResponse(code = 201, message = "Creado"),
			@ApiResponse(code = 401, message = "No Autorizado"),
			@ApiResponse(code = 403, message = "Este codigo corresponde solo a un Humano ....."),
			@ApiResponse(code = 404, message = "No Encontrado."),
			@ApiResponse(code = 500, message = "Un Error Inesperado a Ocurrido."), })
	@GetMapping(value = "/show")
	@Override
	public List<Mutant> getMutants() {
		return mutantService.findAllMutants();
	}

}
