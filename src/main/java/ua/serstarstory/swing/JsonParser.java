package ua.serstarstory.swing;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
                if (element.isBoolean()) {
                        JCheckBox box = new JCheckBox();
                        box.setSelected(element.getAsBoolean());
                        box.setVisible(true);
                        box.addActionListener(l -> {
                                if (map.get(panel).isJsonObject()) {
                                        ((JsonObject) map.get(panel)).add(key, new JsonPrimitive(box.isSelected()));
                                }
                        });
                        component = box;
                } else {
                        JTextField field = new JTextField();
                        field.setText(element.getAsString());
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

                SManager.jFrame.getEditPanel().add(button);
                SManager.jFrame.getEditPanel().add(b);
                SManager.jFrame.pack();
        }
}
