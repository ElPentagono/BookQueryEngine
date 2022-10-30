package es.pentagono.crawler;

import es.pentagono.crawler.tasks.DownloadTask;

import java.util.List;
import java.util.TimerTask;

public class Scheduler extends TimerTask {
    private List<Task> tasks;

    public void addTask(int id) {
        tasks.add(new DownloadTask(id));
    }
    @Override
    public void run() {
        for (Task task : tasks) {
            task.execute();
        }
    }
}
