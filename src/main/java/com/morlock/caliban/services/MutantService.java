package com.morlock.caliban.services;

import java.util.List;
import java.util.Map;

import com.morlock.caliban.entities.Mutant;

public interface MutantService {
	List<Mutant> findAllMutants();

	Mutant saveMutant(Mutant mutantNew);

	public Map<String, Object> stats();

}
