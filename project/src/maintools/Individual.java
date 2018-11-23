package maintools;

import java.util.ArrayList;

import generictools.Point;

/**
 * Individual
 */
public class Individual {

    private double comfort = 0;
    private double deathTimestamp;
    
    private ArrayList<GridPoint> path;

    /**
     * Initializes Individual
     * 
     * @param p Initial GridPoint
     */
    public Individual(GridPoint p) {
        path = new ArrayList<GridPoint>();
        path.add(p);
    }

    /**
     * Constructor used when cloning
     * 
     * @param individual
     */
    public Individual(Individual individual) {
        this.comfort = individual.comfort;
        this.deathTimestamp = individual.deathTimestamp;
        this.path = new ArrayList<GridPoint>(individual.path);
    }

    /**
     * Gets current path position
     * 
     * @return Current path position
     */
    public GridPoint getCurrentPathPosition() {
        return path.get(path.size() - 1);
    }

    /**
     * Gets path
     * 
     * @return Path
     */
    public ArrayList<GridPoint> getPath() {
        return path;
    }

    /**
     * Sets path
     * 
     * @param path
     */
    public void setPath(ArrayList<GridPoint> path) {
        this.path = path;
    }

    /**
     * Adds GridPoint to path
     * 
     * @param p GridPoint
     */
    public void addToPath(GridPoint p) {
        // Check if GridPoint is already in Path
        // If it is, remove the path cycle, while leaving the new GridPoint
        for (int i = 0; i < path.size(); i++) {
            if (p.equals(path.get(i))) {
                for (int j = path.size() - 1; j > i; j--) {
                    path.remove(j);
                }
                return;
            }
        }
        // Add new GridPoint
        path.add(p);
    }

    /**
     * Gets death timestamp
     * 
     * @return Death timestamp
     */
    public double getDeathTimestamp() {
        return deathTimestamp;
    }

    /**
     * Sets death timestamp
     * 
     * @param deathTimestamp
     */
    public void setDeathTimestamp(double deathTimestamp) {
        this.deathTimestamp = deathTimestamp;
    }

    /**
     * Gets comfort
     * 
     * @return Comfort
     */
    public double getComfort() {
        return comfort;
    }

    /**
     * Sets comfort
     * 
     * @param comfort
     */
    public void setComfort(double comfort) {
        this.comfort = comfort;
    }

    /**
     * Gets valid GridPoints the individual can move to
     * 
     * @return Valid GridPoints the individual can move to
     */
    public ArrayList<GridPoint> validPoints() {
        ArrayList<GridPoint> validPoints = new ArrayList<>();
        
        for (Edge e : path.get(path.size() - 1).getEdges()) {
            if (!(e.getDest().isObstacle())) {
                validPoints.add(e.getDest());
            }
        }
        return validPoints;
    }

    /**
     * Clone Individual
     */
	public Individual clone() {
        return new Individual(this);
    }
	
	/**
	 * Gets path cost
	 * 
	 * @return Path cost
	 */
	public int getPathCost() {
	    int cost = 0;
	    
        for (int i = 0; i < (getPath().size() - 1); i++) {
            cost += getPath().get(i).getEdge(getPath().get(i + 1)).getCost();
	    }
        
        return cost;
	}

	/**
	 * Checks whether the current path position matches another point
	 * 
	 * @param finalPoint
	 * @return Whether the current path position matches another point
	 */
    public boolean hasLastPoint(Point finalPoint) {
        GridPoint p = getCurrentPathPosition();
        return p.getX() == finalPoint.getX() && p.getY() == finalPoint.getY();
    }
    
    @Override
    public String toString() {
        String str = path.toString().replace("[", "(").replace("]", ")");
        return str.substring(1,  str.length()-1);
    }
}
