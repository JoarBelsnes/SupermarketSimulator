
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

        // VBox and Scene Initialization
        VBox vBox = new VBox(menuBar);
        Scene primaryScene = new Scene(vBox, 400, 200);

        // Scene Settings

        // Stage Settings
        primaryStage.setTitle("Supermarket Simulator");
        primaryStage.setScene(primaryScene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
