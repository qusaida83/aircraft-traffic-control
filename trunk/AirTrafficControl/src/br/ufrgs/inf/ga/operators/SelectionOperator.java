package br.ufrgs.inf.ga.operators;

import java.util.LinkedList;
import java.util.List;

import br.ufrgs.inf.ga.model.Parents;
import br.ufrgs.inf.ga.model.Population;

/**
 * Implements a genetic selection operation.
 * 
 * @author diego
 *
 */
public class SelectionOperator {

	/**
	 * Selects a list of parents to reproduction.
	 * 
	 * <p>
	 * The selected parents are the most adapted in the population.
	 * The number of selected parents is equal to <code>(int)(population.MAX_INDIVIDUALS * population.REPRODUCTION_RATE)</code>
	 * </p>
	 * 
	 * @param population population used to select the parents.
	 * @return list of selected parents.
	 */
	public List<Parents> selectParents(Population population) {
		List<Parents> selectedParents = new LinkedList<Parents>();
		
		int selectedIndividualsCount = (int)(Population.MAX_INDIVIDUALS * Population.REPRODUCTION_RATE);

		// sorts the population (asc by fitness value) and select just the most adapted ones.
		population.sortByFitness();
		
		// selects the individuals and create the parents
		for(int i = 0; i < selectedIndividualsCount; i++) {
			Parents parents = new Parents(population.get(i), population.get(i + 1));
			selectedParents.add(parents);
		}
		
		return selectedParents;
	}
}
