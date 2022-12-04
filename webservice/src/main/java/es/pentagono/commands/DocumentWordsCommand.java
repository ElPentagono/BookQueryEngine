package es.pentagono.commands;

import es.pentagono.Appearance;
import es.pentagono.AppearanceSerializer;
import es.pentagono.Command;
import es.pentagono.MetadataDeserializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentWordsCommand implements Command {

    private final MetadataDeserializer deserializer;
    private final AppearanceSerializer serializer;

    public DocumentWordsCommand(MetadataDeserializer deserializer, AppearanceSerializer serializer) {
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    @Override
    public String execute(Map<String, String> parameters) {
        return "{\"count\":" +
                Arrays.stream(parameters.get(":words").split("\\+")).parallel()
                        .map(DocumentWordsCommand::toPath)
                        .map(DocumentWordsCommand::getLines)
                        .mapToLong(List::size)
                        .sum() +
                ", \"appearances\": [" +
                Arrays.stream(parameters.get(":words").split("\\+")).parallel()
                        .map(DocumentWordsCommand::toPath)
                        .map(DocumentWordsCommand::getLines)
                        .flatMap(List::stream)
                        .map(s-> new Appearance(s.split("\t")[0], bookTitle(s.split("\t")[0]),  s.split("\t")[2], s.split("\t")[1]))
                        .map(serializer::serialize)
                        .collect(Collectors.joining(","))
                + "]}";

    }

    private String bookTitle(String uuid) {
        try {
            return deserializer.deserialize(Files.readString(Path.of(System.getenv("DATAMART") + "/metadata/content/" + uuid))).title;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getLines(Path path) {
        try {
            if (!Files.exists(path)) return List.of(path.getFileName().toString() + " does not exist");
            return Files.lines(path).skip(1).parallel()
                    .map(line -> toString(line, path.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toString(String line, String word) {
        return line + "\t" + word;
    }

    private static Path toPath(String word) {
        return Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word);
    }
}