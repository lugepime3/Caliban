package com.morlock.caliban.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.morlock.caliban.entities.Mutant;
import com.morlock.caliban.exceptions.TechnicalException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalibanImplTest {
	CalibanImpl c;

	@Before
	public void init() {
		c = new CalibanImpl();
	}

	@Test
	@DisplayName("Is a Mutant")
	public void test_isMutantOK() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean currency = c.isMutant(dna);
		assertTrue(currency);
	}

	@Test
	@DisplayName("Not Is a Mutant is A Human")
	public void test_isMutan_HumanOK() throws TechnicalException {
		String[] dna = { "CTGCGA", "CAGTGC", "TTATGT", "AGAAAG", "ACCCTA", "TCACTG" };
		boolean currency = c.isMutant(dna);
		assertFalse(currency);
	}

	@Test(expected = TechnicalException.class)
	@DisplayName("dna is Empty")
	public void test_isMutantFail_DNAEMPTY() throws TechnicalException {
		String[] dna = {};
		c.isMutant(dna);
	}

	@Test
	@DisplayName("dna caharactes in rows are Incomplete")
	public void test_isMutantFail() throws TechnicalException {
		String[] dna = { "TGC", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean currency = c.isMutant(dna);
		assertFalse(currency);
	}

	@Test
	@DisplayName("dna rows are Incomplete")
	public void test_isMutantFail_DNA_Incomplete() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA" };
		boolean currency = c.isMutant(dna);
		assertFalse(currency);
	}

	@Test
	@DisplayName("Is a Mutant, dna have Distinct Characters")
	public void test_isMutantOK_DistinctCharacters() throws TechnicalException {
		String[] dna = { "18G3G1", "31G8G3", "8818G8", "1G11GG", "333381", "83138G" };
		boolean currency = c.isMutant(dna);
		assertTrue(currency);
	}

	@Test
	@DisplayName("Is a Mutant,dna row and characters are Extra Dimension 7x7")
	public void test_isMutantOK_ExtraDimension() throws TechnicalException {
		String[] dna = { "ATGCGAA", "CAGTGCA", "TTATGTA", "AGAAGGA", "CCCCTAA", "TCACTGA", "CCCCTAA" };
		boolean currency = c.isMutant(dna);
		assertTrue(currency);
	}

	@Test
	@DisplayName("Is a Mutant,Minimal Dimension 4x4")
	public void test_isMutantOK_MinimalDimension() throws TechnicalException {
		String[] dna = { "ATGC", "CAGT", "TTAT", "AGAA" };
		boolean currency = c.isMutant(dna);
		assertTrue(currency);
	}

	@Test
	@DisplayName("Not Is a Mutant,is Down of Minimal Dimension 4x4")
	public void test_isMutantOK_DownMinimalDimension() throws TechnicalException {
		String[] dna = { "ATGC", "CAGT", "TTAT" };
		boolean currency = c.isMutant(dna);
		assertFalse(currency);
	}

	@Test
	@DisplayName("Is a Mutant,mutageno is present in diagonal row")
	public void test_isPatternInDiagonalOK() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInDiagonal();
		assertTrue(currency);
	}

	@Test
	@DisplayName("No Is a Mutant,mutageno is not present in diagonal row")
	public void test_isPatternInDiagonalFail() throws TechnicalException {
		String[] dna = { "TTGCGA", "CAGTGC", "TTATGT", "AG2ATG", "CTCCTA", "TCGCTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInDiagonal();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Is a Mutant,mutageno is  present in Column diagonal row")
	public void test_isPatternInVerticalDiagonalOK() throws TechnicalException {
		String[] dna = { "TTGCGA", "TAGTGC", "TTATGT", "TG2ATG", "CTCCTA", "TCGCTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInVerticalDiagonal();
		assertTrue(currency);
	}

	@Test
	@DisplayName("No Is a Mutant,mutageno is not present in Column diagonal row")
	public void test_isPatternInVerticalDiagonalFail() throws TechnicalException {
		String[] dna = { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInVerticalDiagonal();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Is a Mutant,mutageno is  present in Right To Left diagonal row")
	public void test_isPatternInRightToLeftDiagonalOK() throws TechnicalException {
		String[] dna = { "ATGCAA", "CAGTAC", "TTAAGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInRightToLeftDiagonal();
		assertTrue(currency);
	}

	@Test
	@DisplayName("No Is a Mutant,mutageno is not present in Right To Left diagonal row")
	public void test_isPatternInRightToLeftDiagonalFail() throws TechnicalException {
		String[] dna = { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInRightToLeftDiagonal();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Is a Mutant,mutageno is  present in Left To Right diagonal row")
	public void test_isPatternInLeftToRightDiagonalOK() throws TechnicalException {
		String[] dna = { "ATGCAA", "CAGTAC", "TTAAGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInLeftToRightDiagonal();
		assertTrue(currency);
	}

	@Test
	@DisplayName("No Is a Mutant,mutageno is not present in Left To Right diagonal row")
	public void test_isPatternInLeftToRightDiagonalFail() throws TechnicalException {
		String[] dna = { "ATGCAA", "CCGTAC", "TTAAGT", "AGATGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInLeftToRightDiagonal();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Check the error status in the component: init")
	public void test_CalibanImpl() {
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Check the error status in the component: set property")
	public void test_setErrorInComponent() {
		c.setErrorInComponent(false);
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Create the matrix and check the status of the component")
	public void test_createMatrixOK() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Try Create the matrix and check the status of the component")
	public void test_createMatrixFail() throws TechnicalException {
		String[] dna = { "ATGCGA" };
		c.createMatrix(dna);
		boolean currency = c.isErrorInComponent();
		assertTrue(currency);
	}

	@Test
	@DisplayName("Check the length of the matrix")
	public void test_getMatrixLength() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		c.startComponent(dna);
		int expected = 6;
		int currency = c.getMatrixLength();
		assertEquals(expected, currency);
	}

	@Test
	@DisplayName("Extract characters from string(rows)")
	public void test_extractCharToPatternOK() throws TechnicalException {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		c.extractCharToPattern();
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	@DisplayName("Try Extract characters from string(rows)")
	public void test_extractCharToPatternFail() throws TechnicalException {
		String[] dna = { "ATGCGA" };
		c.createMatrix(dna);
		c.extractCharToPattern();
		boolean currency = c.isErrorInComponent();
		assertTrue(currency);
	}

	@Test(expected = TechnicalException.class)
	@DisplayName("Throw exception when character is white")
	public void test_createDiagonalException() throws TechnicalException {
		char ch = ' ';
		c.createDiagonal(ch);
	}

	@Test(expected = TechnicalException.class)
	@DisplayName("Throw exception when rows are empty")
	public void test_createMatrixException() throws TechnicalException {
		String[] entry = {};
		c.createMatrix(entry);
	}

	// entity
	@Test
	@DisplayName("valid string length dna")
	public void test_getDnaOK() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean result;
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);
		result = mutant.getDna().length == 6;
		assertTrue(result);
	}

	@Test
	@DisplayName("Try valid string length dna")
	public void test_getDnaFail() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA" };
		boolean result;
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);
		result = mutant.getDna().length == 6;
		assertFalse(result);
	}

	@Test
	@DisplayName("validate the confirmed field(is mutant or not)")
	public void test_isConfirmedOK() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(true);
		assertTrue(mutant.isConfirmed());
	}

	@Test
	@DisplayName("Try validate the confirmed field(is mutant or not)")
	public void test_isConfirmedFail() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);

		assertFalse(mutant.isConfirmed());
	}

	@Test
	@DisplayName("validate the creation of the hashcode in the entity")
	public void test_Mutant_hashCodeOk() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);
		boolean result = mutant.hashCode() > 0;
		assertTrue(result);
	}

	@Test
	@DisplayName("validate the creation of the equals in the entity")
	public void test_Mutant_equalsOK() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);
		Mutant mutantCopy = mutant;
		boolean result = mutant.equals(mutantCopy);
		assertTrue(result);
	}

	@Test
	@DisplayName("Try validate the creation of the equals in the entity")
	public void test_Mutant_equals_Null() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);
		Mutant mutantCopy = null;
		boolean result = mutant.equals(mutantCopy);
		assertFalse(result);
	}

	@Test
	@DisplayName("Try validate equals between different objects type")
	public void test_Mutant_equals_Distinct_Object() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);
		String anyThing = "I'm a String...";
		boolean result = mutant.equals(anyThing);
		assertFalse(result);
	}

	@Test
	@DisplayName("Validate the Create toString method")
	public void test_Mutant_toString() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setConfirmed(false);
		assertFalse(mutant.toString().isEmpty());
	}

}//
