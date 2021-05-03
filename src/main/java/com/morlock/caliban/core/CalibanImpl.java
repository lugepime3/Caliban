package com.morlock.caliban.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.morlock.caliban.exceptions.TechnicalException;
import com.morlock.caliban.interfaces.ICaliban;

@SuppressWarnings("JavaDoc")
@Component
@Qualifier("caliban")
public class CalibanImpl implements ICaliban {
	private static final Logger logger = LoggerFactory.getLogger(CalibanImpl.class);

	public static final int PATTERN_LENGHT = 4;
	private static final String UNEXPECTED_ERROR = "An unexpected error occurred: ";
	private static final String DETECTED_ERROR = "An error was detected in the component... ";
	private char[][] matrix;
	private int matrixLength;
	private Set<String> charToPattern;
	private List<String> patterns;
	private StringBuilder sbDiagonal;
	private boolean isErrorInComponent;

	public CalibanImpl() {
		super();
		this.isErrorInComponent = false;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public boolean isErrorInComponent() {
		return isErrorInComponent;
	}

	/**
	 * 
	 * Global component error indicator
	 * 
	 * @param isErrorInComponent
	 */

	public void setErrorInComponent(boolean isErrorInComponent) {
		this.isErrorInComponent = isErrorInComponent;
	}

	/**
	 * Start Component the search process: *Build the matrix *initialize variables
	 * *Build the search patterns
	 * 
	 * @param entry
	 * @throws TechnicalException
	 */
	@Override
	public void startComponent(String[] entry) throws TechnicalException {
		this.createMatrix(entry);
		this.matrixLength = this.matrix.length;
		this.charToPattern = new HashSet<>();
		this.patterns = new ArrayList<>();
		this.sbDiagonal = new StringBuilder();
		this.builtPatters();
	}

	/**
	 * Validate the dimensions of the matrix
	 * 
	 * @param dimension
	 * @param row
	 * @return
	 */
	@Override
	public boolean isInvalidDimension(int dimension, String row) {
		boolean invalid = dimension != row.length();
		this.setErrorInComponent(invalid);
		return invalid;
	}

	/**
	 * Build the matrix
	 */
	@Override
	public void createMatrix(String[] entry) throws TechnicalException {
		if (entry.length == 0) {
			this.setErrorInComponent(true);
			throw new TechnicalException("There are no inputs to analyze");
		}
		try {
			List<String> results = Arrays.asList(entry);
			int dimension = results.size();
			this.matrix = new char[dimension][dimension];
			int x = 0;
			for (String s : results) {
				for (int y = 0; y < s.length(); y++) {
					this.matrix[x][y] = s.charAt(y);
					if (this.isInvalidDimension(dimension, s)) {
						this.setErrorInComponent(true);
						throw new TechnicalException(
								"The matrix must be Square: N: " + dimension + " x N: " + s.length());
					}
				}
				x++;
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}

	}

	/**
	 * Returns the size of the matrix
	 * 
	 * @return
	 */
	@Override
	public int getMatrixLength() {
		return this.matrixLength;
	}

	/**
	 * Extract characters from the row to generate the search pattern
	 */
	@Override
	public void extractCharToPattern() {
		String element;
		try {
			for (int i = 0; i < this.getMatrixLength(); i++) {
				for (int j = 0; j < this.getMatrixLength(); j++) {
					element = Character.toString(this.matrix[i][j]);
					this.charToPattern.add(element);
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
	}

	/**
	 * Generate the search pattern
	 */
	@Override
	public void builtPatters() {
		this.extractCharToPattern();
		try {
			for (String element : this.charToPattern) {
				String pattern = String.format("%" + PATTERN_LENGHT + "s", element).replace(" ", element);
				this.patterns.add(pattern);
			}

		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}

	}

	/**
	 * Create the diagonal of the matrix by appending characters
	 */
	@Override
	public void createDiagonal(char c) throws TechnicalException {
		if (c == ' ') {
			this.setErrorInComponent(true);
			throw new TechnicalException("The diagonal must not contain blank characters");
		}
		try {
			this.sbDiagonal.append(c);
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}

	}

	/**
	 * returns the length of the diagonal in the matrix
	 */
	@Override
	public int getLengthDiagonal() {
		try {
			return this.getContentDiagonal().length();
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return 0;
	}

	/**
	 * Returns the content of the diagonal in the matrix
	 */
	@Override
	public String getContentDiagonal() {
		try {
			return this.sbDiagonal.toString();
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return "";
	}

	/**
	 * reset the values of the diagonal in the matrix
	 */
	@Override
	public void resetDiagonal() {
		try {
			this.sbDiagonal = new StringBuilder();
			this.getContentDiagonal();
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
	}

	/**
	 * Look for the pattern on the diagonal of the matrix
	 */
	@Override
	public boolean isPresentPattern() {
		try {
			for (String p : this.patterns) {
				if (this.getContentDiagonal().toUpperCase().contains(p.toUpperCase())) {
					return true;
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return false;
	}

	/**
	 * Look for the pattern on the oblique diagonal from left to right
	 */
	@Override
	public boolean isPatternInLeftToRightDiagonal() {
		int x1;
		int y1;
		try {
			for (int i = 0; i < this.getMatrixLength(); i++) {
				for (int j = 0; j < this.getMatrixLength(); j++) {
					x1 = i;
					y1 = j;
					this.resetDiagonal();
					while (x1 < this.getMatrixLength() && y1 < this.getMatrixLength()) {
						this.createDiagonal(this.matrix[x1][y1]);
						x1++;
						y1++;
					}
					if (this.getLengthDiagonal() >= PATTERN_LENGHT) {
						if (this.isPresentPattern()) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return false;
	}

	/**
	 * Look for the pattern on the oblique diagonal from right to left
	 */
	@Override
	public boolean isPatternInRightToLeftDiagonal() {
		int x1;
		int y1;
		try {
			int auxiliarLenght = this.getMatrixLength() - 1;
			for (int i = 0; i < this.getMatrixLength(); i++) {
				for (int j = auxiliarLenght; j >= 0; j--) {
					x1 = i;
					y1 = j;
					this.resetDiagonal();
					while (x1 < this.getMatrixLength() && y1 >= 0) {
						this.createDiagonal(this.matrix[x1][y1]);
						x1++;
						y1--;
					}
					if (this.getLengthDiagonal() >= PATTERN_LENGHT) {
						if (this.isPresentPattern()) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return false;
	}

	/**
	 * Look for the pattern on the vertical diagonal
	 */
	@Override
	public boolean isPatternInVerticalDiagonal() {
		try {
			for (int i = 0; i < this.getMatrixLength(); i++) {
				this.resetDiagonal();
				for (int j = 0; j < this.matrix[i].length; j++) {
					this.createDiagonal(this.matrix[j][i]);
				}
				if (this.isPresentPattern()) {
					return true;
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return false;
	}

	/**
	 * Look for the pattern on the horizontal diagonal
	 */
	@Override
	public boolean isPatternInHorizontalDiagonal() {
		try {
			for (int i = 0; i < this.getMatrixLength(); i++) {
				this.resetDiagonal();
				for (int j = 0; j < this.matrix[i].length; j++) {
					this.createDiagonal(this.matrix[i][j]);
				}
				if (this.isPresentPattern()) {
					return true;
				}
			}
		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}
		return false;
	}

	/**
	 * verify that the pattern exists in one of the diagonals
	 */
	@Override
	public boolean isPatternInDiagonal() {
		if (this.isErrorInComponent) {
			logger.error(DETECTED_ERROR);
			return false;
		}
		try {
			if (this.isPatternInHorizontalDiagonal()) {
				return true;
			}
			if (this.isPatternInVerticalDiagonal()) {
				return true;
			}
			if (this.isPatternInRightToLeftDiagonal()) {
				return true;
			}
			if (this.isPatternInLeftToRightDiagonal()) {
				return true;
			}

		} catch (Exception e) {
			this.setErrorInComponent(true);
			logger.error(UNEXPECTED_ERROR.concat(e.getMessage()));
		}

		return false;
	}

	/**
	 * Search a matrix and validate that it is mutant
	 */
	@Override
	public boolean isMutant(String[] entry) throws TechnicalException {
		this.startComponent(entry);
		return this.isPatternInDiagonal();
	}

}//
