package com.example;

import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PieChartSample extends Application {
    
    static String gameName = "";
    static double posPercent = 0;
    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle(gameName + " Review Percentage");
        stage.setWidth(500);
        stage.setHeight(500);
 
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Positive Reviews", posPercent),
                new PieChart.Data("Negative Reviews", 100-posPercent));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(gameName + " Review Percentage");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Data raw = new Data();
        raw.run();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the top game printed: ");
        gameName = scanner.nextLine();
        System.out.print("Enter the postive percent printed (Round to hundredths): ");
        posPercent = scanner.nextDouble();
        scanner.close();
        launch(args);
    }
}