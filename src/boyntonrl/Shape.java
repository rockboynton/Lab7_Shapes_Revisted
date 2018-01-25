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
 * This class represents a generic graphical shape.
 * @author taylor
 */
public abstract class Shape {

    private Color color;
    protected double x;
    protected double y;

    /**
     * Constructor initializes Shape attributes.
     * @param x cartesian x-origin of this Shape
     * @param y cartesian x-origin of this Shape
     * @param color the java.awt.Color for this Shape
     */
    public Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Sets the color of the shape
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Set the pen color on the WinPlotter object to match the current color of the shape.
     * @param plotter reference to a WinPlotter whose pen color should be set
     */
    public void setPenColor(WinPlotter plotter) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        plotter.setPenColor(red, green, blue);
    }

    /**
     * Draws the Shape. Abstract, so implementation deferred to sub classes
     * @param plotter reference to a WinPlotter object used for drawing
     */
    public abstract void draw(WinPlotter plotter);

}

