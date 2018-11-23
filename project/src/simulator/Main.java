package simulator;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import generictools.Event;
import maintools.Individual;

public class Main {

    /**
     * Main entry point
     *
     * @param args
     */
    public static void main(String[] args) {
        Simulation sim;
        int obsCount = 0, eventCount = 0;

        // Check arguments
        if (args.length != 1) {
            System.out.println("USAGE: simulator <filename>");
            System.exit(-1);
        }

        sim = parseInput(new File(args[0]));

        // Simulation start
        Event<Individual> currEvent;

        Individual ind = null;
        boolean reachedFinalPoint = false;

        while (!sim.isEndOfSimulation()) {
            currEvent = sim.getPec().nextEvent();
            sim.setCurrTime(currEvent.getTimestamp());

            // Simulate current Event
            currEvent.executeEvent(sim);
            eventCount++;

            // Check if individual arrived at final point
            if (currEvent.getInd().hasLastPoint(sim.getFinalPoint())) {
                if (!reachedFinalPoint) {
                    ind = currEvent.getInd().clone();
                    reachedFinalPoint = true;
                } else {
                    // Check if new individual has a lower cost path
                    if (ind == null || currEvent.getInd().getPathCost() < ind.getPathCost()) {
                        ind = currEvent.getInd().clone();
                    }
                }

            } else if (!reachedFinalPoint) {
                // Save individual with greater comfort
                if (ind == null || currEvent.getInd().getComfort() > ind.getComfort()) {
                    ind = currEvent.getInd().clone();
                }
            }

            if (sim.checkForEpidemic()) {
                sim.triggerEpidemic();
            }

            if (sim.shouldObserve(obsCount)) {
                sim.printObservation(obsCount, eventCount, reachedFinalPoint, ind);
                obsCount++;
            }
        }

        if (ind == null) {
            System.out.println("Error! Simulation didn't have anything to simulate.");
            System.exit(-1);
        } else {
            System.out.println("Path of the best fit individual = {" + ind.toString() + "}");
        }
    }

    /**
     * Loads and parses XML input
     *
     * @param inputFile File containing XML simulation data
     * @return Simulation
     */
    private static Simulation parseInput(File inputFile) {
        SAXParser saxParser;

        // Build the SAX parser
        SAXParserFactory fact = SAXParserFactory.newInstance();
        fact.setValidating(true);

        try {
            saxParser = fact.newSAXParser();
            // Parse XML input document
            InputHandler handler = new InputHandler();

            saxParser.parse(inputFile, handler);
            return handler.getSim();
        } catch (IOException e) {
            System.out.println("Error! Failed to import XML.");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("Error! Failed to parse XML.");
            System.exit(-1);
        }

        return null;
    }
}
