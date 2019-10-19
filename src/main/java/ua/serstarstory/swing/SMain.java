package ua.serstarstory.swing;

import javax.swing.*;
import java.io.File;

public class SMain {
        public static void main(String[] args) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                SManager.jFrame = new SFrame();
                new JsonParser(new File("json.json"));
        }

}
