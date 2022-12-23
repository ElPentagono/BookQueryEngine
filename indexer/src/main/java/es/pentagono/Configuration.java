package es.pentagono;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties config;

    private static void loadConfig() {
        config = new Properties();
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("indexer.properties")) {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (config == null) loadConfig();
        return config.getProperty(key);
    }
}
