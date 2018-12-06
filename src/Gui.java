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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

/**
 * Gui Class
 * Runs Supermarket Simulation in a Graphical User Interface
 * User can modify settings, save settings, load previously saved settings
 * Switches to Simulation View, where one can watch live data while the simulation runs
 * Will call results window at the end.
 * Contributors: Andrew, Liam, Joshua
 */
public class Gui extends Application {

    public void start(Stage primaryStage) {

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
        Text lblMeanArrivalTime = new Text("Customer Mean Arrival Time: ");
        Text lblMaxSimulationTime = new Text("Customer Arrival Window: ");
        Text lblCustomersShopping = new Text("Customers Shopping: ");
        Text dataCustomersShopping = new Text("0");
        Text lblCustomersInQueue = new Text("Customers In Queue: ");
        Text dataCustomersInQueue = new Text("0");
        Text lblCustomersDone = new Text("Customers Exited: ");
        Text dataCustomersDone = new Text("0");
        Text lblAverageQueueTime = new Text("Average Queue Time: ");
        Text dataAverageQueueTime = new Text("0");
        Text lblSimulationTime = new Text ("Current Time: ");
        Text dataSimulationTime = new Text ("0");
        Text lblLongestQueue = new Text ("Longest Queue Time: ");
        Text dataLongestQueue = new Text ("0");
        Text lblNumberOfRageQuiters = new Text ("Customers Abandoned: ");
        Text dataNumberOfRageQuiters = new Text ("0");
        Text lblSupermarket = new Text("Supermarket");
        Text lblBlank1 = new Text(" ");
        Text lblBlank2 = new Text(" ");

        // Creating Text Fields
        TextField txtMaxCustomers = new TextField();
        TextField txtMeanArrivalTime = new TextField();
        TextField txtArrivalWindow = new TextField();

        // Buttons
        Button runButton = new Button("Run Simulation");
        Button runButton2 = new Button("Start Simulation");
        Button back = new Button("Return");
        runButton.setMinWidth(80.0);
        runButton2.setMinWidth(80.0);
        back.setMinWidth(80.0);

        // Combo Box
        final ComboBox<Integer> cmbNumberOfCheckoutsLines = new ComboBox<Integer>();
        cmbNumberOfCheckoutsLines.getItems().addAll(1, 2, 3, 4, 5);
        cmbNumberOfCheckoutsLines.setValue(2);

        // Setup Scene
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(25);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(lblMaxCustomers, 0, 0);
        grid.add(txtMaxCustomers, 1, 0);
        grid.add(lblNumberOfCheckoutLines, 0, 1);
        grid.add(cmbNumberOfCheckoutsLines, 1, 1);
        grid.add(lblMeanArrivalTime, 0, 2);
        grid.add(txtMeanArrivalTime, 1, 2);
        grid.add(lblMaxSimulationTime, 0, 3);
        grid.add(txtArrivalWindow, 1, 3);
        grid.add(runButton, 1, 4);
        
        // Scene Settings
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, grid);
        Scene primaryScene = new Scene(root, 600, 300);

        // Simulation Scene
        // Left Pane
        GridPane simulationLeftPane = new GridPane();
        simulationLeftPane.setPadding(new Insets(10, 10, 10, 10));
        simulationLeftPane.add(lblSimulationTime, 0, 0);
        simulationLeftPane.add(dataSimulationTime, 1, 0);
        simulationLeftPane.add(lblCustomersShopping, 0, 1);
        simulationLeftPane.add(dataCustomersShopping, 1, 1);
        simulationLeftPane.add(lblCustomersInQueue, 0, 2);
        simulationLeftPane.add(dataCustomersInQueue, 1, 2);
        simulationLeftPane.add(lblCustomersDone, 0, 3);
        simulationLeftPane.add(dataCustomersDone, 1, 3);
        simulationLeftPane.add(lblAverageQueueTime, 0, 4);
        simulationLeftPane.add(dataAverageQueueTime, 1, 4);
        simulationLeftPane.add(lblLongestQueue, 0, 5);
        simulationLeftPane.add(dataLongestQueue, 1, 5);
        simulationLeftPane.add(lblNumberOfRageQuiters, 0, 6);
        simulationLeftPane.add(dataNumberOfRageQuiters, 1, 6);
        simulationLeftPane.add(lblBlank1, 0, 9);
        simulationLeftPane.add(lblBlank2, 1, 9);    
        simulationLeftPane.add(runButton2, 0, 10);
        simulationLeftPane.add(back, 1, 10);

