package ua.serstarstory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {
        @FXML
        private Button LaunchServer;

        @FXML
        public void initialize(){
                LaunchServer.setOnMouseClicked(event -> {
                        Main.stage.getScene().setRoot(Main.LaunchServerPane);
                });
        }
}
