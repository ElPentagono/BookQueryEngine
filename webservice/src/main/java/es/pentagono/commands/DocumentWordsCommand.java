package es.pentagono.commands;

import es.pentagono.Command;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;
import es.pentagono.deserializers.GsonMetadataDeserializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentWordsCommand implements Command {

    private static final Map<String, Metadata> metadatas = new HashMap<>();
    private static final MetadataDeserializer deserializer = new GsonMetadataDeserializer();

    @Override
    public String execute(Map<String, String> parameters) {
        return Arrays.stream(parameters.get(":words").split("\\+"))
                .map(DocumentWordsCommand::toPath)
                .map(DocumentWordsCommand::getLines)
                .map(lines -> filterTo(lines, parameters.getOrDefault("to", null)))
                .map(lines -> filterFrom(lines, parameters.getOrDefault("from", null)))
                .map(lines -> filterAuthor(lines, parameters.getOrDefault("author", null)))
                .collect(Collectors.toList()).toString();
    }

    private List<String> filterAuthor(List<String> lines, String parameter) {
        if (parameter == null) return lines;
        return lines.stream()
                .filter(line -> {
                    Metadata metadata = getMetadata(line.split("\t")[0]);
                    if (metadata.author.isBlank()) return false;
                    String author = metadata.author;
                    return author.equals(parameter);})
                .collect(Collectors.toList());
    }

    private List<String> filterFrom(List<String> lines, String parameter) {
        if (parameter == null) return lines;
        return lines.stream()
                .filter(line -> {
                    Metadata metadata = getMetadata(line.split("\t")[0]);
                    if (metadata.releaseDate.equals("Unknown")) return false;
                    String[] date = metadata.releaseDate.split(" ");
                    int year = Integer.parseInt(date[date.length-1]);
                    return year >= Integer.parseInt(parameter);})
                .collect(Collectors.toList());
    }

    private List<String> filterTo(List<String> lines, String parameter) {
        if (parameter == null) return lines;
        return lines.stream()
                .filter(line -> {
                    Metadata metadata = getMetadata(line.split("\t")[0]);
                    if (metadata.releaseDate.equals("Unknown")) return false;
                    String[] date = metadata.releaseDate.split(" ");
                    int year = Integer.parseInt(date[date.length-1]);
                    return year <= Integer.parseInt(parameter);})
                .collect(Collectors.toList());
    }


    private static List<String> filterLines(List<String> lines, Map<String, String> parameters) {
        return null;
    }


    private static Metadata getMetadata(String uuid) {
        if (!metadatas.containsKey(uuid)) metadatas.put(uuid, deserializer.deserialize(getMetadataJson(uuid)));
        return metadatas.get(uuid);
    }

    private static String getMetadataJson(String uuid) {
        try {
            return Files.readString(Path.of(System.getenv("DATAMART") + "/metadata/content/" + uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getLines(Path path) {
        try {
            if (!Files.exists(path))
                return List.of(path.getFileName().toString() + " does not exist");
            List<String> lines = Files.readAllLines(path);
            lines.remove(0);
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path toPath(String word) {
        return Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word);
    }
}
