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
            write(storeEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void write(StoreEvent storeEvent) throws IOException {
        FileWriter writer = new FileWriter(PATH, true);
        if (isEmpty()) writeHeader(writer);
        writer.write(storeEvent.ts + "\t" + storeEvent.source + "\t" + storeEvent.uuid + "\t" + storeEvent.md5 + "\n");
        writer.close();
    }

    private void writeHeader(FileWriter writer) throws IOException {
        writer.write("ts\tsrc\tuuid\tmd5\n");
    }

    private boolean isEmpty() {
        return new File(PATH).length()==0;
    }
}
