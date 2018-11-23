package simulator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import generictools.Event;
import generictools.Point;
import maintools.Grid;
import maintools.GridPoint;
import maintools.Individual;
import maintools.PEC;
import maintools.Population;

/**
 * Simulation
 */
public class Simulation {

    private int finalInst;
    private double currTime = 0;
    private Point initPoint;
    private Point finalPoint;
    private int initPopulation;
    private int maxPopulation;
    private int comfortSens;
    private int deathParam;
    private int reproductionParam;
    private int moveParam;

    private PEC pec;
    private Population pop;
    private Grid grid;

    private Random random = new Random();

    public Simulation(int finalInst, int initPopulation, int maxPopulation, int comfortSens) {
        super();
        this.finalInst = finalInst;
        this.initPopulation = initPopulation;
        this.maxPopulation = maxPopulation;
        this.comfortSens = comfortSens;
        this.pec = new PEC();
        this.pop = new Population();
    }

    /**
     * Gets death timestamp for an individual
     * 
     * @param ind Individual
     * @return Death timestamp for an individual
     */
    public double getDeathTimestamp(Individual ind) {
        double avg = (1.0 - Math.log(1 - ind.getComfort())) * deathParam;
        return -avg * Math.log(1.0 - random.nextDouble()) + currTime;
    }
    
    /**
     * Gets reproduction timestamp for an individual
     * 
     * @param ind Individual
     * @return Reproduction timestamp for an individual
     */
    public double getReproductionTimestamp(Individual ind) {
        double avg = (1.0 - Math.log(ind.getComfort())) * reproductionParam;
        return -avg * Math.log(1.0 - random.nextDouble()) + currTime;
    }

    /**
     * Gets move timestamp for an individual
     * 
     * @param ind Individual
     * @return Move timestamp for an individual
     */
    public double getMoveTimestamp(Individual ind) {
        double avg = (1.0 - Math.log(ind.getComfort())) * moveParam;
        return -avg * Math.log(1.0 - random.nextDouble()) + currTime;
    }

    /**
     * Checks whether an observation should be done
     * 
     * @param obsCount Number of current observations
     * @return Whether an observation should be done
     */
    public boolean shouldObserve(int obsCount) {
        if (obsCount > 19)
            return false;
        double i = finalInst / 20;

        return currTime < (obsCount + 1) * i && currTime > obsCount * i;
    }

    /**
     * Checks whether the simulation is over
     * 
     * @return Whether the simulation is over
     */
    public boolean isEndOfSimulation() {
        return currTime >= finalInst || pec.getSize() == 0;
    }

    /**
     * Creates initial individuals and corresponding data and events
     */
    public void setInitialPopulation() {
        GridPoint pInit = grid.getGridPoint(initPoint);

        for (int i = 0; i < initPopulation; i++) {
            Individual ind = new Individual(pInit);
            updateIndividualComfort(ind);

            // add events to PEC
            double deathTS = getDeathTimestamp(ind);
            double reproductionTS = getReproductionTimestamp(ind);
            double moveTS = getMoveTimestamp(ind);

            ind.setDeathTimestamp(deathTS);

            Event<Individual> deathEv = new EventDeath(ind, deathTS);
            pec.addEvPEC(deathEv);

            if (reproductionTS < deathTS) {
                Event<Individual> reproductionEv = new EventReproduction(ind, reproductionTS);
                pec.addEvPEC(reproductionEv);
            }

            if (moveTS < deathTS) {
                Event<Individual> moveEv = new EventMove(ind, moveTS);
                pec.addEvPEC(moveEv);
            }

            pop.addIndividual(ind);
        }
    }

    /**
     * Checks whether an epidemic should occur
     * 
     * @return Whether an epidemic should occur 
     */
    public boolean checkForEpidemic() {
        return pop.getSize() > maxPopulation && pop.getSize() > 5;
    }

