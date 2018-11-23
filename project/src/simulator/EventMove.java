package simulator;

import generictools.Event;
import maintools.GridPoint;
import maintools.Individual;

import java.util.ArrayList;
import java.util.Random;

public class EventMove extends Event<Individual> {

    /**
     * Initializes EventMove
     * 
     * @param ind Individual
     * @param timestamp Event timestamp
     */
    public EventMove(Individual ind, double timestamp) {
        super(ind, timestamp);
    }

    @Override
    public void executeEvent(Object o) {
        Random rand = new Random();
        Simulation s = (Simulation) o;

        // Store the adjacent (noObst) Points the Individual has currently
        ArrayList<GridPoint> validPoints = ind.validPoints();

        // If it can move
        if (!validPoints.isEmpty()) {
            double i = rand.nextDouble();

            for (int j = 1; j <= validPoints.size(); j++) {
                if ((double) (j - 1.0) / validPoints.size() < i && i <= (double) j / validPoints.size()) {
                    ind.addToPath(validPoints.get(j - 1));
                    break;
                }
            }
            // Update comfort
            s.updateIndividualComfort(ind);

            double moveTS = s.getMoveTimestamp(ind);

            // Add move event to PEC if it is simulated before the death one
            if (moveTS < ind.getDeathTimestamp()) {
                Event<Individual> moveEv = new EventMove(ind, moveTS);
                s.getPec().addEvPEC(moveEv);
            }
        }
    }
}
