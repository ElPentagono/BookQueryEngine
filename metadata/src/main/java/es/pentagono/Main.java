package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.readers.FSMetadataReader;

import java.io.File;
import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        FSMetadataReader reader = new FSMetadataReader(new GsonMetadataDeserializer());
        File file = new File(Configuration.getProperty("datalake") + "/documents");
        while (!file.exists()) {}

        Scheduler scheduler = new Scheduler(new Timer());
        scheduler.add(new UpdateTask(file, reader));
        scheduler.start();

    }
}