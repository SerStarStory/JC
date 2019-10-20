package ua.serstarstory.swing;

import javax.swing.*;
import java.io.File;

import static java.awt.Component.CENTER_ALIGNMENT;

public class SMain {
        public static void main(String[] args) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                SManager.jFrame = new SFrame();
                JPanel panel = new JPanel();
                JButton button = new JButton("Выбрать файл");
                button.setAlignmentX(CENTER_ALIGNMENT);

                button.addActionListener(e -> {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Открыть файл");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                                File file = fileopen.getSelectedFile();
                                new JsonParser(file);
                        }
                });
                panel.add(button);
                SManager.start = panel;
                SManager.jFrame.setPanel(panel);
                //new JsonParser(new File("json.json"));
        }

}
