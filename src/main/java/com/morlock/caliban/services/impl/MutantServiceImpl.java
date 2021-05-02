package com.morlock.caliban.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.exceptions.TechnicalException;
import com.morlock.caliban.interfaces.ICaliban;
import com.morlock.caliban.repositories.MutantRepository;
import com.morlock.caliban.services.MutantService;

import javax.transaction.Transactional;

@Service
@Qualifier("MutantService")
@Transactional
public class MutantServiceImpl implements MutantService {

	private static final Logger logger = LoggerFactory
			.getLogger(com.morlock.caliban.services.impl.MutantServiceImpl.class);
	private static final String UNEXPECTED_ERROR_SAVE = "An unexpected error occurred: ";

	@Autowired(required = false)
	@Qualifier("MutantRepository")
	MutantRepository mutantRepository;

	@Autowired(required = false)
	@Qualifier("caliban")
	private final ICaliban caliban;

	public MutantServiceImpl(ICaliban caliban) {
		super();
		this.caliban = caliban;
	}

	@Override
	public List<Mutant> findAllMutants() {
		return mutantRepository.findAll();
	}

	@Override
	public Mutant saveMutant(Mutant mutantNew) {

		String[] dna = mutantNew.getDna();
		try {
			mutantNew.setConfirmed(caliban.isMutant(dna));
			return mutantRepository.save(mutantNew);
		} catch (TechnicalException e) {
			logger.error(UNEXPECTED_ERROR_SAVE.concat(e.getMessage()));
		}
		return new Mutant();
	}

	public Map<String, Object> stats() {

		List<Mutant> records = mutantRepository.findAll();
		HashMap<String, Object> map = new HashMap<>();
		long count_total;
		long count_mutant_dna;
		long count_human_dna;
		float ratio = 0.0f;

		if (!records.isEmpty()) {
			count_total = records.size();
			count_mutant_dna = records.stream().filter(c -> c.isConfirmed() == true).count();
			count_human_dna = count_total - count_mutant_dna;
			if (count_total > 0) {
				ratio = (count_mutant_dna * 100) / count_total;
			}
			map.put("count_mutant_dna", count_mutant_dna);
			map.put("count_human_dna", count_human_dna);
			map.put("ratio", ratio);
		}
		return map;
	}

}
