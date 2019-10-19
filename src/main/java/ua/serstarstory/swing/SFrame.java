package ua.serstarstory.swing;

import javax.swing.*;
import java.awt.*;

public class SFrame extends JFrame {
        private JPanel edit;
        private JPanel last = new JPanel();

        public SFrame() {
                super("Configurator");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(null);
                setPreferredSize(new Dimension(600, 400));
                setResizable(false);
                setVisible(true);
                createEditPane();
                pack();
        }

        public void setPanel(JPanel panel) {
                remove(last);
                pack();
                last = panel;
                panel.setBounds(0, 0, 600, 300);
                add(panel);
                pack();
                repaint();
        }

        private void createEditPane() {
                edit = new JPanel();
                edit.setLayout(null);
                edit.setBounds(0, 300, 600, 100);
                add(edit);
                pack();
        }

        public JPanel getEditPanel() {
                return edit;
        }


}
