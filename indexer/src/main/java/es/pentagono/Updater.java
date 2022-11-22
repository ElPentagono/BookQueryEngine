package es.pentagono;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Updater {
    private final String DOCUMENTS = System.getenv("DATALAKE") + "/documents";
    private final String EVENTS = System.getenv("DATAMART") + "/invertedindex/events/indexed.log";
    private static final List<String> UUIDS = new ArrayList<>();
    private final DocumentProcessor processor;

    public Updater(DocumentProcessor processor) {
        this.processor = processor;
        getProcessedDocuments();
    }

    private void getProcessedDocuments() {
        try {
            if (!new File(EVENTS).exists()) return;
            Scanner scanner = new Scanner(new FileReader(EVENTS));
            while (scanner.hasNextLine()) UUIDS.add(scanner.nextLine().split("\t")[1]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (!new File(DOCUMENTS).exists()) return;
        for (String uuid : getUnprocessedDocuments()) processor.onSomething(uuid);
    }

    private List<String> getUnprocessedDocuments() {
        List<String> unprocessed = new ArrayList<>();
        for (File file : new File(DOCUMENTS).listFiles()) {
            if (isProcessed(file.getName())) continue;
            unprocessed.add(file.getName());
        }
        return unprocessed;
    }

    private static boolean isProcessed(String uuid) {
        return UUIDS.stream().anyMatch(id -> id.equals(uuid));
    }
}
