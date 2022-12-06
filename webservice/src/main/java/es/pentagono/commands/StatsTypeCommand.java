package es.pentagono.commands;

import es.pentagono.*;
import es.pentagono.deserializers.events.StoreEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsTypeCommand implements Command {

    public final EventSerializer eventserializer;
    public final MetadataSerializer metadataSerializer;
    public final MetadataManager manager;

    public StatsTypeCommand(EventSerializer serializer, MetadataSerializer metadataSerializer, MetadataManager manager) {
        this.eventserializer = serializer;
        this.metadataSerializer = metadataSerializer;
        this.manager = manager;
    }

    @Override
    public String execute(Map<String, String> parameters) {
        String req = parameters.get(":type");
        if (req.equals("metadata")) return metadataInfo();
        if (req.equals("documents")) return datalakeInfo();
        return "Not found (Error 404)";
    }

    private String datalakeInfo() {
        try {
            return "{\"count\":" + countAppearancesDatalake() + ",\"appearances\":[" + getEvents() + "]}";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int countAppearancesDatalake() {
        return new File(System.getenv("DATALAKE") + "/documents").list().length;
    }

    private String getEvents() throws IOException {
        return Files.lines(Path.of(System.getenv("DATALAKE") + "/events/updates.log"))
                .skip(1)
                .map(l -> new StoreEvent(l.split("\t")[0], l.split("\t")[1], l.split("\t")[2], l.split("\t")[3]))
                .map(eventserializer::serialize)
                .collect(Collectors.joining(","));
    }

    private String metadataInfo() {
        return "{\"occurrences\":" + countAppearancesDatamart() + ",\"appearances\":[" + getInfo() + "]}";
    }

    private String getInfo() {
        return manager.metadata().stream()
                .map(metadataSerializer::serialize)
                .collect(Collectors.joining(","));
    }

    private int countAppearancesDatamart() {
        return manager.count();
    }
}
