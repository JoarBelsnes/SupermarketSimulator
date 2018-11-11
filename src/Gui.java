
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.*;
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
        Text lblNumberOfLimitedCheckoutLines = new Text("# of 'x Items or Less' Checkout Lines: ");
        Text lblMaxSimulationTime = new Text("Max. Simulation Time: ");
        Text lblQueueSelectionMethod = new Text("Queue Selection Method");


        // Creating Text Fields
        TextField txtMaxCustomers = new TextField();
        TextField txtNumberOfCheckoutLines = new TextField();
        TextField txtNumberOfLimitedCheckoutLines = new TextField();
        TextField txtMaxSimulationTime = new TextField();

        // Combo Boxes
        ComboBox cmbQueueSelectionMethod = new ComboBox();
        cmbQueueSelectionMethod.getItems().addAll(
            "Store Assigned",
            "Customer Selected",
            "Random Assignment"
            );

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
        grid.add(lblNumberOfLimitedCheckoutLines, 0, 2);
        grid.add(txtNumberOfLimitedCheckoutLines, 1, 2);
        grid.add(lblMaxSimulationTime, 0, 3);
        grid.add(txtMaxSimulationTime, 1, 3);

        // Scene Settings
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, grid);
        Scene primaryScene = new Scene(root, 600, 300);
        // Stage Settings
        primaryStage.setTitle("Supermarket Simulator");
        primaryStage.setScene(primaryScene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
