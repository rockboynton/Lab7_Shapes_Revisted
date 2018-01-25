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
 * This class represents a LabeledRectangle
 */
public class LabeledRectangle extends Rectangle {

    private String name;

    /**
     * Constructor -- creates the LabeledRectangle
     * @param x - cartesian x-origin of this LabeledRectangle
     * @param y - cartesian x-origin of this LabeledRectangle
     * @param width - base (along x-axis) of this LabeledRectangle
     * @param height - height (along y-axis) of this LabeledRectangle
     * @param color - the java.awt.Color for this LabeledRectangle
     * @param name - descriptive name for this LabeledRectangle
     */
    public LabeledRectangle(double x,
                            double y,
                            double width,
                            double height,
                            Color color,
                            String name) {
        super(x, y, width, height, color);
        this.name = name;
    }

    /**
     * Draws the LabeledRectangle with the name in the center To draw the sides of the
     * LabeledRectangle, just call the Rectangle's draw() method.
     * @param plotter reference to a WinPlotter object used for drawing
     */
    public void draw(WinPlotter plotter) {
        super.draw(plotter);
        plotter.printAt(width/2 + x, height/2 + y, name);
    }
}
