/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 4 Inheritance with Shapes
 * Name: Rock Boynton
 * Created: 12/20/17
 */

package boyntonrl;

import edu.msoe.se1010.winPlotter.WinPlotter;

import java.awt.Color;

/**
 * This class represents a LabeledTriangle
 */
public class LabeledTriangle extends Triangle {

    private String name;

    /**
     * Constructor -- creates the LabeledTriangle
     * @param x - cartesian x-origin of this LabeledTriangle
     * @param y - cartesian x-origin of this LabeledTriangle
     * @param base - base (along x-axis) of this LabeledTriangle
     * @param height - height (along y-axis) of this LabeledTriangle
     * @param color - the java.awt.Color for this LabeledTriangle
     * @param name - descriptive name for this LabeledTriangle
     */
    public LabeledTriangle(double x,
                           double y,
                           double base,
                           double height,
                           Color color,
                           String name) {
        super(x, y, base, height, color);
        this.name = name;
    }

    /**
     * Draws the LabeledTriangle, with the name printed near the base. To draw the sides and base
     * of the LabeledTriangle, just call the Triangle's draw() method.
     * @param plotter reference to a WinPlotter object used for drawing
     */
    public void draw(WinPlotter plotter) {
        super.draw(plotter);
        plotter.printAt(base/2 + x, y, name);
    }
}
