package maintools;

import java.util.List;

import generictools.Point;

/**
 * GridPoint
 */
public class GridPoint extends Point {

    private boolean isObstacle = false;
    private List<Edge> edges;

    /**
     * Initializes GridPoint
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @param isObstacle Whether it is an obstacle
     */
    public GridPoint(int x, int y, boolean isObstacle) {
        super(x, y);
        this.isObstacle = isObstacle;
    }

    /**
     * Gets isObstacle
     * 
     * @return Whether the GridPoint is an obstacle
     */
    public boolean isObstacle() {
        return isObstacle;
    }

    /**
     * Sets isObstacle
     * 
     * @param isObstacle Boolean of whether the GridPoint is an obstacle or not
     */
    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    /**
     * Gets edge that matches the provided GridPoint Point
     * 
     * @param gp GridPoint
     * @return Edge
     */
    public Edge getEdge(GridPoint gp) {
        for (Edge e : edges) {
            if (e.getDest() == gp)
                return e;
        }
        return null;
    }

    /**
     * Sets list of edges
     * 
     * @param edges List of edges
     */
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Gets list of edges
     * 
     * @return List of edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Sets new edge cost if higher than previous one
     * 
     * @param pEdge Edge point
     * @param cost Edge cost
     */
    public void setEdgeCostIfHigher(Point pEdge, int cost) {
        for (Edge iedge : edges) {
            if (pEdge == iedge.getDest()) {
                if (cost > iedge.getCost()) {
                    iedge.setCost(cost);
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[" + this.getX() + "," + this.getY() + "]";
    }
}
