package ua.serstarstory.swing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
        private WindowListener listener;

        public JsonParser(File file) {
                start();
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

        private void start() {
                SManager.jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                listener = new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                                Object[] options = {"Да", "Нет!"};
                                int rc = JOptionPane.showOptionDialog(
                                        e.getWindow(), "Вы уверены что хотите выйти?" +
                                                "Прогресс настроек не сохранится",
                                        "Подтверждение", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null, options, options[0]);
                                if (rc == 0) {
                                        e.getWindow().setVisible(false);
                                        System.exit(0);
                                }
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                };
                SManager.jFrame.addWindowListener(listener);
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
                if (element.isJsonArray()) {
                        parseArray(key, (JsonArray) element, panel);
                }
        }

        private void parseArray(String key, JsonArray array, JPanel p) {
                JPanel panel = new JPanel();
                JButton button = new JButton(key);
                p.add(button);
                button.addActionListener(l -> {
                        setPane(panel);
                });
                SManager.jFrame.pack();
                int i = 0;
                map.put(panel, array);
                for (JsonElement e : array) {
                        check("FromArray_" + i++, e, panel);
                }
        }

        private void parsePrimitive(String key, JsonPrimitive element, JPanel panel) {
                Component component;
                JLabel label = new JLabel(key + ":");
                Val val = new Val(element);
                if (element.isBoolean()) {
                        JCheckBox box = new JCheckBox();
                        box.setSelected(element.getAsBoolean());
                        box.setVisible(true);
                        box.addActionListener(l -> {
                                if (map.get(panel).isJsonObject()) {
                                        ((JsonObject) map.get(panel)).add(key, new JsonPrimitive(box.isSelected()));
                                } else if (map.get(panel).isJsonArray()) {
                                        JsonArray array = (JsonArray) map.get(panel);
                                        array.remove(val.getVal());
                                        array.add(box.isSelected());
                                        val.setVal(new JsonPrimitive(box.isSelected()));
                                }
                        });
                        component = box;
                } else {
                        JTextField field = new JTextField();
                        field.setPreferredSize(new Dimension(120, 20));
                        field.setText(element.getAsString());
                        field.addActionListener(l -> {
                                System.out.println(field.getWidth());
                                JsonPrimitive value;
                                try {
                                        value = new JsonPrimitive(Integer.parseInt(field.getText()));
                                } catch (NumberFormatException e) {
                                        value = new JsonPrimitive(field.getText());
                                }
                                if (map.get(panel).isJsonObject()) {
                                        ((JsonObject) map.get(panel)).add(key, value);
                                } else if (map.get(panel).isJsonArray()) {
                                        JsonArray array = (JsonArray) map.get(panel);
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
                button.addActionListener(l -> setPane(panel));
                b.addActionListener(l -> {
                        end();
                        setPane(SManager.start);
                });
                SManager.jFrame.getEditPanel().add(button);
                SManager.jFrame.getEditPanel().add(b);
                SManager.jFrame.pack();
        }

        private void end() {
                try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(SManager.gson.toJson(json));
                        writer.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                SManager.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                SManager.jFrame.removeWindowListener(listener);


        }
}

class Val {
        private JsonPrimitive val;

        public Val(JsonPrimitive v) {
                val = v;
        }

        public JsonPrimitive getVal() {
                return val;
        }

        public void setVal(JsonPrimitive val) {
                this.val = val;
        }
}