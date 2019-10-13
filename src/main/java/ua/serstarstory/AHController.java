package ua.serstarstory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class AHController {
        private String res,res1;
        @FXML
        private ChoiceBox<String> select;
        @FXML
        private ChoiceBox<String> select1;
        @FXML
        private TextField RM;//Response Mask
        @FXML
        private TextField RU;//request url
        @FXML
        private Label desc;
        @FXML
        private Label desc1;
        @FXML
        private Button next;//next
        @FXML
        private AnchorPane req;//request pane
        @FXML
        private AnchorPane mys;//mysql
        @FXML
        private AnchorPane mys1;//mysql pane (handler)
        @FXML
        private TextField DN;//database name
        @FXML
        private TextField DP;//database port
        @FXML
        private TextField DU;//database user
        @FXML
        private TextField PU;//database user password
        @FXML
        private TextField DIP;//database IP
        @FXML
        private TextField DQ;//database query
        @FXML
        private TextField QP;//query params
        @FXML
        private TextField EM;//error message
        @FXML
        private TextField DN1;//database name
        @FXML
        private TextField DP1;//database port
        @FXML
        private TextField DU1;//database user
        @FXML
        private TextField PU1;//database user password
        @FXML
        private TextField DIP1;//database IP
        @FXML
        private TextField table;//table
        @FXML
        private TextField UC;//uuid Column
        @FXML
        private TextField UNC;//UserNameColumn
        @FXML
        private TextField ATC;//access Token Column
        @FXML
        private TextField SidC;//server ID column
        @FXML
        private TextField SU;//skins url
        @FXML
        private TextField CU;//cloaks url
        @FXML
        public void initialize(){
                select.getItems().add("accept");
                select.getItems().add("request");
                select.getItems().add("mysql");
                select1.getItems().addAll("mysql","memory");
                select1.getSelectionModel().selectedItemProperty().addListener((e,a,v)->{
                        mys1.setVisible(false);
                        res1=v;
                        switch (v){
                                case "memory":
                                        desc1.setText("UUID получается путем преобразования бинарного представления ника");
                                        break;
                                case "mysql":
                                        desc1.setText("Для получения UUID лаунчсервер обращается к базе данных mysql");
                                        mys1.setVisible(true);
                                        break;
                        }

                });
                req.setVisible(false);
                mys.setVisible(false);
                mys1.setVisible(false);
                select.getSelectionModel().selectedItemProperty().addListener((e,a,v)->{
                        res=v;
                        req.setVisible(false);
                        mys.setVisible(false);
                        switch (v){
                                case "accept":
                                        desc.setText("Принимает любые пары логин-пароль.Настройка не требуется");
                                        break;
                                case "request":
                                        desc.setText("Для проверки логина и пароля лаунчсервер обращается к сайту по протоколу HTTP/HTTPS");
                                        req.setVisible(true);
                                        break;
                                case "mysql":
                                        mys.setVisible(true);
                                        desc.setText("Для проверки логина и пароля лаунчсервер обращается к базе данных mysql");
                                        break;
                        }
                });
                next.setOnMouseClicked(e->{
                        HashMap<String,Object> provider=new HashMap<>();
                        switch (res){
                                case "request":
                                        provider.put("type","request");
                                        provider.put("url",RU.getText());
                                        provider.put("response",RM.getText());
                                        break;
                                case "accept":
                                        provider.put("type","accept");
                                        break;
                                case "mysql":
                                        provider.put("type","mysql");
                                        HashMap<String,Object> holder=new HashMap<>();
                                        holder.put("address",DIP.getText());
                                        holder.put("port",Integer.parseInt(DP.getText()));
                                        holder.put("username",DU.getText());
                                        holder.put("password",PU.getText());
                                        holder.put("database",DN.getText()+"?serverTimezone=UTC");
                                        holder.put("timezone","UTC");
                                        provider.put("mySQLHolder",holder);
                                        provider.put("query",DQ.getText());
                                        provider.put("queryParams",QP.getText().split(" "));
                                        provider.put("message",EM.getText());
                                        break;

                        }
                        HashMap<String,Object> handler =new HashMap<>();
                        switch (res1){
                                case "memory":
                                        handler.put("type","memory");
                                        break;
                                case "mysql":
                                        handler.put("type","mysql");
                                        HashMap<String,Object> holder=new HashMap<>();
                                        holder.put("address",DIP1.getText());
                                        holder.put("port",Integer.parseInt(DP1.getText()));
                                        holder.put("username",DU1.getText());
                                        holder.put("password",PU1.getText());
                                        holder.put("database",DN1.getText()+"?serverTimezone=UTC");
                                        holder.put("timezone","UTC");
                                        handler.put("mySQLHolder",holder);
                                        handler.put("table",table.getText());
                                        handler.put("uuidColumn",UC.getText());
                                        handler.put("usernameColumn",UNC.getText());
                                        handler.put("accessTokenColumn",ATC.getText());
                                        handler.put("serverIDColumn",SidC.getText());
                                        break;
                        }
                        HashMap<String,Object> texture=new HashMap<>();
                        texture.put("type","request");
                        texture.put("skinURL",SU.getText());
                        texture.put("cloakURL",CU.getText());
                        Main.manager.addAuth("provider",provider);
                        Main.manager.addAuth("handler",handler);
                        Main.manager.addAuth("textureProvider",texture);
                        Main.manager.setNetty();
                        Gson gson =new GsonBuilder().setPrettyPrinting().create();
                        File file =new File("LaunchServer.json");
                        try {
                                file.createNewFile();
                                Writer writer =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                                writer.write(gson.toJson(Main.manager));
                                writer.flush();
                        } catch (Throwable ex) {
                                ex.printStackTrace();
                        }

                });




        }
}
