package br.ufrgs.inf.ga.model;

/**
 * Abstracts a parents relation.
 * 
 * @author diego
 *
 */
public class Parents {

	private final Individual parent1;
	private final Individual parent2;
	
	public Parents(final Individual parent1, final Individual parent2) {
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public Individual getParent1() {
		return parent1;
	}

	public Individual getParent2() {
		return parent2;
	}
}
