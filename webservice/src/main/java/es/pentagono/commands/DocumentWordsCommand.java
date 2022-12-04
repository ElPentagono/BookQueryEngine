package es.pentagono.commands;

import es.pentagono.Appearance;
import es.pentagono.AppearanceSerializer;
import es.pentagono.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentWordsCommand implements Command {

    private final AppearanceSerializer serializer;

    public DocumentWordsCommand(AppearanceSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public String execute(Map<String, String> parameters) {
        return "{\"count\":" +
                Arrays.stream(parameters.get(":words").split("\\+"))
                        .map(DocumentWordsCommand::toPath)
                        .map(DocumentWordsCommand::getLines)
                        .mapToLong(List::size)
                        .sum() +
                ", \"appearances\": [" +
                Arrays.stream(parameters.get(":words").split("\\+"))
                        .map(DocumentWordsCommand::toPath)
                        .map(DocumentWordsCommand::getLines)
                        .flatMap(List::stream)
                        .map(s-> new Appearance(s.split("\t")))
                        .map(serializer::serialize)
                        .collect(Collectors.joining(","))
                + "]}";
    }


    private static List<String> getLines(Path path) {
        try {
            if (!Files.exists(path)) return List.of(path.getFileName().toString() + " does not exist");
            return Files.lines(path).skip(1).parallel().map(Object::toString).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path toPath(String word) {
        return Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word);
    }
}