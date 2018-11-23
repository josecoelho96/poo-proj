package simulator;

import java.util.ArrayList;

import generictools.Event;
import maintools.GridPoint;
import maintools.Individual;

public class EventReproduction extends Event<Individual> {

    /**
     * Initializes EventReproduction
     * 
     * @param ind Individual
     * @param timestamp Event timestamp
     */
    public EventReproduction(Individual ind, double timestamp) {
        super(ind, timestamp);
    }

    @Override
    public void executeEvent(Object o) {
        Simulation s = (Simulation) o;

        // New Individual
        
        // Calculate new path from parent
        ArrayList<GridPoint> parentPath = ind.getPath();
        ArrayList<GridPoint> childPath = new ArrayList<GridPoint>(parentPath);

        int childPathPos = (int) Math.ceil((0.9 + 0.1 * ind.getComfort()) * parentPath.size());
        for (int i = parentPath.size() - 1; i > childPathPos; i--) {
            childPath.remove(i);
        }

        Individual childInd = new Individual(childPath.get(childPath.size() - 1));
        s.updateIndividualComfort(childInd);
        childInd.setPath(childPath);

        // Add new individual's events to PEC
        double deathTS = s.getDeathTimestamp(childInd);
        double reproductionTS = s.getReproductionTimestamp(childInd);
        double moveTS = s.getMoveTimestamp(childInd);

        childInd.setDeathTimestamp(deathTS);

        Event<Individual> deathEv = new EventDeath(childInd, deathTS);
        s.getPec().addEvPEC(deathEv);

        if (reproductionTS < deathTS) {
            Event<Individual> reproductionEv = new EventReproduction(childInd, reproductionTS);
            s.getPec().addEvPEC(reproductionEv);
        }

        if (moveTS < deathTS) {
            Event<Individual> moveEv = new EventMove(childInd, moveTS);
            s.getPec().addEvPEC(moveEv);
        }

        s.getPop().addIndividual(childInd);

        // Add parent reproduction event to PEC if it is simulated before the death one
        double parentReproductionTS = s.getReproductionTimestamp(ind);

        if (parentReproductionTS < ind.getDeathTimestamp()) {
            Event<Individual> parentReproductionEv = new EventReproduction(ind, parentReproductionTS);
            s.getPec().addEvPEC(parentReproductionEv);
        }
    }
}
