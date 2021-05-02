package com.morlock.caliban.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.SpringRunner;

import com.morlock.caliban.entities.Mutant;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MutantRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	public MutantRepositoryTest() {

	}

	@Test
	@DisplayName("Validate the creation of the registry")
	public void test_saveOK() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);
		Mutant resultMutant = entityManager.persist(mutant);
		assertEquals(resultMutant.getDna(), mutant.getDna());
	}


}
