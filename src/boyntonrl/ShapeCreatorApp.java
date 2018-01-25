package boyntonrl;

import edu.msoe.se1010.winPlotter.WinPlotter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    private static Scanner fileIn;

    /**
     *  The main program, a class method, that creates a ShapeLoaderApp instance and calls the
     *  appropriate methods to complete the requirements of the assignment.
     * @param args args passed to main method
     */
    public static void main(String[] args) {
        // prompt for file
        //attach scanner to file
        //create app instance
        ShapeCreatorApp app;
        try {
            app = new ShapeCreatorApp(fileIn);
        } catch (Exception e) {
            // print something went wrong
            e.printStackTrace();
        }
    }

    private static Shape parseShape(String line) throws IllegalArgumentException {
        Shape shape;


        return shape;
    }

    private static Color stringToColor(String hexColor) throws InputMismatchException {
        Color c;

        return c;
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
        String title = "";
        String dimensions = "";
        String backgroundColor = "";
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0 :
                    title = fileIn.nextLine();
                case 1 :
                    dimensions = fileIn.nextLine();
                case 2 :
                    backgroundColor = fileIn.nextLine();
            }
        }
        this.setWindowTitle(title);
        // get x and y values seperated by space
        String[] xAndY = dimensions.split("\\s+");
        this.setWindowSize(Integer.valueOf(xAndY[0]), Integer.valueOf(xAndY[1])); // get x and y 
        this.setBackgroundColor(Integer.valueOf(backgroundColor.substring( 1, 3 ), 16 ), //red
                Integer.valueOf(backgroundColor.substring( 3, 5 ), 16 ), //green
                Integer.valueOf(backgroundColor.substring( 5, 7 ), 16 ) ); // blue
    }

    private ArrayList<Shape> readShapes(Scanner fileIn) {
        ArrayList<Shape> shapes = new ArrayList<>();

        while (fileIn.hasNextLine()) {
            try {
                String shapeSpec = fileIn.nextLine();

            } catch (IllegalArgumentException e){

            }
        }

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
    private Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}
