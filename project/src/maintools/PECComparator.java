package maintools;

import java.util.Comparator;

import generictools.Event;

/**
 * PECComparator
 */
public class PECComparator implements Comparator<Event<Individual>> {

    @Override
    public int compare(Event<Individual> x, Event<Individual> y) {
        if (x == null || y == null) {
            return 0;
        }

        if (x.getTimestamp() < y.getTimestamp()) {
            return -1;
        }
        if (x.getTimestamp() > y.getTimestamp()) {
            return 1;
        }
        return 0;
    }
}
