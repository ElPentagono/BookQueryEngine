package es.pentagono;

import java.io.*;
import java.util.Properties;

public class Configuration {

    private static Properties config;

    private static void loadConfig() {
        config = new Properties();
        try (InputStream in = new FileInputStream("app/crawler.properties")) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (config == null) loadConfig();
        return config.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        if (config == null) loadConfig();
        config.setProperty(key, value);
    }

    public static void saveConfig() {
        try (OutputStream out = new FileOutputStream("app/crawler.properties")) {
            config.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
