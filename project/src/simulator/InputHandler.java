package simulator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import generictools.Point;
import maintools.Grid;

/**
 * Input Handler XML
 */
class InputHandler extends DefaultHandler {

    private Simulation sim;
    private Grid grid;

    private boolean bZone = false;
    private Point pZoneInitial;
    private Point pZoneFinal;
    private int colsnb, rowsnb;

    /**
     * Gets parsed Simulation instance
     * 
     * @return Simulation instance
     */
    public Simulation getSim() {
        return sim;
    }

    public void endDocument() {
        sim.setGrid(grid);
        sim.setInitialPopulation();
    }

    public void startElement(String uri, String name, String tag, Attributes atts) {
        if (tag.equals("simulation")) {
            int finalinst = 1, initpop = 1, maxpop = 1, comfortsens = 1;
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error! " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "finalinst":
                    finalinst = val;
                    break;
                case "initpop":
                    initpop = val;
                    break;
                case "maxpop":
                    maxpop = val;
                    break;
                case "comfortsens":
                    comfortsens = val;
                }
            }

            if (initpop > maxpop) {
                System.out.println("Error! maxpop parameter must be greater or equal to initpop.");
                System.exit(-1);
            }
            
            sim = new Simulation(finalinst, initpop, maxpop, comfortsens);
        } else if (tag.equals("grid")) {
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error: " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "colsnb":
                    colsnb = val;
                    break;
                case "rowsnb":
                    rowsnb = val;
                }
            }
            grid = new Grid(colsnb, rowsnb);

        } else if (tag.equals("initialpoint")) {
            int xinitial = 0;
            int yinitial = 0;
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error: " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "xinitial":
                    xinitial = val;
                    break;
                case "yinitial":
                    yinitial = val;
                }
            }

            // Check if points belong to the grid
            if (xinitial > rowsnb || yinitial > colsnb) {
                System.out.println("Error: Points must belong to the grid.");
                System.exit(-1);
            }

            Point posInitial = new Point(xinitial, yinitial);
            sim.setInitPoint(posInitial);

        } else if (tag.equals("finalpoint")) {
            int xfinal = 0;
            int yfinal = 0;
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error: " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "xfinal":
                    xfinal = val;
                    break;
                case "yfinal":
                    yfinal = val;
                }
            }

            // Check if points belong to the grid
            if (xfinal > colsnb || yfinal > rowsnb) {
                System.out.println("Error: Points must belong to the grid.");
                System.exit(-1);
            }

            sim.setFinalPoint(new Point(xfinal, yfinal));

        } else if (tag.equals("zone")) {
            int xinitial = 0, yinitial = 0, xfinal = 0, yfinal = 0;
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error: " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "xinitial":
                    xinitial = val;
                    break;
                case "yinitial":
                    yinitial = val;
                    break;
                case "xfinal":
                    xfinal = val;
                    break;
                case "yfinal":
                    yfinal = val;
                }
            }

            // Check if points belong to the grid
            if (xinitial > colsnb || yinitial > rowsnb || xfinal > colsnb || yfinal > rowsnb) {
                System.out.println("Error: Zone points must belong to the grid.");
                System.exit(-1);
            }

            pZoneInitial = new Point(xinitial, yinitial);
            pZoneFinal = new Point(xfinal, yfinal);
            bZone = true;

        } else if (tag.equals("obstacle")) {
            int xpos = 0;
            int ypos = 0;
            int val = 0;

            for (int i = 0; i < atts.getLength(); i++) {
                try {
                    val = Integer.parseInt(atts.getValue(i));
                } catch (NumberFormatException e) {
                    System.out.println("Error! " + e.getMessage());
                    System.exit(-1);
                }

                if (val <= 0) {
                    System.out.println("Error: " + atts.getLocalName(i) + " parameter must be greater than zero.");
                    System.exit(-1);
                }

                switch (atts.getLocalName(i)) {
                case "xpos":
                    xpos = val;
                    break;
                case "ypos":
                    ypos = val;

                }
            }

            // Check if points belong to the grid
            if (xpos > colsnb || ypos > rowsnb) {
                System.out.println("Error: Obstacle point must belong to the grid.");
                System.exit(-1);
            }

            grid.setGridPointObstacle(new Point(xpos, ypos));

        } else if (tag.equals("death")) {
            int deathParam;
            try {
                if ((deathParam = Integer.parseInt(atts.getValue(0))) <= 0) {
                    System.out.println("Error: Death parameter must be greater than zero.");
                    System.exit(-1);
                }
                sim.setDeathParam(deathParam);
            } catch (NumberFormatException e) {
                System.out.println("Error! " + e.getMessage());
                System.exit(-1);
            }

        } else if (tag.equals("reproduction")) {
            int reproduceParam;
            try {
                if ((reproduceParam = Integer.parseInt(atts.getValue(0))) <= 0) {
                    System.out.println("Error: Reproduction parameter must be greater than zero.");
                    System.exit(-1);
                }
                sim.setReproductionParam(reproduceParam);
            } catch (NumberFormatException e) {
                System.out.println("Error! " + e.getMessage());
                System.exit(-1);
            }

        } else if (tag.equals("move")) {
            int moveParam;
            try {
                if ((moveParam = Integer.parseInt(atts.getValue(0))) <= 0) {
                    System.out.println("Error: Move parameter must be greater than zero.");
                    System.exit(-1);
                }
                sim.setMoveParam(moveParam);
            } catch (NumberFormatException e) {
                System.out.println("Error! " + e.getMessage());
                System.exit(-1);
            }
        }
    }

    public void characters(char[] ch, int start, int length) {
        if (bZone) {
            int cost = 1;
            bZone = false;
            try {
                if ((cost = Integer.parseInt(new String(ch, start, length))) <= 0) {
                    System.out.println("Error: Zone cost must be greater than zero.");
                    System.exit(-1);
                }
                if (grid.getMaxCost() < cost) {
                    grid.setMaxCost(cost);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! " + e.getMessage());
                System.exit(-1);
            }
            grid.setSpecialCostZone(pZoneInitial, pZoneFinal, cost);
        }
    }

    public void warning(SAXParseException e) throws SAXParseException {
        System.out.println("Warning! " + e.getMessage());
    }

    public void error(SAXParseException e) throws SAXParseException {
        System.out.println("Error! " + e.getMessage());
        System.exit(-1);
    }

    public void fatalError(SAXParseException e) throws SAXParseException {
        System.out.println("Fatal error! " + e.getMessage());
        System.exit(-1);
    }
}