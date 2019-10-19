package ua.serstarstory.swing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
        private HashMap<JPanel, Integer> x = new HashMap<>();
        private HashMap<JPanel, Integer> y = new HashMap<>();
        private HashMap<JPanel, JsonElement> map = new HashMap<>();
        private File file;
        private JsonObject json;
        private JPanel panel;

        public JsonParser(File file) {
                this.file = file;
                try {
                        json = SManager.gson.fromJson(new FileReader(file), JsonObject.class);
                } catch (FileNotFoundException e) {
                        return;
                }
                panel = new JPanel();
                SManager.jFrame.setPanel(panel);
                setEditPane();
                parseObject("Начать", json, panel);
        }

        private void parseObject(String key, JsonObject o, JPanel p) {
                JButton button = new JButton(key);
                JPanel panel = new JPanel();
                p.add(button);
                SManager.jFrame.pack();
                button.addActionListener(l -> {
                        setPane(panel);
                });
                map.put(panel, o);
                for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
                        check(entry.getKey(), entry.getValue(), panel);
                }
        }

        private void check(String key, JsonElement element, JPanel panel) {
                if (element.isJsonObject()) {
                        parseObject(key, (JsonObject) element, panel);
                }
                if (element.isJsonPrimitive()) {
                        parsePrimitive(key, (JsonPrimitive) element, panel);
                }
        }

        private void parsePrimitive(String key, JsonPrimitive element, JPanel panel) {
                Component component = null;
                JLabel label = new JLabel(key);
                Val val=new Val(element);
                if (element.isBoolean()) {
                        JCheckBox box = new JCheckBox();
                        box.setSelected(element.getAsBoolean());
                        box.setVisible(true);
                        box.addActionListener(l -> {
                                if (map.get(panel).isJsonObject()) {
                                        ((JsonObject) map.get(panel)).add(key, new JsonPrimitive(box.isSelected()));
                                }else if(map.get(panel).isJsonArray()){
                                        JsonArray array= (JsonArray) map.get(panel);
                                        array.remove(val.getVal());
                                        array.add(box.isSelected());
                                        val.setVal(new JsonPrimitive(box.isSelected()));
                                }
                        });
                        component = box;
                } else {
                        JTextField field = new JTextField();
                        field.setText(element.getAsString());
                        field.addActionListener(l->{
                                JsonPrimitive value=null;
                                try {
                                        value=new JsonPrimitive(Integer.parseInt(field.getText()));
                                }catch (NumberFormatException e){
                                        value=new JsonPrimitive(field.getText());
                                }
                                if(map.get(panel).isJsonObject()){
                                        ((JsonObject) map.get(panel)).add(key,value);
                                }else if(map.get(panel).isJsonArray()){
                                        JsonArray array= (JsonArray) map.get(panel);
                                        array.remove(val.getVal());
                                        val.setVal(value);
                                        array.add(val.getVal());
                                }
                        });
                        component = field;
                }
                panel.add(label);
                panel.add(component);

        }

        private void setPane(JPanel pane) {
                SManager.jFrame.setPanel(pane);
                SManager.jFrame.pack();
        }

        private void setEditPane() {
                JButton button = new JButton("Undo");
                button.setBounds(0, 30, 100, 40);
                JButton b = new JButton("Done");
                b.setBounds(500, 30, 100, 40);
                button.addActionListener(l -> {
                        setPane(panel);
                });
                b.addActionListener(l->{
                        end();
                        setPane(panel);
                });
                SManager.jFrame.getEditPanel().add(button);
                SManager.jFrame.getEditPanel().add(b);
                SManager.jFrame.pack();
        }
        private void end(){
                try {
                        FileWriter writer=new FileWriter(file);
                        writer.write(SManager.gson.toJson(json));
                        writer.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }
}
class Val{
        private JsonPrimitive val;
        public Val(JsonPrimitive v){
                val=v;
        }

        public void setVal(JsonPrimitive val) {
                this.val = val;
        }

        public JsonPrimitive getVal() {
                return val;
        }
}