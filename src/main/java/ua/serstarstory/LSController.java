package ua.serstarstory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class LSController {
        public static String ip;
        @FXML
        private TextField PN;//projectName
        @FXML
        private TextField BN;//binaryName
        @FXML
        private TextField WLE;//WhiteListError
        @FXML
        private TextField SS;//startScript
        @FXML
        private TextField IP;//IP address
        @FXML
        private TextField port;//StandartPort

        @FXML
        private Button next;//nextButton

        @FXML
        private CheckBox CJE;//cope jar and exe to updates
        @FXML
        private CheckBox GMPG;//genMapping ProGuard
        @FXML
        private CheckBox WJV;//warning Java Version
        @FXML
        private CheckBox OPG;//Onn ProGuard
        @FXML
        private CheckBox ASM;//ASM
        @FXML
        private CheckBox DTF;//DeleteTempFile
        @FXML
        private CheckBox CWL;//Compress launcher
        @FXML
        private CheckBox ALBPG;//attachLibraryBeforeProGuard
        @FXML
        private CheckBox CE;//certeficate enabled
        @FXML
        private CheckBox L4J;//launch4j

        @FXML
        public void initialize(){
                next.setOnMouseClicked(e->{

                        HashMap<String,Object> launcher=new HashMap<>();
                        launcher.put("guardType","no");
                        launcher.put("attachLibraryBeforeProGuard",ALBPG.isSelected());
                        launcher.put("compress",CWL.isSelected());
                        launcher.put("warningMissArchJava",WJV.isSelected());
                        launcher.put("enabledProGuard",OPG.isSelected());
                        launcher.put("stripLineNumbers",true);
                        launcher.put("deleteTempFiles",DTF.isSelected());
                        launcher.put("proguardGenMappings",GMPG.isSelected());
                        Main.manager.setLauncher(launcher);
                        Main.manager.setCertificate(CE.isSelected());
                        Main.manager.setStartScript(SS.getText());
                        Main.manager.setBinaryName(BN.getText());
                        Main.manager.setCopyBinaries(CJE.isSelected());
                        Main.manager.setProjectName(PN.getText());
                        Main.manager.setWhitelistRejectString(WLE.getText());
                        Main.manager.setL4J(L4J.isSelected());
                        Main.stage.getScene().setRoot(Main.AuthHandler);
                        ip=IP.getText();
                });
        }
}
