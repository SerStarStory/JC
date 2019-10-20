package ua.serstarstory.swing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;


public class SManager {
        public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
        public static SFrame jFrame;
        public static JPanel start;
}
