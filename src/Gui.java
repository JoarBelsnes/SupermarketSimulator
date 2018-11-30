
import javafx.application.Application;
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
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button; 
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gui extends Application {




    public void start(Stage primaryStage) {





        // Menu Bar
        MenuBar menuBar = new MenuBar();
        // Menu - File
        Menu menuFile = new Menu("File");
        MenuItem menuFileNew = new MenuItem("New");
        MenuItem menuFileLoad = new MenuItem("Load");
        MenuItem menuFileSave = new MenuItem("Save");
        MenuItem menuFileSaveAs = new MenuItem("Save As");
        MenuItem menuFileExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuFileNew, menuFileLoad, menuFileSave, menuFileSaveAs, menuFileExit);
        // Menu - Simulations
        Menu menuSimulation = new Menu("Simulation");
        MenuItem menuSimulationRun = new MenuItem("Run");
        MenuItem menuSimulationHalt = new MenuItem("Halt");
        menuSimulation.getItems().addAll(menuSimulationRun, menuSimulationHalt);

        menuBar.getMenus().addAll(menuFile, menuSimulation);

        // Creating Labels
        Text lblMaxCustomers = new Text("Max. Customers: ");
        Text lblNumberOfCheckoutLines = new Text("# of Checkout Lines: ");
        // Text lblNumberOfLimitedCheckoutLines = new Text("# of 'x Items or Less' Checkout Lines: ");
        Text lblMaxSimulationTime = new Text("Max. Simulation Time: ");
        Text lblQueueSelectionMethod = new Text("Queue Selection Method");


        // Creating Text Fields
        TextField txtMaxCustomers = new TextField();
        TextField txtNumberOfCheckoutLines = new TextField();
        // TextField txtNumberOfLimitedCheckoutLines = new TextField();
        TextField txtMaxSimulationTime = new TextField();

        // Combo Boxes
        ComboBox cmbQueueSelectionMethod = new ComboBox();
        cmbQueueSelectionMethod.getItems().addAll(
            "Store Assigned",
            "Customer Selected",
            "Random Assignment"
            );

        //run button

        Button runButton = new Button("Run Simulation");



        // Grid Pane
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(25);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(lblMaxCustomers, 0, 0);
        grid.add(txtMaxCustomers, 1, 0);
        grid.add(lblQueueSelectionMethod, 2, 0);
        grid.add(cmbQueueSelectionMethod, 2, 1, 2, 1);
        grid.add(lblNumberOfCheckoutLines, 0, 1);
        grid.add(txtNumberOfCheckoutLines, 1, 1);
        // grid.add(lblNumberOfLimitedCheckoutLines, 0, 2);
        // grid.add(txtNumberOfLimitedCheckoutLines, 1, 2);
        grid.add(lblMaxSimulationTime, 0, 3);
        grid.add(txtMaxSimulationTime, 1, 3);
        grid.add(runButton,1,4);
        // Scene Settings
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, grid);
        Scene primaryScene = new Scene(root, 600, 300);





        //simulation scene
        //array of cashiers
        ArrayList<Text> cashiers = new ArrayList<Text>();
        int numberOfLines = 4;
        //array of customers
        ArrayList<Text> Customers = new ArrayList<Text>();
        ArrayList<Integer> numberOfCustomers = new ArrayList<Integer>();

        numberOfCustomers.add(4);
        numberOfCustomers.add(6);
        numberOfCustomers.add(5);
        numberOfCustomers.add(7);


        GridPane simulationPane = new GridPane();
        simulationPane.setVgap(5);
        simulationPane.setHgap(25);
        simulationPane.setPadding(new Insets(5, 5, 5, 5));
        Button runButton2 = new Button("Run Simulation");
        Button back = new Button("Back");
        simulationPane.add(back, 5,1);
        simulationPane.add( runButton2, 1,1);


        Group root2 = new Group();
        root2.getChildren().addAll(simulationPane);


        Scene simulationScene = new Scene(root2, 800, 400);

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






        // Stage Settings
        primaryStage.setTitle("Supermarket Simulator");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        // Handlers
        menuFileExit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menuFileNew.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                txtMaxCustomers.setText("10");
                txtMaxSimulationTime.setText("50");
                txtNumberOfCheckoutLines.setText("2");
            }
        });
        menuSimulationRun.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                Supermarket smart = new Supermarket(Integer.parseInt(txtMaxSimulationTime.getText()), Integer.parseInt(txtMaxCustomers.getText()), Integer.parseInt(txtNumberOfCheckoutLines.getText()));
                smart.run();


            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                primaryStage.setScene(simulationScene);

            }
        });
        runButton2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                Supermarket smart = new Supermarket(Integer.parseInt(txtMaxSimulationTime.getText()), Integer.parseInt(txtMaxCustomers.getText()), Integer.parseInt(txtNumberOfCheckoutLines.getText()));
                smart.run();
            }

        });
        back.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                primaryStage.setScene(primaryScene);
            }

        });


        // Listeners
        // These Enforce only Integers in the text boxes
        txtMaxCustomers.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if (!newValue.matches("\\d*")) {
                    txtMaxCustomers.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtNumberOfCheckoutLines.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if (!newValue.matches("\\d*")) {
                    txtNumberOfCheckoutLines.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtMaxSimulationTime.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if (!newValue.matches("\\d*")) {
                    txtMaxSimulationTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
