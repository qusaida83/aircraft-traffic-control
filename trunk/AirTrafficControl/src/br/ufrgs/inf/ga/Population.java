package br.ufrgs.inf.ga;

import java.util.Iterator;
import java.util.Set;

public class Population<T> implements Iterable<T> {

	public static final int MAX_INDIVIDUALS = 200;
	
	private final Set<T> individuals;
	
	public Population(Set<T> individuals) {
		this.individuals = individuals;
	}
	
	public void add(T individual) {
		this.individuals.add(individual);
	}
	
	public void remove(T individual) {
		this.individuals.remove(individual);
	}

	@Override
	public Iterator<T> iterator() {
		return individuals.iterator();
	}
	
	
}
