//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import java.io.*;
//import java.util.ArrayList;
//
//public class ResultsStage extends Stage{
//    ResultsStage(){
//        /*
//         * Results Window
//         */
//
//        // Labels
//        Label lblBlankSpacer = new Label ("");
//        Label lblCustomers = new Label("Customers: ");
//        Label lblSimulationTime = new Label("Simulation Time: ");
//        Label lblCheckouts = new Label("Checkout Lanes: ");
//        Label lblSpacer = new Label("-------------------");
//        // Data
//        Label dataCustomers = new Label("0");
//        Label dataSimulationTime = new Label("0");
//        Label dataCheckouts = new Label("0");
//        Label dataSpacer = new Label("-------------------");
//        // Buttons
//        Button test3 = new Button("What do you think?");
//
//        GridPane resultsGrid = new GridPane();
//        resultsGrid.setVgap(25);
//        resultsGrid.setHgap(25);
//        resultsGrid.setPadding(new Insets(2, 2, 2, 2));
//        resultsGrid.add(lblCustomers, 0, 1);
//        resultsGrid.add(lblSimulationTime, 0, 2);
//        resultsGrid.add(lblCheckouts, 0, 3);
//        resultsGrid.add(lblSpacer, 0, 4);
//        resultsGrid.add(dataCustomers, 1, 1);
//        resultsGrid.add(dataSimulationTime, 1, 2);
//        resultsGrid.add(dataCheckouts, 1, 3);
//        resultsGrid.add(dataSpacer, 1, 4);
//        resultsGrid.add(test3, 2, 4);
//        Group resultsRoot = new Group();
//        resultsRoot.getChildren().addAll(resultsGrid);
//        this.setScene(new Scene(resultsRoot, 400, 400));
//        this.show();
//
//
//    }
//}