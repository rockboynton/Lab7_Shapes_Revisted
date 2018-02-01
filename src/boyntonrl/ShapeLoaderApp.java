/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 7 Shapes Revisited
 * Name: Rock Boynton
 * Created: 1/25/18
 */

package boyntonrl;

import edu.msoe.se1010.winPlotter.WinPlotter;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.*; // importing swing classes using *

/**
 * This a javax.swing application that draws an image composed of shapes by reading a text file
 * with this specific format:
 * Point - P: X Y COLOR
 * Circle - C: X Y COLOR RADIUS
 * Triangle - T: X Y COLOR BASE HEIGHT
 * Rectangle - R: X Y COLOR WIDTH HEIGHT
 * Labeled Triangle - LT: X Y COLOR BASE HEIGHT LABEL
 * Labeled Rectangle - LR: X Y COLOR WIDTH HEIGHT LABEL
 */
public class ShapeLoaderApp extends WinPlotter {

    /**
     * Radix for hexadecimal numbers;
     */
    public static final int HEX = 16;

    /**
     *  The main program, a class method, that creates a ShapeLoaderApp instance and calls the
     *  appropriate methods to complete the requirements of the assignment.
     * @param args args passed to main method
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Enter file");
        // prompt for file
        String fileName = JOptionPane.showInputDialog(
                frame,
                "Enter an input text file (without the .txt) to create a picture!",
                "Create A New Picture",
                JOptionPane.INFORMATION_MESSAGE);
        Path path = Paths.get(fileName);
        File file = new File(path.toString());
        ArrayList<Shape> shapes;
        //create app instance
        ShapeLoaderApp app;
        //attach scanner to file
        try (Scanner fileIn = new Scanner(file)){
            app = new ShapeLoaderApp(fileIn);
            shapes = app.readShapes(fileIn);
            app.drawShapes(shapes);
        } catch (IllegalArgumentException e) {
            JFrame dialogFrame = new JFrame();
            JOptionPane.showMessageDialog(dialogFrame,
                    "Error reading picture header information (first three lines " +
                            "of the file). ",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            JFrame dialogFrame = new JFrame();
            JOptionPane.showMessageDialog(dialogFrame,
                    "File not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Shape parseShape(String shapeInfo) throws IllegalArgumentException {
        Shape shape;
        String[] infoPieces = shapeInfo.split("\\s+");
        if (infoPieces.length < 4) {
            // error reading line of file after header information
            throw new IllegalArgumentException("Incorrect formatting of line");
        }
        String shapeType = infoPieces[0];
        double x = Double.parseDouble(infoPieces[1]);
        double y = Double.parseDouble(infoPieces[2]);
        Color color = stringToColor(infoPieces[3]);

        switch (shapeType) {
            case "P:":  // point
                shape = new Point(x, y, color);
                break;
            case "C:":  // circle
                double radius = Double.parseDouble(infoPieces[4]);
                shape = new Circle(x, y, radius, color);
                break;
            case "T:": { // triangle
                double base = Double.parseDouble(infoPieces[4]);
                double height = Double.parseDouble(infoPieces[5]);
                shape = new Triangle(x, y, base, height, color);
                break;
            }
            case "R:": { // rectangle
                double width = Double.parseDouble(infoPieces[4]);
                double height = Double.parseDouble(infoPieces[5]);
                shape = new Rectangle(x, y, width, height, color);
                break;
            }
            case "LT:": { // labeled triangle
                double base = Double.parseDouble(infoPieces[4]);
                double height = Double.parseDouble(infoPieces[5]);
                String[] nameWords = Arrays.copyOfRange(infoPieces, 6, infoPieces.length);
                String name = String.join("", nameWords);
                shape = new LabeledTriangle(x, y, base, height, color, name);
                break;
            }
            case "LR:": { // labeled rectangle
                double width = Double.parseDouble(infoPieces[4]);
                double height = Double.parseDouble(infoPieces[5]);
                String[] nameWords = Arrays.copyOfRange(infoPieces, 6, infoPieces.length);
                String name = String.join("", nameWords);
                shape = new LabeledRectangle(x, y, width, height, color, name);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid shape identifier");
        }
        return shape;
    }

    /**
     * This constructor accepts a Scanner instance as input and assigns it to an object
     * attribute. The Scanner instance is associated with an input file. The method
     * reads the header information from the file (the first three lines) and initializes the
     * object with the characteristics specified in the input file. This method should propagate
     * any exceptions that are encountered back to main() to be handled there.
     * @param fileIn a scanner associated with an input file.
     * @throws IllegalArgumentException when there is a problem with the header of the file
     */
    public ShapeLoaderApp(Scanner fileIn) throws IllegalArgumentException {
        super();
        String title = "";
        String dimensions = "";
        String backgroundColor = "";
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0 :
                    title = fileIn.nextLine();
                    break;
                case 1 :
                    dimensions = fileIn.nextLine();
                    break;
                case 2 :
                    backgroundColor = fileIn.nextLine();
                    break;
            }
        }
        // get x and y values seperated by space
        String[] xAndY = dimensions.split(" ");
        if (xAndY.length < 2 || xAndY.length > 2) {
            throw new IllegalArgumentException();
        }
        if (backgroundColor.length() != 7 || backgroundColor.charAt(0) != '#' ||
                !backgroundColor.substring(1).matches("[0-9A-F]+")) {
            throw new IllegalArgumentException("invalid hex color");
        }
        this.setWindowTitle(title);
        this.setWindowSize(Integer.valueOf(xAndY[0]), Integer.valueOf(xAndY[1])); // get x and y
        this.setPlotBoundaries(0.0D, 0.0D, Double.valueOf(xAndY[0]),
                Double.valueOf(xAndY[1]));
        this.setBackgroundColor(Integer.valueOf(backgroundColor.substring(1, 3), HEX), //red
                Integer.valueOf(backgroundColor.substring(3, 5), HEX), //green
                Integer.valueOf(backgroundColor.substring(5, 7), HEX)); // blue

    }

    private ArrayList<Shape> readShapes(Scanner fileIn) {
        ArrayList<Shape> shapes = new ArrayList<>();
        String shapeSpec = "";
        do {
            try {
                shapeSpec = fileIn.nextLine();
                shapes.add(parseShape(shapeSpec));
            } catch (IllegalArgumentException e){
                System.out.print("Shape specified by line <" + shapeSpec + "> is not valid: ");
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame,
                        "Shape specified by line <" + shapeSpec + "> is not valid: " +
                                e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (fileIn.hasNextLine());

        return shapes;
    }

    private void drawShapes(ArrayList<Shape> shapes) {
        for (Shape shape: shapes) {
            shape.draw(this);
        }
    }

    private static Color stringToColor(String colorStr) throws InputMismatchException{
        // checks valid length, # indicating hex, and valid hex color string
        if (colorStr.length() != 7 || colorStr.charAt(0) != '#' || !colorStr.substring(1).matches("[0-9A-F]+")) {
            throw new InputMismatchException("invalid hex color");
        }
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), HEX),
                Integer.valueOf(colorStr.substring(3, 5), HEX),
                Integer.valueOf(colorStr.substring(5, 7), HEX));
    }
}
