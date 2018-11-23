package maintools;

import java.util.ArrayList;
import java.util.List;

/**
 * Population is a List of Individuals
 */
public class Population {

    private List<Individual> individuals;

    /**
     * Initializes Population
     */
    public Population() {
        individuals = new ArrayList<Individual>();
    }

    /**
     * Adds Individual
     * 
     * @param i
     */
    public void addIndividual(Individual i) {
        individuals.add(i);
    }

    /**
     * Removes Individual
     * 
     * @param i
     */
    public void removeIndividual(Individual i) {
        individuals.remove(i);
    }

    /**
     * Gets Individuals
     * 
     * @return Individuals
     */
    public List<Individual> getIndividuals() {
        return individuals;
    }

    /**
     * Sets Individuals
     * 
     * @param individuals
     */
    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    /**
     * Gets size
     * 
     * @return Size
     */
    public int getSize() {
    	return individuals.size();
    }
}
