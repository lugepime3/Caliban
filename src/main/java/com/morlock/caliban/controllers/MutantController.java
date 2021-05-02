package com.morlock.caliban.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morlock.caliban.entities.Mutant;

public interface MutantController {
	List<Mutant> getMutants();

	ResponseEntity<Mutant> addMutant(Mutant mutant);

	String stats() throws JsonProcessingException;


}
