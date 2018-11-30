
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

public class Gui extends Application {




    public void start(Stage primaryStage) {


        // Variables
        int customersInStore = 0;


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

        menuBar.getMenus().addAll(menuFile);

        // Creating Labels
        Text lblMaxCustomers = new Text("Max. Customers: ");
        Text lblNumberOfCheckoutLines = new Text("# of Checkout Lines: ");
        // Text lblNumberOfLimitedCheckoutLines = new Text("# of 'x Items or Less' Checkout Lines: ");
        Text lblMaxSimulationTime = new Text("Max. Simulation Time: ");


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
        grid.add(runButton,1,4);
        // Scene Settings
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, grid);
        Scene primaryScene = new Scene(root, 600, 300);





        //simulation scene
        //array of cashiers
        //array of customers

        GridPane simulationPane = new GridPane();
        simulationPane.setVgap(5);
        simulationPane.setHgap(25);
        simulationPane.setPadding(new Insets(5, 5, 5, 5));
        Button runButton2 = new Button("Run Simulation");
        Button back = new Button("Back");
        simulationPane.add(back, 24,1);
        simulationPane.add( runButton2, 1,1);

        Canvas simulationCanvas = new Canvas(800, 400);
        Group root2 = new Group();
        root2.getChildren().addAll(simulationPane);


        Scene simulationScene = new Scene(root2, 800, 400);







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
        runButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                runButton2.setDisable(false);
                primaryStage.setScene(simulationScene);

            }
        });
        runButton2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                runButton2.setDisable(true);
                Supermarket smart = new Supermarket(Integer.parseInt(txtMaxSimulationTime.getText()), Integer.parseInt(txtMaxCustomers.getText()), Integer.parseInt(txtNumberOfCheckoutLines.getText()));
                Thread martThread = new Thread(smart::call);
                martThread.start();
                back.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent t) {
                        martThread.stop();
                        primaryStage.setScene(primaryScene);
                    }
                });
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
