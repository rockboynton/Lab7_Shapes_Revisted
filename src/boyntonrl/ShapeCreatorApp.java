package boyntonrl;

import edu.msoe.se1010.winPlotter.WinPlotter;
import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class ShapeCreatorApp extends WinPlotter { // extends application

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }

    private Scanner fileIn;
//    public enum ShapeType {POINT, CIRCLE, TRIANGLE, RECTANGLE, LABLED_TRIANGLE, LABELED_RECTANGLE}

    /**
     *  The main program, a class method, that creates a ShapeLoaderApp instance and calls the
     *  appropriate methods to complete the requirements of the assignment.
     * @param args args passed to main method
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("InputDialog Example #2");
        String fileName = JOptionPane.showInputDialog(
                frame,
                "Enter an input text file to create a picture!",
                "Create A New Picture",
                JOptionPane.INFORMATION_MESSAGE);
        // prompt for file
        System.out.println(fileName);
        Path path = Paths.get(fileName);
        File file = new File(path.toString());
        //attach scanner to file
        ArrayList<Shape> shapes;
        //create app instance
        ShapeCreatorApp app;
        try (Scanner fileIn = new Scanner(file)){
            app = new ShapeCreatorApp(fileIn);
            shapes = app.readShapes(fileIn);
            app.drawShapes(shapes);
        } catch (Exception e) {
            // print something went wrong
            e.printStackTrace();
            System.out.println("baaaaaad");
        }
    }

    private static Shape parseShape(String shapeInfo) throws IllegalArgumentException {
        Shape shape;
        String[] infoPieces = shapeInfo.split("\\s+");
        String shapeType = infoPieces[0];
        double x = Double.parseDouble(infoPieces[1]);
        double y = Double.parseDouble(infoPieces[2]);
        Color color = hex2Rgb(infoPieces[3]);

        if (shapeType.equals("P:")) { // point
            shape = new Point(x, y, color);
        } else if (shapeType.equals("C:")) { // circle
            double radius = Double.parseDouble(infoPieces[4]);
            shape = new Circle(x, y, radius, color);
        } else if (shapeType.equals("T:")) { // triangle
            double base = Double.parseDouble(infoPieces[4]);
            double height = Double.parseDouble(infoPieces[5]);
            shape = new Triangle(x, y, base, height, color);
        } else if (shapeType.equals("R:")) { // rectangle
            double width = Double.parseDouble(infoPieces[4]);
            double height = Double.parseDouble(infoPieces[5]);
            shape = new Rectangle(x, y, width, height, color);
        } else if (shapeType.equals("LT:")) { // labeled triangle
            double base = Double.parseDouble(infoPieces[4]);
            double height = Double.parseDouble(infoPieces[5]);
            String[] nameWords = Arrays.copyOfRange(infoPieces, 6, infoPieces.length);
            String name = String.join("", nameWords);
            shape = new LabeledTriangle(x, y, base, height, color, name);
        }  else if (shapeType.equals("LR:")) { // labeled rectangle
            double width = Double.parseDouble(infoPieces[4]);
            double height = Double.parseDouble(infoPieces[5]);
            String[] nameWords = Arrays.copyOfRange(infoPieces, 6, infoPieces.length);
            String name = String.join("", nameWords);
            shape = new LabeledRectangle(x, y, width, height, color, name);
        } else {
            throw new IllegalArgumentException("Bad formatting shape");
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
     */
    public ShapeCreatorApp(Scanner fileIn) throws IllegalArgumentException {
        super();
        this.fileIn = fileIn;
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
        this.setWindowTitle(title);
        // get x and y values seperated by space
        String[] xAndY = dimensions.split(" ");
        this.setWindowSize(Integer.valueOf(xAndY[0]), Integer.valueOf(xAndY[1])); // get x and y
        this.setBackgroundColor(Integer.valueOf(backgroundColor.substring( 1, 3 ), 16 ), //red
                Integer.valueOf(backgroundColor.substring( 3, 5 ), 16 ), //green
                Integer.valueOf(backgroundColor.substring( 5, 7 ), 16 ) ); // blue

    }

    private ArrayList<Shape> readShapes(Scanner fileIn) {
        ArrayList<Shape> shapes = new ArrayList<>();
        String shapeSpec = "";
        do {
            try {
                shapeSpec = fileIn.nextLine();
                shapes.add(parseShape(shapeSpec));
            } catch (IllegalArgumentException e){
                System.out.println("Shape specified by line <" + shapeSpec + "> is not valid");
            }
        } while (fileIn.hasNextLine());

        return shapes;
    }

    private void drawShapes(ArrayList<Shape> shapes) {
        for (Shape shape: shapes) {
            shape.draw(this);
        }
    }

    /**
     *
     * @param colorStr e.g. "#FFFFFF"
     * @return
     */
    private static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}
