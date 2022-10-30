package es.pentagono.crawler.tasks;

import es.pentagono.crawler.DownloadEvent;
import es.pentagono.crawler.GutenbergSource;
import es.pentagono.crawler.Task;

import java.io.IOException;

public class DownloadTask implements Task {
    private final int id;

    public DownloadTask(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
        try {
            DownloadEvent event = new GutenbergSource().readBook(id);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
