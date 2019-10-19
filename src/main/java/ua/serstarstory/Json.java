package ua.serstarstory;

import com.google.gson.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Json {
        public static AnchorPane pane = new AnchorPane();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        private JsonObject json;
        private Button end = new Button("Done!");
        private HashMap<AnchorPane, JsonObject> objects = new HashMap<>();
        private HashMap<AnchorPane, JsonArray> arrays = new HashMap<>();
        private HashMap<AnchorPane, Integer> x = new HashMap<>();
        private HashMap<AnchorPane, Integer> y = new HashMap<>();
        private File file;

        public Json(JsonObject json, File file) {
                this.file = file;
                this.json = json;
                x.put(pane, 0);
                y.put(pane, 0);
                parseObject("Начать работу", json, pane);
                setPane(pane);
        }

        private void check(Object i, AnchorPane p) {
                String key = "";
                Object val = null;
                if (i instanceof Map.Entry) {
                        Map.Entry<String, JsonElement> map = (Entry<String, JsonElement>) i;
                        key = map.getKey();
                        val = map.getValue();
                } else if (i instanceof JsonElement) {
                        val = i;
                }
                if (val instanceof JsonObject) {
                        parseObject(key, (JsonObject) val, p);
                }
                if (val instanceof JsonPrimitive) {
                        parsePrimitive(key, (JsonPrimitive) val, p);
                }
                if (val instanceof JsonArray) {
                        parseArray(key, (JsonArray) val, p);
                }
        }

        private void parseArray(String k, JsonArray o, AnchorPane p) {
                AnchorPane panel = new AnchorPane();
                arrays.put(panel, o);
                x.put(panel, 0);
                y.put(panel, 0);
                addButton(k, panel, p);
                for (JsonElement i : o) {
                        check(i, panel);
                }
        }

        private void parsePrimitive(String k, JsonPrimitive o, AnchorPane p) {
                AnchorPane a = new AnchorPane();
                Label l = new Label(k + ":");
                if (k.equals("")) l.setText("FromArray:");
                Val val = new Val(o);
                if (!o.isBoolean()) {
                        TextField field = new TextField();
                        field.setText(o.getAsString());
                        l.setMaxWidth(80);
                        field.setMaxWidth(120);
                        field.setLayoutX(80);
                        field.setOnKeyReleased(e -> {
                                if (objects.containsKey(p)) {
                                        JsonPrimitive v;
                                        try {
                                                int b = Integer.parseInt(field.getText());
                                                v = new JsonPrimitive(b);

                                        } catch (NumberFormatException ex) {
                                                v = new JsonPrimitive(field.getText());
                                        }
                                        objects.get(p).add(k, v);

                                } else if (arrays.containsKey(p)) {
                                        JsonArray array = arrays.get(p);
                                        array.remove(val.get());
                                        JsonPrimitive v;
                                        try {
                                                int b = Integer.parseInt(field.getText());
                                                v = new JsonPrimitive(b);

                                        } catch (NumberFormatException ex) {
                                                v = new JsonPrimitive(field.getText());
                                        }
                                        array.add(v);
                                        val.set(v);

                                }
                        });
                        a.getChildren().addAll(l, field);
                } else {
                        CheckBox box = new CheckBox();
                        l.setMaxWidth(180);
                        box.setMaxWidth(20);
                        box.setLayoutX(180);
                        box.setSelected(o.getAsBoolean());
                        end.setOnMouseClicked(e -> {
                                if (objects.containsKey(p)) {
                                        objects.get(p).add(k, new JsonPrimitive(box.isSelected()));
                                } else if (arrays.containsKey(p)) {
                                        JsonArray array = arrays.get(p);
                                        array.remove(val.get());
                                        JsonPrimitive jp = new JsonPrimitive(box.isSelected());
                                        array.add(jp);
                                        val.set(jp);
                                }
                        });
                        a.getChildren().addAll(l, box);
                }

                add(a, p);
        }

        private void parseObject(String k, JsonObject o, AnchorPane p) {
                AnchorPane panel = new AnchorPane();
                objects.put(panel, o);
                x.put(panel, 0);
                y.put(panel, 0);
                addButton(k, panel, p);
                for (Entry<String, JsonElement> i : o.entrySet()) {
                        check(i, panel);
                }
        }

        public void setPane(AnchorPane pane) {
                Button button = new Button("Undo");
                button.setLayoutY(575);
                button.setMinWidth(60);
                button.setLayoutX(0);
                button.setOnMouseClicked(e -> {
                        if (pane != Json.pane)
                                setPane(Json.pane);
                        else Main.stage.getScene().setRoot(Main.StartPane);
                });
                pane.getChildren().add(button);
                end.setLayoutX(740);
                end.setLayoutY(575);
                end.setMinWidth(60);
                end.setOnMouseClicked(e -> {
                        setPane((AnchorPane) Main.StartPane);
                        end();
                });
                pane.getChildren().add(end);

                Main.stage.getScene().setRoot(pane);
        }

        public void add(Parent i, AnchorPane p) {

                i.setLayoutX(x.get(p));
                i.setLayoutY(y.get(p));
                if ((x.get(p) + 200) >= 800) {
                        y.put(p, y.get(p) + 25);
                        x.put(p, 0);
                } else x.put(p, x.get(p) + 200);

                p.getChildren().add(i);
        }

        private void addButton(String k, AnchorPane panel, AnchorPane p) {
                if(k.equals(""))k="FromArray";
                Button button = new Button(k);
                button.setOnAction(e -> setPane(panel));
                add(button, p);
        }

        private void end() {
                try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(gson.toJson(json));
                        writer.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                System.gc();
        }


}

class Val {
        private JsonPrimitive p;

        public Val(JsonPrimitive p) {
                this.p = p;
        }

        public JsonPrimitive get() {
                return p;
        }

        public void set(JsonPrimitive p) {
                this.p = p;
        }
}