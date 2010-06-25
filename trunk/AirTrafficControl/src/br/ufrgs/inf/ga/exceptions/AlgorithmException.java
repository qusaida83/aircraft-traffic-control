package br.ufrgs.inf.ga.exceptions;

/**
 * Thrown if anything bad happen in the genetic algorithm execution.
 * 
 * @author diego
 *
 */
public class AlgorithmException extends Exception {

	public AlgorithmException(String msg, Exception e) {
		super(msg, e);
	}

}
