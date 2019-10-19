package ua.serstarstory.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ua.serstarstory.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StartController {
        @FXML
        private Button LaunchServer;
        @FXML
        private Button AnyJson;
        @FXML
        private TextField path;

        @FXML
        public void initialize() {
                LaunchServer.setOnMouseClicked(event -> {
                        Main.stage.getScene().setRoot(Main.LaunchServerPane);
                });
                AnyJson.setOnMouseClicked(e -> {
                        try {
                                File file = new File(path.getText());
                                FileReader reader = new FileReader(file);
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                        }

                });
        }
}
