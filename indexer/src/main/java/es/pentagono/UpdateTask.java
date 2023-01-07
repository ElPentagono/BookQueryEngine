package es.pentagono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateTask implements Task {
    private static final String DOCUMENTS = Configuration.getProperty("datalake") + "/documents";
    private static final String EVENTS = Configuration.getProperty("datalake") + "/events/invertedIndex.log";
    private final DocumentProcessor processor;

    public UpdateTask(DocumentProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute() {
        if (!Files.exists(Path.of(DOCUMENTS))) return;
        for (String uuid : unprocessedDocuments()) processor.process(uuid);
    }

    private List<String> unprocessedDocuments() {
        return Arrays.stream(new File(DOCUMENTS).listFiles())
                .map(File::getName)
                .filter(uuid -> !processedDocuments().contains(uuid))
                .collect(Collectors.toList());
    }

    private static List<String> processedDocuments() {
        try {
            if (!Files.exists(Path.of(EVENTS))) return List.of();
            return Files.readAllLines(Path.of(EVENTS)).stream()
                    .map(l -> l.split("\t")[1])
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
