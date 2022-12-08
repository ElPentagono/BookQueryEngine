package es.pentagono.commands;

import es.pentagono.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentWordsCommand implements Command {

    private final MetadataLoader loader;
    private final AppearanceSerializer serializer;
    private final Map<String, Filter> filters = new HashMap<>();

    public DocumentWordsCommand(MetadataLoader loader, AppearanceSerializer serializer) {
        this.loader = loader;
        this.serializer = serializer;
    }

    @Override
    public String execute(Map<String, String> parameters) {
        setFilters(parameters);
        return "{\"count\":" + countAppearances(parameters) + ",\"appearances\":[" + getAppearancesOccurrences(parameters) + "]}";
    }

    private void setFilters(Map<String, String> parameters) {
        initializeFilters();
        for (Map.Entry<String, String> entry : parameters.entrySet().stream().skip(1).collect(Collectors.toList())) {
            if (!List.of("from", "to", "author").contains(entry.getKey())) continue;
            if (entry.getKey().equals("from")) filters.put("from", Filter.FROM);
            if (entry.getKey().equals("to")) filters.put("to", Filter.TO);
            if (entry.getKey().equals("author")) filters.put("author", Filter.AUTHOR);
        }
    }

    private void initializeFilters() {
        this.filters.put("from", Filter.NULL);
        this.filters.put("to", Filter.NULL);
        this.filters.put("author", Filter.NULL);
    }

    private long countAppearances(Map<String, String> parameters) {
        return getAppearanceStream(parameters)
                .count();
    }

    private String getAppearancesOccurrences(Map<String, String> parameters) {
        return getAppearanceStream(parameters)
                .map(serializer::serialize)
                .collect(Collectors.joining(","));
    }

    private Stream<Appearance> getAppearanceStream(Map<String, String> parameters) {
        return Arrays.stream(parameters.get(":words").split("\\+")).parallel()
                .map(DocumentWordsCommand::toPath)
                .map(DocumentWordsCommand::getLines)
                .flatMap(List::stream)
                .map(s -> new Appearance(s.split("\t")[0], getMetadata(s.split("\t")[0]), s.split("\t")[2], s.split("\t")[1]))
                .filter(appearance -> applyFilters(parameters, appearance));
    }

    private boolean applyFilters(Map<String, String> parameters, Appearance appearance) {
        return filterParameter("from", appearance, parameters.getOrDefault("from", "")) &&
                filterParameter("to", appearance, parameters.getOrDefault("to", "")) &&
                filterParameter("author", appearance, parameters.getOrDefault("author", ""));
    }

    private boolean filterParameter(String parameter, Appearance appearance, String value) {
        return filters.get(parameter).filter(appearance, value);
    }

    private Metadata getMetadata(String uuid) {
        return loader.load(uuid);
    }

    private static Path toPath(String word) {
        return Path.of("/appI" + "/invertedIndexDatamart/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word); // System.getenv("DATAMART") + "/invertedIndex/index/"
    }

    private static List<String> getLines(Path path) {
        try {
            if (!Files.exists(path)) return List.of("\tNULL\t" + path.getFileName().toString());
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

    private interface Filter {

        Filter NULL = (appearance, value) -> true;

        Filter FROM = (appearance, value) -> Integer.parseInt(appearance.metadata.releaseDate.substring(appearance.metadata.releaseDate.length() - 4)) >= Integer.parseInt(value);

        Filter TO = (appearance, value) -> Integer.parseInt(appearance.metadata.releaseDate.substring(appearance.metadata.releaseDate.length() - 4)) <= Integer.parseInt(value);

        Filter AUTHOR = (appearance, value) -> appearance.metadata.author.equals(value);

        boolean filter(Appearance appearance, String value);
    }
}