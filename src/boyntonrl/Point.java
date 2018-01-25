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
 * This class represents a Point
 */
public class Point extends Shape {

    /**
     * Constructor -- creates the Point
     * @param x - cartesian x-origin of this Point
     * @param y - cartesian x-origin of this Point
     * @param color - the java.awt.Color for this Point
     */
    public Point(double x,
                 double y,
                 Color color) {
        super(x, y, color);
    }

    /**
     * Draws the point.
     * @param plotter reference to a WinPlotter object used for drawing
     */
    public void draw(WinPlotter plotter) {
        super.setPenColor(plotter);
        plotter.drawPoint(x, y);
    }
}
