package maintools;

import java.util.ArrayList;
import java.util.List;

import generictools.Point;

/**
 * Grid of (rows x cols) size with GridPoints and Edges with variable cost
 */
public class Grid {

    private int colsnb;
    private int rowsnb;
    private int maxCost = 1;
    private GridPoint[][] points;

    /**
     * Initializes Grid
     * 
     * @param colsnb Number of grid columns
     * @param rowsnb Number of grid rows
     */
    public Grid(int colsnb, int rowsnb) {
        this.colsnb = colsnb;
        this.rowsnb = rowsnb;

        points = new GridPoint[colsnb][rowsnb];

        // Create grid points
        for (int i = 0; i < colsnb; i++) {
            for (int j = 0; j < rowsnb; j++) {
                points[i][j] = new GridPoint(i + 1, j + 1, false);
            }
        }

        // Create grid edges
        for (int i = 0; i < colsnb; i++) {
            for (int j = 0; j < rowsnb; j++) {
                List<Edge> edges = new ArrayList<>();
                if (i > 0) {
                    edges.add(new Edge(1, points[i - 1][j]));
                }
                if (j > 0) {
                    edges.add(new Edge(1, points[i][j - 1]));
                }
                if (i < colsnb - 1) {
                    edges.add(new Edge(1, points[i + 1][j]));
                }
                if (j < rowsnb - 1) {
                    edges.add(new Edge(1, points[i][j + 1]));
                }
                points[i][j].setEdges(edges);
            }
        }
    }
    
    /**
     * Sets maximum cost of edge
     * 
     * @param maxCost
     */
    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    /**
     * Gets maximum cost of edge
     * 
     * @return Maximum cost of edge
     */
    public int getMaxCost() {
        return maxCost;
    }

    /**
     * Gets number of grid columns
     * 
     * @return Number of grid columns
     */
    public int getColsnb() {
        return colsnb;
    }

    /**
     * Gets number of grid rows
     * 
     * @return Number of grid rows
     */
    public int getRowsnb() {
        return rowsnb;
    }

    /**
     * Gets GridPoint from Point
     * 
     * @param p Point
     * @return GridPoint
     */
    public GridPoint getGridPoint(Point p) {
        return points[p.getX() - 1][p.getY() - 1];
    }

    /**
     * Sets GridPoint obstacle from Point
     * 
     * @param p Point
     */
    public void setGridPointObstacle(Point p) {
        points[p.getX() - 1][p.getY() - 1].setObstacle(true);
    }

    
    /**
     * Gets a special zone with a custom cost and changes the grid accordingly
     * 
     * @param pinitial Initial zone point
     * @param pfinal Final zone point
     * @param cost Zone cost
     */
    public void setSpecialCostZone(Point pinitial, Point pfinal, int cost) {
        Point a = new Point(Math.min(pinitial.getX(), pfinal.getX()), Math.min(pinitial.getY(), pfinal.getY()));
        Point b = new Point(Math.max(pinitial.getX(), pfinal.getX()), a.getY());
        Point c = new Point(b.getX(), Math.max(pinitial.getY(), pfinal.getY()));
        Point d = new Point(a.getX(), c.getY());

        for (int i = a.getX(); i < b.getX(); i++) {
            points[i - 1][a.getY() - 1].setEdgeCostIfHigher(points[i][a.getY() - 1], cost);
            points[i - 1][c.getY() - 1].setEdgeCostIfHigher(points[i][c.getY() - 1], cost);

            points[i][a.getY() - 1].setEdgeCostIfHigher(points[i - 1][a.getY() - 1], cost);
            points[i][c.getY() - 1].setEdgeCostIfHigher(points[i - 1][c.getY() - 1], cost);
        }
        for (int j = b.getY(); j < c.getY(); j++) {
            points[b.getX() - 1][j - 1].setEdgeCostIfHigher(points[b.getX() - 1][j], cost);
            points[d.getX() - 1][j - 1].setEdgeCostIfHigher(points[d.getX() - 1][j], cost);

            points[b.getX() - 1][j].setEdgeCostIfHigher(points[b.getX() - 1][j - 1], cost);
            points[d.getX() - 1][j].setEdgeCostIfHigher(points[d.getX() - 1][j - 1], cost);
        }
    }
}
