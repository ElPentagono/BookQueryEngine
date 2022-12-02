package es.pentagono.commands;

import es.pentagono.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentWordsCommand implements Command {

    @Override
    public String execute(Map<String, String> parameters) {
        String[] split = parameters.get(":words").split("\\+");

        return Arrays.stream(split)
                .map(DocumentWordsCommand::toPath)
                .map(DocumentWordsCommand::getLines)
                .collect(Collectors.toList()).toString();
    }

    private static List<String> getLines(Path path) {
        try {
            if (!Files.exists(path))
                return List.of(path.getFileName().toString() + " does not exist");
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path toPath(String word) {
        return Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2) + "/" + word);
    }
}
