
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui extends Application {




    public void start(Stage primaryStage) {



    Group root = new Group();
    ObservableList list = root.getChildren();
    StackPane pane = new StackPane();





    primaryStage.setTitle("Supermarket Simulator");
    primaryStage.setScene(new Scene(root, 200, 200));
    primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
