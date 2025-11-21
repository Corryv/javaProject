package com.example.draw;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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
    void clearCanvasButtonPressed(ActionEvent event){
        var gc = graphCanvas.getGraphicsContext2D();

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

    @FXML
    void quadGraphButtonPressed(ActionEvent event) {
        double a = Double.parseDouble(aTextField.getText());
        double b = Double.parseDouble(quadbTextField.getText());
        double c = Double.parseDouble(cTextField.getText());
        ArrayList<double[]> points = quadraticCoordinates(a, b,c);
        plot(points);

    }

    @FXML
    private void graphButtonPressed(ActionEvent event){
        double m = Double.parseDouble(mTextField.getText());
        double b = Double.parseDouble(bTextField.getText());
        ArrayList<double[]> points = functionCoordinates(m,b);
        plot(points);
    }
    // initialize canvas
    @FXML
    public void initialize() {
        graphCanvas.widthProperty().bind(hboxContainer.widthProperty());
        graphCanvas.heightProperty().bind(hboxContainer.heightProperty());
        ChangeListener<Number> sizeListener = (ObservableValue<? extends Number> obs, Number oldVal, Number newVal)
                -> drawGraph();
        graphCanvas.widthProperty().addListener((obs, oldVal, newVal) -> drawGraph());
        graphCanvas.heightProperty().addListener((obs, oldVal, newVal) -> drawGraph());
        drawGraph();
    }

    private void drawGraph() {
        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();

        var gc = graphCanvas.getGraphicsContext2D();

        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, width, height);

        double centerX = width / 2.0;
        double centerY = height / 2.0;

        gc.setStroke(javafx.scene.paint.Color.WHITE);
        gc.strokeLine(0, centerY, width, centerY);   // X-axis
        gc.strokeLine(centerX, 0, centerX, height);  // Y-axis
    }

    // calculate x.y coordinates (x values from -10 to 10, so 10 points total)
    public ArrayList<double[]> functionCoordinates(double m, double b){
        // ArrayList of x,y coordinates( arraylist of arrays)
        ArrayList<double[]> graphPoints = new ArrayList<>();
        for (int x = -100; x < 101; x++){
            double y = m * x + b;
            graphPoints.add(new double[]{x,y});
        }
    return graphPoints;
    }
    public ArrayList<double[]> quadraticCoordinates(double a, double b, double c){
        ArrayList<double[]> graphPoints = new ArrayList<>();
        for (double x = -100; x < 101; x+=0.1) {
            double y = a * (Math.pow(x, 2)) + b * x + c;
            graphPoints.add(new double[]{x, y});
        }
        return graphPoints;

        }

    // plot the function
    public void plot(ArrayList<double[]>graphPoints){
        var gc = graphCanvas.getGraphicsContext2D();
        // center of canvas
        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        // pixels per graphing unit, adjusts the zoom of graphCanvas
        double scale = 50;
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        gc.setLineWidth(2);
        // starting point
        double[] startingPoint = {
                centerX + graphPoints.getFirst()[0] * scale,
                centerY - graphPoints.getFirst()[0] * scale
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