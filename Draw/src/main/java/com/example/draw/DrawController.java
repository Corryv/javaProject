/**
* Name:    Nick Foster, Corry Vantienen, Seth Edwards, Melissa Lopez, Julie Jimenez
* Date:    11/23/2025
* Class:   CSC-331
* Purpose: controller that controls the different aspects to the calculator
*/

package com.example.draw;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * DrawController class controls the functionality of the controllers.
 * Methods:
 *     void clearCanvasButtonPressed(ActionEvent event)
 *     void quadGraphButtonPressed(ActionEvent event)
 *     void graphButtonPressed(ActionEvent event)
 *     void initialize()
 *     public ArrayList<double[]> functionCoordinates(double m, double b)
 *     public ArrayList<double[]> quadraticCoordinates(double a, double b, double c)
 *     public void plot(ArrayList<double[]>graphPoints)
 */
public class DrawController {

    @FXML
    private TextField aTextField;

    @FXML
    private TextField bTextField;

    @FXML
    private TextField cTextField;

    @FXML
    private StackPane canvasStackPane;

    @FXML
    private ImageView cartoonPlaneImageView;

    @FXML
    private Button clearCanvasButton;

    @FXML
    private Separator clearCanvasSeparator;

    @FXML
    private Button graphButton;

    @FXML
    private Canvas graphCanvas;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Label instructionLabel;

    @FXML
    private TextField mTextField;

    @FXML
    private Separator planeSeparator;

    @FXML
    private TextField quadbTextField;

    @FXML
    private Label quadraticLabel;

    @FXML
    private VBox vboxContainer;

    @FXML
    private Slider scaleSlider;

    /**
     * Clears and redraws the canvas. This method resets the background to black,
     * recreates the graphics context, and redraws the X and Y axes with the
     * origin positioned at the center of the canvas.
     * The JavaFX canvas coordinate system begins at the top-left corner (0,0),
     * with increasing x-values to the right and increasing y-values downward.
     *
     * @param event the ActionEvent triggered by pressing the "Clear Canvas" button
     * Methods:
     *     graphCanvas.getGraphicsContext2D()
     *     gc.fillRect(0, 0, double width, double height)
     *     gc.strokeLine(startX, startY, endX, endY)
     *     gc.setStroke(javafx.scene.paint.Color.WHITE)
     */
    @FXML
    void clearCanvasButtonPressed(ActionEvent event){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();

        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();
        double centerX = width / 2;
        double centerY = height / 2;

        // Draw background
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // Draw axes
        gc.setStroke(javafx.scene.paint.Color.WHITE);
        gc.strokeLine(0, centerY, width, centerY); // X-axis
        gc.strokeLine(centerX, 0, centerX, height); // Y-axis
    }

    /**
     * Handles the graphing of a quadratic function. Inputs are taken from the aTextField(x^2-coefficient),
     * quadbTextField(x-coefficient), and cTextField(y-intercept)
     * @param event the ActionEvent triggered by pressing the "Graph Quadratic" button
     * Methods:
     *     quadraticCoordinates(double a, double b, double c)
     *     plot(ArrayList<double[]> points)
     */
    @FXML
    void quadGraphButtonPressed(ActionEvent event) {
        double a = Double.parseDouble(aTextField.getText());
        double b = Double.parseDouble(quadbTextField.getText());
        double c = Double.parseDouble(cTextField.getText());
        ArrayList<double[]> points = quadraticCoordinates(a, b,c);
        plot(points);
    }

    /**
     * Handles the action of graphing a linear function. Inputs are taken from the mTextField(x-coefficient) and
     * bTextField(y-intercept).
     * @param event the ActionEvent triggered by pressing the "Graph Line" button
     * Methods:
     *     functionCoordinates(double m, double b)
     *     plot(ArrayList<double[]> points)
     */
    @FXML
    private void graphButtonPressed(ActionEvent event){
        double m = Double.parseDouble(mTextField.getText());
        double b = Double.parseDouble(bTextField.getText());
        ArrayList<double[]> points = functionCoordinates(m,b);
        plot(points);
    }


