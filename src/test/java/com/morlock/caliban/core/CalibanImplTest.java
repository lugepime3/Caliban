package com.morlock.caliban.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.morlock.caliban.exceptions.TechnicalException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalibanImplTest {

	@Test
	public void test_isMutantOK() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean currency = c.isMutant(dna);
		assertTrue(currency);
	}

	@Test
	public void test_isMutantFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "123", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean currency = c.isMutant(dna);
		assertFalse(currency);
	}

	@Test
	public void test_isPatternInDiagonal() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInDiagonal();
		assertTrue(currency);
	}

	@Test
	public void test_isPatternInDiagonalFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "TTGCGA", "CAGTGC", "TTATGT", "AG2ATG", "CTCCTA", "TCGCTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInDiagonal();
		assertFalse(currency);
	}

	@Test
	public void test_isPatternInVerticalDiagonal() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "TTGCGA", "CAGTGC", "TTATGT", "AG2ATG", "CTCCTA", "TCGCTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInVerticalDiagonal();
		assertFalse(currency);
	}

	@Test
	public void test_isPatternInVerticalDiagonalFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInVerticalDiagonal();
		assertFalse(currency);
	}

	@Test
	public void test_isPatternInRightToLeftDiagonalFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.startComponent(dna);
		boolean currency = c.isPatternInRightToLeftDiagonal();
		assertFalse(currency);
	}

	@Test
	public void test_CalibanImpl() {
		CalibanImpl c = new CalibanImpl();
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	public void test_setErrorInComponent() {
		CalibanImpl c = new CalibanImpl();
		c.setErrorInComponent(false);
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	public void test_createMatrixOK() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	public void test_createMatrixFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA" };
		c.createMatrix(dna);
		boolean currency = c.isErrorInComponent();
		assertTrue(currency);
	}

	@Test
	public void test_getMatrixLength() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		c.startComponent(dna);
		int expected = 6;
		int currency = c.getMatrixLength();
		assertEquals(expected, currency);
	}

	@Test
	public void test_extractCharToPatternOK() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		c.createMatrix(dna);
		c.extractCharToPattern();
		boolean currency = c.isErrorInComponent();
		assertFalse(currency);
	}

	@Test
	public void test_extractCharToPatternFail() throws TechnicalException {
		CalibanImpl c = new CalibanImpl();
		String[] dna = { "ATGCGA" };
		c.createMatrix(dna);
		c.extractCharToPattern();
		boolean currency = c.isErrorInComponent();
		assertTrue(currency);
	}

	@Test(expected = TechnicalException.class)  
	public void test_createDiagonalException() throws TechnicalException{
		CalibanImpl c = new CalibanImpl();
		char ch = ' ';
		c.createDiagonal(ch);
	}
	
	@Test(expected = TechnicalException.class)  
	public void test_createMatrixException() throws TechnicalException{
		CalibanImpl c = new CalibanImpl();
		String[] entry = {};
		c.createMatrix(entry);
	}


	
	
}//
