package ua.serstarstory.swing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SManager {
        public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
        public static SFrame jFrame;
}
