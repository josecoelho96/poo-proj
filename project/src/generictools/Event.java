package generictools;

/**
 * Event
 * @param <T>
 */
public abstract class Event<T> {

    protected T ind;
    protected double timestamp;

    /**
     * Initializes event
     * 
     * @param id
     * @param timestamp
     */
    public Event(T id, double timestamp) {
        this.ind = id;
        this.timestamp = timestamp;
    }

    /**
     * Gets Ind
     * 
     * @return
     */
    public T getInd() {
        return ind;
    }

    /**
     * Sets Ind
     * 
     * @param ind
     */
    public void setInd(T ind) {
        this.ind = ind;
    }

    /**
     * Gets timestamp
     *  
     * @return Timestamp
     */
    public double getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp
     * 
     * @param timestamp
     */
    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Executes Event
     *
     * @param o
     */
    public abstract void executeEvent(Object o);
}
