package ua.serstarstory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
        public static Stage stage;
        public static Parent LaunchServerPane;
        public static Parent AuthHandler;
        public static Manager manager=new Manager();
        @Override
        public void start(Stage primaryStage) throws Exception {
                stage=primaryStage;
                Parent pane= FXMLLoader.load(getClass().getResource("/start.fxml"));
                LaunchServerPane=FXMLLoader.load(getClass().getResource("/LS.fxml"));
                AuthHandler=FXMLLoader.load(getClass().getResource("/AH.fxml"));
                primaryStage.setScene(new Scene(pane,800,600));
                primaryStage.setTitle("Gravit Launcher Configurator");
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
