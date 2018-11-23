package maintools;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import generictools.Event;

/**
 * Pending Event Container
 */
public class PEC {

    Comparator<Event<Individual>> comparator;
    PriorityQueue<Event<Individual>> queue;

    /**
     * Initializes PEC
     */
    public PEC() {
        comparator = new PECComparator();
        queue = new PriorityQueue<Event<Individual>>(10, comparator);
    }

    /**
     * Gets queue
     * 
     * @return Queue
     */
    public PriorityQueue<Event<Individual>> getQueue() {
        return queue;
    }

    /**
     * Adds event
     * 
     * @param ev
     */
    public void addEvPEC(Event<Individual> ev) {
        queue.add(ev);
    }

    /**
     * Gets size
     * 
     * @return Size
     */
    public int getSize() {
        return queue.size();
    }

    /**
     * Gets next event and removes it from the queue
     * 
     * @return
     */
    public Event<Individual> nextEvent() {
        return queue.remove();
    }

    /**
     * Removes events by an individual
     * 
     * @param ind
     */
	public void removeEventsByIndividual(Individual ind) {
		Iterator<Event<Individual>> itr = queue.iterator();
	    
		while(itr.hasNext()) {
			Event<Individual> e = itr.next();
			if (e.getInd().equals(ind)) {
				itr.remove();
			}
		}
	}
}
