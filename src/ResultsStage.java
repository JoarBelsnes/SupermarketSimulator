import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

/**
 * Results Stage
 * Creates a Window with the results from the simulation
 * Saves to File when you click save
 * Closes Window when you click close
 * Contributors: Joshua
 */


public class ResultsStage extends Stage{
    ResultsStage(Supermarket smart){
        /*
         * Results Window
         */

        // File Selector
        FileChooser fileSelector = new FileChooser();
        fileSelector.setInitialDirectory(new File(System.getProperty("user.home")));
        fileSelector.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Document", "*.txt")
        );

        // Labels
        Label lblBlankSpacer1 = new Label ("");
        Label lblCustomers = new Label("Total Number of Customers: ");
        Label lblSimulationTime = new Label("Total Simulation Time: ");
        Label lblCheckouts = new Label("Number of Checkout Lanes: ");
        Label lblSpacer = new Label("-------------------");
        Label lblLongestQueueTime = new Label("Longest Queue Time: ");
        Label lblCustomersRageQuit = new Label("Customers that Abandoned the store: ");
        // Data
        Label dataCustomers = new Label("" + smart.getCustomers().size());
        Label dataSimulationTime = new Label("" + smart.getCurrentTime());
        Label dataCheckouts = new Label("" + smart.getCashiers().size());
        Label dataSpacer = new Label("-------------------");
        Label dataLongestQueueTime = new Label("" + smart.getLongestQueueTime());
        Label dataCustomersRageQuit = new Label("" + smart.getRageQuit());
        // Buttons
        Button btnSave = new Button("Save");
        Button btnClose = new Button("Close");

        GridPane resultsGrid = new GridPane();
        resultsGrid.setVgap(25);
        resultsGrid.setHgap(25);
        resultsGrid.setPadding(new Insets(2, 2, 2, 2));
        resultsGrid.add(lblCustomers, 0, 1);
        resultsGrid.add(lblSimulationTime, 0, 2);
        resultsGrid.add(lblCheckouts, 0, 3);
        resultsGrid.add(lblSpacer, 0, 4);
        resultsGrid.add(lblLongestQueueTime, 0, 5);
        resultsGrid.add(lblCustomersRageQuit, 0, 6);
        resultsGrid.add(dataCustomers, 1, 1);
        resultsGrid.add(dataSimulationTime, 1, 2);
        resultsGrid.add(dataCheckouts, 1, 3);
        resultsGrid.add(dataSpacer, 1, 4);
		resultsGrid.add(dataLongestQueueTime, 1, 5);
        resultsGrid.add(dataCustomersRageQuit, 1, 6);
        resultsGrid.add(btnSave, 0, 7);
        resultsGrid.add(btnClose, 1, 7);
        Group resultsRoot = new Group();
        resultsRoot.getChildren().addAll(resultsGrid);
        this.setScene(new Scene(resultsRoot, 400, 400));
        this.show();
        
        // Settings
        this.setTitle("Results");

        // Handlers
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
            }
        });
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileSelector.setTitle("Save Settings");
                Stage stage = (Stage) btnClose.getScene().getWindow();
                File file = fileSelector.showSaveDialog(stage);
                if (file != null) {
                    try {
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write("Supermarket Simulation Results");
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write("------------------------------");
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(lblCustomers.getText() + " " + dataCustomers.getText());
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(lblSimulationTime.getText() + " " + dataSimulationTime.getText());
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(lblCheckouts.getText() + " " + dataCheckouts.getText());
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(lblLongestQueueTime.getText() + " " + dataLongestQueueTime.getText());
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(lblCustomersRageQuit.getText() + " " + dataCustomersRageQuit.getText());
                        fileWriter.write(System.lineSeparator());
                        fileWriter.write(System.lineSeparator());
                        for (Customer i : smart.getCustomers()){
                        	fileWriter.write("Customer# :," + i.getId() + ",  Number of Items: ," + i.getItems() + ", Entered at: ," + i.getArrivedTime() + ", Queued at: ," + i.getQueuedTime() + ", Departed at: ," + i.getDepartedTime() + ", Abandoned Store :," + i.hasRageQuit() +",");
                        	fileWriter.write(System.lineSeparator());
                        }
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }
        });
    }
}