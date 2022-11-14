package es.pentagono.persisters;

import es.pentagono.InvertedIndexPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSystemInvertedIndexPersister implements InvertedIndexPersister {


    @Override
    public void persist(Map<String, String> invertedIndex) {
        for (String word : invertedIndex.keySet()) {
            File file = new File(System.getenv("DATAMART") + "/invertedIndex/" + word.charAt(0) + "/" + word.substring(0,2));
            createDirectoryWord(file.getAbsolutePath());
            write(file.getAbsolutePath() + String.format("/%s", word), invertedIndex.get(word));
        }
    }

    private void createDirectoryWord(String path) {
        if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
    }

    private static void write(String path, String content) {
        try {
            if (!Files.exists(Paths.get(path))) createFile(path);
            FileWriter writer = new FileWriter(path, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void createFile(String file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("id\tposition");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
