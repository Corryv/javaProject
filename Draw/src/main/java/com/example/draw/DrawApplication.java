/**
* Name:    Nick Foster, Corry Vantienen, Seth Edwards, Melissa Lopez, Julie Jimenez
* Date:    11/23/2025
* Class:   CSC-331
* Purpose: loads a new scene to draw the graph
*/

package com.example.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DrawApplication.class.getResource("Draw.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Grrraf");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
