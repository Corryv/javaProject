package com.example.draw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.action.Action;

import java.util.ArrayList;

public class DrawController {


    @FXML
    private TextField bTextField;

    @FXML
    private StackPane canvasStackPane;

    @FXML
    private Canvas functionCanvas;

    @FXML
    private Label functionLabel;

    @FXML
    private HBox functionVbox;

    @FXML
    private Button graphButton;

    @FXML
    private Label instructionLabel;

    @FXML
    private TextField mTextField;

    @FXML
    private SplitPane rootSplitPane;

    @FXML
    private VBox splitPaneVbox;

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
        // init graphics context object
        var gc = functionCanvas.getGraphicsContext2D();
        double width = functionCanvas.getWidth();
        double height = functionCanvas.getHeight();
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

    // calculate x.y coordinates (x values from -10 to 10, so 10 points total)
    public ArrayList<double[]> functionCoordinates(double m, double b){
        // ArrayList of x,y coordinates( arraylist of arrays with type double)
        ArrayList<double[]> graphPoints = new ArrayList<>();
        for (int x = -10; x < 11; x++){
            double y = m * x + b;
            // add current point array of [x,y] to ArrayList of coordinates
            graphPoints.add(new double[]{x,y});
        }
    return graphPoints;
    }

    // plot the function
    public void plot(ArrayList<double[]>graphPoints){
        var gc = functionCanvas.getGraphicsContext2D();
        // get center of canvas
        double width = functionCanvas.getWidth();
        double height = functionCanvas.getHeight();
        double centerX = width / 2;
        double centerY = height / 2;
        // pixels per graphing unit
        double scale = 20;
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        gc.setLineWidth(2);
        // get starting point
        double[] startingPoint = {
                centerX + graphPoints.get(0)[0] * scale,
                centerY - graphPoints.get(0)[1] * scale
        };

        for (double [] point : graphPoints){

            double x = centerX + point[0] * scale;
            // subtract here because y values on screen increase as you move down (Chatgpt solution)
            double y = centerY - point[1] * scale;
            gc.strokeLine(startingPoint[0],startingPoint[1],x,y);
            startingPoint[0] = x;
            startingPoint[1] = y;

        }

    }
}