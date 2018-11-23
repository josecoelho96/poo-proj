package generictools;

/**
 * Point
 */
public class Point {

    private int x;
    private int y;

    /**
     * Initializes Point
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X coordinate
     * 
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets X coordinate
     * 
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets Y coordinate
     * 
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets Y coordinate
     * 
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
}
