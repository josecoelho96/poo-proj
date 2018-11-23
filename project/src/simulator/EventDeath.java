package simulator;

import generictools.Event;
import maintools.Individual;

public class EventDeath extends Event<Individual> {

    /**
     * Initializes EventDeath
     * 
     * @param ind Individual
     * @param timestamp Event timestamp
     */
    public EventDeath(Individual ind, double timestamp) {
        super(ind, timestamp);
    }

    @Override
    public void executeEvent(Object o) {
        Simulation s = (Simulation) o;
        s.getPop().removeIndividual(ind);
    }
}