    /**
     * Triggers an epidemic
     */
    public void triggerEpidemic() {
        Collections.sort(pop.getIndividuals(), Collections.reverseOrder(Comparator.comparing(Individual::getComfort)));

        Random rand = new Random();

        for (int i = 5; i < pop.getSize(); i++) {
            double val = rand.nextDouble();
            Individual ind = pop.getIndividuals().get(i);

            if (ind.getComfort() < val) {
                // kill it
                pec.removeEventsByIndividual(ind);
                pop.removeIndividual(ind);
                i--;
            }
        }

    }

    /**
     * Updates individual comfort
     * 
     * @param ind Individual
     */
    public void updateIndividualComfort(Individual ind) {
        int pathCost = 0;
        int pathLen = ind.getPath().size() == 0 ? 0 : ind.getPath().size() - 1;
        int dist = Math.abs(finalPoint.getX() - ind.getCurrentPathPosition().getX())
                + Math.abs(finalPoint.getY() - ind.getCurrentPathPosition().getY());

        pathCost = ind.getPathCost();

        double comfort = Math.pow(1.0 - (pathCost - pathLen + 2.0) / (((grid.getMaxCost() - 1.0) * pathLen) + 3.0),
                comfortSens) * Math.pow(1.0 - (dist / (grid.getRowsnb() + grid.getColsnb() + 1.0)), comfortSens);

        ind.setComfort(comfort);
    }

    /**
     * Prints observation data
     *
     * @param obsCount Number of observation
     * @param eventCount Number of realized events
     * @param fpHit Whether the final point has been hit
     * @param bf Best fit individual
     */
    public void printObservation(int obsCount, int eventCount, boolean fpHit, Individual bf) {
        System.out.println("Observation number: " + (obsCount + 1));
        System.out.println("\tPresent instant: " + this.getCurrTime());
        System.out.println("\tNumber of realized events: " + eventCount);
        System.out.println("\tPopulation size: " + this.getPop().getSize());
        System.out.println("\tFinal point has been hit: " + (fpHit ? "yes" : "no"));
        System.out.println("\tPath of the best fit individual: {" + bf.toString() + "}");
        System.out.println(
                "\t" + (fpHit ? "Cost" : "Comfort") + ": " + (fpHit ? bf.getPathCost() : bf.getComfort()) + "\n");
    }
    
    /**
     * Sets move parameter
     * 
     * @param moveParam
     */
    public void setMoveParam(int moveParam) {
        this.moveParam = moveParam;
    }

    /**
     * Sets death parameter
     * 
     * @param deathParam
     */
    public void setDeathParam(int deathParam) {
        this.deathParam = deathParam;
    }

    /**
     * Sets reproduction parameter
     * 
     * @param reproductionParam
     */
    public void setReproductionParam(int reproductionParam) {
        this.reproductionParam = reproductionParam;
    }
    
    /**
     * Gets current simulation time
     * 
     * @return Current simulation time
     */
    public double getCurrTime() {
        return currTime;
    }

    /**
     * Sets current simulation time
     * 
     * @param currTime
     */
    public void setCurrTime(double currTime) {
        this.currTime = currTime;
    }

    /**
     * Gets initial point
     * 
     * @return Initial point
     */
    public Point getInitPoint() {
        return initPoint;
    }

    /**
     * Sets initial point
     * 
     * @param initPoint
     */
    public void setInitPoint(Point initPoint) {
        this.initPoint = initPoint;
    }

    /**
     * Gets final point
     * 
     * @return Final point
     */
    public Point getFinalPoint() {
        return finalPoint;
    }

    /**
     * Sets final point
     * 
     * @param finalPoint
     */
    public void setFinalPoint(Point finalPoint) {
        this.finalPoint = finalPoint;
    }

    /**
     * Gets PEC instance
     * 
     * @return PEC instance
     */
    public PEC getPec() {
        return pec;
    }

    /**
     * Gets Population instance
     * 
     * @return Population instance
     */
    public Population getPop() {
        return pop;
    }

    /**
     * Sets grid
     * 
     * @param grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