    /**
     * Initializes the graph canvas after the FXML fields are injected.
     * This method:
     *  Binds the canvas width and height to the size of the containing HBox
     *  Adds listeners to redraw the graph whenever the canvas is resized
     *  Performs the initial draw of the graph.
     * It is automatically invoked by the JavaFX framework after the controller is constructed.
     */

    // initialize canvas
    @FXML
    public void initialize() {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        graphCanvas.widthProperty().bind(hboxContainer.widthProperty().subtract(vboxContainer.widthProperty()));
        graphCanvas.heightProperty().bind(hboxContainer.heightProperty());
        graphCanvas.widthProperty().addListener((obs, oldVal, newVal) -> drawGraph());
        graphCanvas.heightProperty().addListener((obs, oldVal, newVal) -> drawGraph());
        scaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> drawGraph());
        drawGraph();
    }

    /**
     * Draws the graph on the canvas based on its current size.
     * This method:
     *  Retrieves the current width and height of the canvas,</li>
     *  Clears and fills the background with black,</li>
     *  Draws white X and Y axes centered on the canvas.</li>
     * It is called during initialization and whenever the canvas is resized.
     */

    private void drawGraph() {
        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        double centerY = height / 2;
        double centerX = width / 2;

        // Draw background
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // Draw axes
        gc.setStroke(javafx.scene.paint.Color.WHITE);
        gc.strokeLine(0, centerY, width, centerY); // X-axis
        gc.strokeLine(centerX, 0, centerX, height); // Y-axis
    }

    /**
     * Generates a list of (x, y) coordinate pairs for a linear function.
     *
     * @param m the slope of the linear function
     * @param b the y-intercept of the linear function
     * @return an arrayList containing [x,y] coordinate pairs.
     */
    // calculate x.y coordinates
    public ArrayList<double[]> functionCoordinates(double m, double b){
        // ArrayList of x,y coordinates( arraylist of arrays)
        ArrayList<double[]> graphPoints = new ArrayList<>();
        for (int x = -100; x < 101; x++){
            double y = m * x + b;
            graphPoints.add(new double[]{x,y});
        }
    return graphPoints;
    }

    /**
     * Generates a list of (x, y) coordinate pairs for a quadratic function.
     * @param a the coefficient of the x^2 term
     * @param b the coefficient of the x term
     * @param c the constant term of the quadratic function
     * @return an arrayList containing [x,y] coordinates pairs
     */
    public ArrayList<double[]> quadraticCoordinates(double a, double b, double c){
        ArrayList<double[]> graphPoints = new ArrayList<>();
        for (double x = -100; x < 101; x+=0.1) {
            double y = a * (Math.pow(x, 2)) + b * x + c;
            graphPoints.add(new double[]{x, y});
        }
        return graphPoints;

        }

    /**
     * Plots a list of (x, y) coordinate pairs onto the canvas by drawing
     * connected line segments between each consecutive point.
     * @param graphPoints an ArrayList of double[] pairs representing x and y
     *                    coordinates to be drawn on the canvas
     * Methods:
     *      graphCanvas.getGraphicsContext2D()
     *      gc.fillRect(0, 0, double width, double height)
     *      gc.strokeLine(double startX, double startY, double endX, double endY)
     *      gc.setStroke(javafx.scene.paint.Color.WHITE)
     *      gc.setLineWidth(double width)
     */
    // plot the function
    public void plot(ArrayList<double[]>graphPoints){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        // center of canvas
        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();
        double centerX = width / 2;
        double centerY = height / 2;
        // pixels per graphing unit, adjusts the zoom of graphCanvas
        double scale = scaleSlider.getValue();
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        gc.setLineWidth(2);
        // starting point
        double[] startingPoint = {
                centerX + graphPoints.get(0)[0] * scale,
                centerY - graphPoints.get(0)[1] * scale
        };

        for (double [] point : graphPoints){

            double x = centerX + point[0] * scale;
            // subtract here because y values on screen increase as you move down
            // top-left corner is at (0,0)
            double y = centerY - point[1] * scale;
            gc.strokeLine(startingPoint[0],startingPoint[1],x,y);
            startingPoint[0] = x;
            startingPoint[1] = y;

        }

    }
}