        // Set CSS
        simulationLeftPane.setId("SimulationPane");
        lblSimulationTime.setId("LiveData");
        dataSimulationTime.setId("LiveData");
        lblCustomersShopping.setId("LiveData");
        dataCustomersShopping.setId("LiveData");
        lblCustomersInQueue.setId("LiveData");
        dataCustomersInQueue.setId("LiveData");
        lblCustomersDone.setId("LiveData");
        dataCustomersDone.setId("LiveData");
        lblAverageQueueTime.setId("LiveData");
        dataAverageQueueTime.setId("LiveData");
        lblSupermarket.setId("Cashiers");

        // Center Pane
        GridPane simulationPane = new GridPane();
        simulationPane.setId("SimulationPane");
        simulationPane.setPadding(new Insets(5, 5, 5, 5));

        // Top Pane
        BorderPane simulationTopPane = new BorderPane();
        simulationTopPane.setPadding(new Insets(25, 15, 25, 15));
        simulationTopPane.setCenter(lblSupermarket);
        simulationTopPane.setId("SimulationPane");
        
        BorderPane simulationOverPane = new BorderPane();
        simulationOverPane.setCenter(simulationPane);
        simulationOverPane.setLeft(simulationLeftPane);
        simulationOverPane.setTop(simulationTopPane);
        simulationOverPane.setId("SimulationPane");

        Group root2 = new Group();
        root2.setId("Root");
        root2.getChildren().addAll(simulationOverPane);

        simulationPane.setPrefSize(800, 600);
        simulationLeftPane.setPrefWidth(125);
        Scene simulationScene = new Scene(root2, 1000, 600);

