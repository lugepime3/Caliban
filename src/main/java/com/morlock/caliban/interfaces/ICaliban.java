package com.morlock.caliban.interfaces;

import com.morlock.caliban.exceptions.TechnicalException;

public interface ICaliban {
	void startComponent(String[] entry) throws TechnicalException;

	void createMatrix(String[] entry) throws TechnicalException;

	boolean isInvalidDimension(int dimension, String row);

	int getMatrixLength();

	void extractCharToPattern();

	void builtPatters();

	void createDiagonal(char c) throws TechnicalException;

	int getLengthDiagonal();

	String getContentDiagonal();

	void resetDiagonal();

	boolean isPresentPattern();

	boolean isPatternInLeftToRightDiagonal() throws TechnicalException;

	boolean isPatternInRightToLeftDiagonal() throws TechnicalException;

	boolean isPatternInVerticalDiagonal() throws TechnicalException;

	boolean isPatternInHorizontalDiagonal() throws TechnicalException;

	boolean isPatternInDiagonal() throws TechnicalException;

	boolean isMutant(String[] entry) throws TechnicalException;

}
