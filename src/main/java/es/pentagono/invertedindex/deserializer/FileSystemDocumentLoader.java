package es.pentagono.invertedindex.deserializer;

import com.google.gson.Gson;
import es.pentagono.Document;
import es.pentagono.Metadata;
import es.pentagono.MetadataBuilder;
import es.pentagono.invertedindex.DocumentLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemDocumentLoader implements DocumentLoader {

    public final MetadataBuilder builder = new MetadataBuilder();

    @Override
    public Document load(String id) {
        try {
            String path = String.format("C:/Users/juanc/IdeaProjects/BookQueryEngine/documents/%s", id); // TODO
            return new Document(id, getMetadata(path + "/metadata.json"), getContentOf(path + "/content.txt"));
        }
        catch (Exception exception) {
            throw exception;
        }

    }

    private Metadata getMetadata(String path) {
        return builder.build(getContentOf(path));
    }

    private String getContentOf(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
