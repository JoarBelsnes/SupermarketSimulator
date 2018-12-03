import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Desktop;
import java.io.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button; 
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.util.ArrayList;

public class Gui extends Application {


    public void start(Stage primaryStage) {


        // Variables
        int customersInStore = 0;

        // File Selector
        FileChooser fileSelector = new FileChooser();
        fileSelector.setInitialDirectory(new File(System.getProperty("user.home")));
        fileSelector.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Supermaket Settings", "*.mkt"),
                new FileChooser.ExtensionFilter("Text Document", "*.txt")
        );

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        // Menu - File
        Menu menuFile = new Menu("File");
        MenuItem menuFileNew = new MenuItem("New");
        MenuItem menuFileLoad = new MenuItem("Load");
        MenuItem menuFileSaveAs = new MenuItem("Save As");
        MenuItem menuFileExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuFileNew, menuFileLoad, menuFileSaveAs, menuFileExit);

        menuBar.getMenus().addAll(menuFile);

        // Creating Labels
        Text lblMaxCustomers = new Text("Max. Customers: ");
        Text lblNumberOfCheckoutLines = new Text("# of Checkout Lines: ");
        // Text lblNumberOfLimitedCheckoutLines = new Text("# of 'x Items or Less' Checkout Lines: ");
        Text lblMaxSimulationTime = new Text("Customer Arrival Window: ");


        // Creating Text Fields
        TextField txtMaxCustomers = new TextField();
        TextField txtNumberOfCheckoutLines = new TextField();
        // TextField txtNumberOfLimitedCheckoutLines = new TextField();
        TextField txtMaxSimulationTime = new TextField();

        //run button

        Button runButton = new Button("Run Simulation");


        // Grid Pane
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(25);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(lblMaxCustomers, 0, 0);
        grid.add(txtMaxCustomers, 1, 0);
        grid.add(lblNumberOfCheckoutLines, 0, 1);
        grid.add(txtNumberOfCheckoutLines, 1, 1);
        // grid.add(lblNumberOfLimitedCheckoutLines, 0, 2);
        // grid.add(txtNumberOfLimitedCheckoutLines, 1, 2);
        grid.add(lblMaxSimulationTime, 0, 3);
        grid.add(txtMaxSimulationTime, 1, 3);
        grid.add(runButton, 1, 4);
        // Scene Settings
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, grid);
        Scene primaryScene = new Scene(root, 600, 300);


        //simulation scene
        //array of cashiers


        GridPane simulationPane = new GridPane();
        simulationPane.setVgap(5);
        simulationPane.setHgap(25);
        simulationPane.setPadding(new Insets(5, 5, 5, 5));
        Button runButton2 = new Button("Run Simulation");
        Button back = new Button("Back");
        simulationPane.add(back, 5, 1);
        simulationPane.add(runButton2, 1, 1);


        Group root2 = new Group();
        root2.getChildren().addAll(simulationPane);


        Scene simulationScene = new Scene(root2, 800, 400);
        /**
         //for loop to generate cashiers
         for (int i = 0; i < numberOfLines; i++ ){
         Text t = new Text("Line " + (i+1));
         cashiers.add(t);
         simulationPane.add(cashiers.get(i), (i+1)*2, 3);
         }
         for (int x = 0; x < numberOfCustomers.size(); x++){

         for (int y = 0; y < numberOfCustomers.get(x)-1; y++){
         Text c = new Text("Customer "+ (y+1));
         Customers.add(c);

         simulationPane.add(c,(x+1)*2,y+4);
         }
         }
         **/


        // Stage Settings
        primaryStage.setTitle("Supermarket Simulator");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        // Handlers
        menuFileExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menuFileNew.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                txtMaxCustomers.setText("10");
                txtMaxSimulationTime.setText("50");
                txtNumberOfCheckoutLines.setText("2");
            }
        });
        menuFileLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileSelector.setTitle("Load Settings");
                File file = fileSelector.showOpenDialog(primaryStage);
                if (file != null) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(file));
                        String line = br.readLine();
                        String[] supermarketSettings = line.split(",");
                        txtMaxSimulationTime.setText(supermarketSettings[0]);
                        txtMaxCustomers.setText(supermarketSettings[1]);
                        txtNumberOfCheckoutLines.setText(supermarketSettings[2]);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        Alert alert = new Alert(AlertType.ERROR, "The file you selected is not in the expected format.", ButtonType.OK);
                        alert.showAndWait();
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }
            }
        });
        menuFileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileSelector.setTitle("Save Settings");
                File file = fileSelector.showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(txtMaxSimulationTime.getText() + "," + txtMaxCustomers.getText() + "," + txtNumberOfCheckoutLines.getText());
                        fileWriter.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                runButton2.setDisable(false);
                primaryStage.setScene(simulationScene);

            }
        });
        runButton2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                runButton2.setDisable(true);
                new Thread() {
                    public void run() {
                        try {
                            Supermarket smart = new Supermarket(Integer.parseInt(txtMaxSimulationTime.getText()), Integer.parseInt(txtMaxCustomers.getText()), Integer.parseInt(txtNumberOfCheckoutLines.getText()));
                            //runSimulation(smart, simulationScene, simulationPane);

                            ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
                            ArrayList<Text> cashiersLabel = new ArrayList<Text>();
                            ArrayList<Text> Customers = new ArrayList<Text>();


                            /**
                             * INITIALIZE GUI HERE
                             */
                            boolean end = false;
                            while (!end) {
                                //step forward in time

                                if (smart.step()) {
                                    /**
                                     * UPDATE GUI HERE
                                     */

                                    if (Platform.isFxApplicationThread()) {
                                        System.out.println("IS AN FX APPLICATION THREAD");
                                    } else {
                                        System.out.println("IS AN FX APPLICATION THREAD");
                                    }

                                    cashiers = smart.getCashiers();
                                    //generates the cashiers


                                    for (int i = 0; i < cashiers.size(); i++) {
                                        Text g = new Text("Line Length: " + cashiers.get(i).getLineLength());
                                        cashiersLabel.add(g);
                                        int count = i;
                                        int lineLength = cashiers.get(count).getLineLength();
                                        if (!simulationPane.getChildren().contains(cashiersLabel.get(i))) {
                                           Platform.runLater(()-> simulationPane.add(cashiersLabel.get(count), (count), 3));

                                        } else {
                                            //updates customer text
                                            Platform.runLater(()->cashiersLabel.get(count).setText("Line Length: " + lineLength));
                                        }
                                        //generate customer line
                                    }

                                    if (Customers.size() != 0) {
                                        Platform.runLater(()-> simulationPane.getChildren().removeAll(Customers));
                                    }

                                    //generate the customer objects
                                    for (int z = 0; z < cashiers.size(); z++) {

                                        for (int y = 0; y < cashiers.get(z).getLineLength(); y++) {
                                            Text c = new Text("Customer " + cashiers.get(z).getCustomers().get(y));
                                            Customers.add(c);
                                            int number = z;
                                            int number02 = y;
                                            Platform.runLater(()->simulationPane.add(c, number, number02 + 4));
                                        }
                                    }
                                }
                                System.out.println("CODE UPDATED");
                                try {
                                    Thread.sleep(1000);
                                }catch(InterruptedException e){

                                }

                                if(smart.getEventQueue().isEmpty()){
                                    end = true;
                                }

                            }


                        } catch (NumberFormatException ex) {
                            Alert alert = new Alert(AlertType.ERROR, "Your settings are incompatable. \nMake sure none of the fields are blank.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }.start();
            }

        });


        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                primaryStage.setScene(primaryScene);

            }

        });


        // Listeners
        // These Enforce only Integers in the text boxes
        txtMaxCustomers.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtMaxCustomers.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtNumberOfCheckoutLines.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtNumberOfCheckoutLines.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtMaxSimulationTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtMaxSimulationTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void runSimulation(Supermarket s, Scene x, GridPane g) {
        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
        ArrayList<Text> cashiersLabel = new ArrayList<Text>();
        ArrayList<Text> Customers = new ArrayList<Text>();


        /**
         * INITIALIZE GUI HERE
         */
        boolean end = false;
        while(!end) {
            //step forward in time

            if (s.step()) {
                /**
                 * UPDATE GUI HERE
                 */


                cashiers = s.getCashiers();
                //generates the cashiers


                for (int i = 0; i < cashiers.size(); i++) {
                    Text t = new Text("Line Length: " + cashiers.get(i).getLineLength());
                    cashiersLabel.add(t);
                    if (!g.getChildren().contains(cashiersLabel.get(i))) {
                        g.add(cashiersLabel.get(i), (i), 3);
                    } else {
                        //updates customer text
                        cashiersLabel.get(i).setText("Line Length: " + cashiers.get(i).getLineLength());
                    }
                    //generate customer line
                }


                //generate the customer objects
                for (int z = 0; z < cashiers.size(); z++) {

                    for (int y = 0; y < cashiers.get(z).getLineLength(); y++) {
                        Text c = new Text("Customer " + cashiers.get(z).getCustomers().get(y));
                        Customers.add(c);

                        g.add(c, z, y + 4);
                    }
                }
            }
            System.out.println("CODE UPDATED");

            if(s.getEventQueue().isEmpty()){
                end = true;
            }
        }

    }
}















