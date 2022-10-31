package es.pentagono.crawler.persisters;

import es.pentagono.crawler.Event;
import es.pentagono.crawler.EventPersister;
import es.pentagono.crawler.events.StoreEvent;

import java.io.*;

public class TsvEventPersister implements EventPersister {
    public final String PATH = "C:/Users/juanc/IdeaProjects/BookQueryEngine/datalake/updates.log"; // TODO
    @Override
    public void persist(Event event) {
        try {
            StoreEvent storeEvent = (StoreEvent) event;
            File f = new File(PATH);
            FileWriter writer = new FileWriter(f, true);
            initializeFile(f, writer);
            writeEvent(writer, storeEvent);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeEvent(FileWriter writer, StoreEvent event) throws IOException {
        writer.write(event.ts + "\t" + event.source + "\t" + event.uuid + "\t" + event.md5 + "\n");
    }

    private void initializeFile(File f, FileWriter fw) throws IOException {
        if (f.exists()) return;
        createFile(f);
        writeHeader(fw);
    }

    private static void createFile(File f) throws IOException {
        f.mkdirs();
        f.createNewFile();
    }

    private void writeHeader(FileWriter writer) throws IOException {
        writer.write("ts\tsrc\tuuid\tmd5\n");
    }

}
