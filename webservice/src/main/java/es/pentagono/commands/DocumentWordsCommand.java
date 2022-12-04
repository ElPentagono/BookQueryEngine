package es.pentagono.commands;

import es.pentagono.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentWordsCommand implements Command {

    private final MetadataLoader loader;
    private final AppearanceSerializer serializer;

    public DocumentWordsCommand(MetadataLoader loader, AppearanceSerializer serializer) {
        this.loader = loader;
        this.serializer = serializer;
    }

    @Override
    public String execute(Map<String, String> parameters) {
        return "{\"count\":" + countAppearances(parameters) + ",\"appearances\":[" + getAppearances(parameters) + "]}";
    }

    private long countAppearances(Map<String, String> parameters) {
        return Arrays.stream(parameters.get(":words").split("\\+")).parallel()
                .map(DocumentWordsCommand::toPath)
                .map(DocumentWordsCommand::getLines)
                .flatMap(List::stream)
                .map(s -> new Appearance(s.split("\t")[0], getMetadata(s.split("\t")[0]), s.split("\t")[2], s.split("\t")[1]))
                .filter(a -> parameters.get("author") == null || a.metadata.author.equals(parameters.get("author")))
                .filter(a -> parameters.get("to")==null || Integer.parseInt(a.metadata.releaseDate.substring(a.metadata.releaseDate.length() - 4)) <= Integer.parseInt(parameters.get("to")))
                .filter(a -> parameters.get("from")==null || Integer.parseInt(a.metadata.releaseDate.substring(a.metadata.releaseDate.length() - 4)) >= Integer.parseInt(parameters.get("from")))
                .count();
    }

    private String getAppearances(Map<String, String> parameters) {
        return Arrays.stream(parameters.get(":words").split("\\+")).parallel()
                .map(DocumentWordsCommand::toPath)
                .map(DocumentWordsCommand::getLines)
                .flatMap(List::stream)
                .map(s -> new Appearance(s.split("\t")[0], getMetadata(s.split("\t")[0]), s.split("\t")[2], s.split("\t")[1]))
                .filter(a -> parameters.get("author") == null || a.metadata.author.equals(parameters.get("author")))
                .filter(a -> parameters.get("to")==null || Integer.parseInt(a.metadata.releaseDate.substring(a.metadata.releaseDate.length() - 4)) <= Integer.parseInt(parameters.get("to")))
                .filter(a -> parameters.get("from")==null || Integer.parseInt(a.metadata.releaseDate.substring(a.metadata.releaseDate.length() - 4)) >= Integer.parseInt(parameters.get("from")))
                .map(serializer::serialize)
                .collect(Collectors.joining(","));
    }

    private Metadata getMetadata(String uuid) {
        return loader.load(uuid);
    }

    private static Path toPath(String word) {
        return Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word);
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
}