        simulationScene.setFill(Color.BLACK);
        simulationScene.getStylesheets().add("simulation.css");

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
                txtArrivalWindow.setText("50");
                cmbNumberOfCheckoutsLines.setValue(2);
                txtMeanArrivalTime.setText("25");
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
                        txtArrivalWindow.setText(supermarketSettings[0]);
                        txtMaxCustomers.setText(supermarketSettings[1]);
                        cmbNumberOfCheckoutsLines.setValue(Integer.parseInt(supermarketSettings[2]));
                        txtMeanArrivalTime.setText(supermarketSettings[3]);
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
                        fileWriter.write(txtArrivalWindow.getText() + "," + txtMaxCustomers.getText() + "," + cmbNumberOfCheckoutsLines.getValue() + "," + txtMeanArrivalTime.getText());
                        fileWriter.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(Integer.parseInt(txtMeanArrivalTime.getText()) < Integer.parseInt(txtArrivalWindow.getText())){
                    runButton2.setDisable(false);
                    primaryStage.setScene(simulationScene);

                } else {
                        Alert alertMeanGreaterThanMax = new Alert(AlertType.ERROR, "Your settings are incompatable. \nMake sure the \"Customer Mean Arrival\" time \nis less than the \"Customer Arrival Window\".", ButtonType.OK);
                        alertMeanGreaterThanMax.showAndWait();
                }


            }
        });

        /**
         * run button on the simulation screen.
         * when clicked, runs the simulation with the specified settings
         * contributors: Andrew, Liam
         */
        runButton2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                runButton2.setDisable(true);
                new Thread(() -> {
                    try {
                        Supermarket smart = new Supermarket(Integer.parseInt(txtArrivalWindow.getText()),
                                Integer.parseInt(txtMaxCustomers.getText()),
                                cmbNumberOfCheckoutsLines.getValue(),
                                Integer.parseInt(txtMeanArrivalTime.getText()));

                        //SsimulationPane.setPrefWidth(100*Integer.parseInt(txtNumberOfCheckoutLines.getText()));
                        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
                        ArrayList<Label> cashiersLabel = new ArrayList<Label>();
                        ArrayList<Label> Customers = new ArrayList<Label>();

                        /**
                         * INITIALIZE GUI HERE
                         */
                        boolean end = false;
                        while (!end) {
                            //initialize labels
                            Platform.runLater(()->dataSimulationTime.setText(Integer.toString(smart.getCurrentTime())));
                            Platform.runLater(()->dataCustomersShopping.setText(Integer.toString(smart.getNumberOfShoppingCustomers())));
                            Platform.runLater(()->dataCustomersInQueue.setText(Integer.toString(smart.getNumberOfQueuedCustomers())));
                            Platform.runLater(()->dataCustomersDone.setText(Integer.toString(smart.getNumberOfDepartedCustomers())));
                            Platform.runLater(()->dataAverageQueueTime.setText("" + smart.getAverageQueueTime()));
                            Platform.runLater(()->dataLongestQueue.setText("" + smart.getLongestQueueTime()));
                            Platform.runLater(()->dataNumberOfRageQuiters.setText("" + smart.getRageQuit()));

                            //increment simulation time
                            if (smart.step()) {
                                //update the GUI
                                cashiers = smart.getCashiers();
                                //generates the cashiers
                                for (int i = 0; i < cashiers.size(); i++) {
                                    Label g = new Label("Line Length: " + cashiers.get(i).getLineLength());
                                    g.setId("Cashiers");
                                    Border b = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3)));
                                    g.setBorder(b);
                                    cashiersLabel.add(g);
                                    int count = i;
                                    int lineLength = cashiers.get(count).getLineLength();
                                    if (!simulationPane.getChildren().contains(cashiersLabel.get(i))) {
                                        Platform.runLater(() -> simulationPane.add(cashiersLabel.get(count), (count), 3));

                                    } else {
                                        //updates customer text
                                        Platform.runLater(() -> cashiersLabel.get(count).setText("Line Length: " + lineLength));
                                    }
                                }

                                if (Customers.size() != 0) {
                                    Platform.runLater(() -> simulationPane.getChildren().removeAll(Customers));
                                }

                                //generate the customer objects
                                for (int z = 0; z < cashiers.size(); z++) {

                                    for (int y = 0; y < cashiers.get(z).getLineLength(); y++) {
                                        Label c = new Label("Customer " + cashiers.get(z).getCustomers().get(y));
                                        c.setId("Customers");
                                        Customers.add(c);
                                        int number = z;
                                        int number02 = y;
                                        Platform.runLater(() -> simulationPane.add(c, number, number02 + 4));
                                    }
                                }
                            }
                            //print the event queue to the console
                            System.out.println("Current events in queue:");
                            for (Event e : smart.getEventQueue()) {
                                System.out.print(e.getEventType() + ", ");
                            }
                            System.out.println("");

                            //wait for one second
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {

                            }

                            //end the simulation if there are no more events
                            if (smart.getEventQueue().isEmpty()) {
                                end = true;
                                //open the results window
                                smart.results();
                                Platform.runLater(() -> new ResultsStage(smart));
                            }

                        }

                    } catch (NumberFormatException ex) {
                        //warn users not to leave fields blank
                        Alert alert = new Alert(AlertType.ERROR, "Your settings are incompatable. \nMake sure none of the fields are blank.", ButtonType.OK);
                        alert.showAndWait();
                    }
                }).start();
            }

        });


        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                primaryStage.setScene(primaryScene);
                // new ResultsStage();

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
        txtArrivalWindow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtArrivalWindow.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtMeanArrivalTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtMeanArrivalTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
