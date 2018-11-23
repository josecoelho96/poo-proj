package maintools;

/**
 * Edge of a GridPoint
 */
public class Edge {

    private int cost;
    private GridPoint dest;

    /**
     * Initializes Edge
     * 
     * @param cost Cost
     * @param dest Destination GridPoint
     */
    public Edge(int cost, GridPoint dest) {
        this.cost = cost;
        this.dest = dest;
    }

    /**
     * Gets cost
     * 
     * @return Cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets cost
     * 
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets destination GridPoint
     * 
     * @return Destination GridPoint
     */
    public GridPoint getDest() {
        return dest;
    }
}
