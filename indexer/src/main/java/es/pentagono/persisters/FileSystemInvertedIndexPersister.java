package es.pentagono.persisters;

import es.pentagono.InvertedIndexPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSystemInvertedIndexPersister implements InvertedIndexPersister {

    private final String TSV_HEADER = "ts\tuuid\n";

    @Override
    public void persist(Map<String, String> invertedIndex) {
        for (String word : invertedIndex.keySet()) {
            File file = new File(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0,2));
            createDirectoryWord(file.getAbsolutePath());
            write(file.getAbsolutePath() + String.format("/%s", word), invertedIndex.get(word));
        }
    }

    @Override
    public void persist(String event) {
        String path = String.format(System.getenv("DATAMART"));
        if (! Files.exists(Paths.get(path + "/invertedIndex/events/indexed.log"))) createLogFile(path + "/invertedIndex/events" );
        writeEvent(path + "/invertedIndex/events/indexed.log", event);
    }

    private void createDirectoryWord(String path) {
        if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
    }

    private static void write(String path, String content) {
        try {
            if (!Files.exists(Paths.get(path))) createFile(path, "id\tposition");
            FileWriter writer = new FileWriter(path, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createFile(String path, String text) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createLogFile(String path) {
        new File(path).mkdirs();
        createFile(path + "/indexed.log", TSV_HEADER);
    }

    private void writeEvent(String path, String event) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(event);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
