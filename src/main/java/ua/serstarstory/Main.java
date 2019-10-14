package ua.serstarstory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
        public static Stage stage;
        public static Parent LaunchServerPane;
        public static Parent AuthHandler;
        public static Parent StartPane;
        public static Manager manager = new Manager();

        public static void main(String[] args) throws IOException {
                launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                stage = primaryStage;
                StartPane = FXMLLoader.load(getClass().getResource("/start.fxml"));
                LaunchServerPane = FXMLLoader.load(getClass().getResource("/LS.fxml"));
                AuthHandler = FXMLLoader.load(getClass().getResource("/AH.fxml"));
                primaryStage.setScene(new Scene(StartPane, 800, 600));
                primaryStage.setTitle("Gravit Launcher Configurator");
                primaryStage.show();
                /*File file=new File("json.json");
                FileReader reader=new FileReader(file);
                Json json=new Json(gson.fromJson(reader, JsonObject.class));*/
        }
}
