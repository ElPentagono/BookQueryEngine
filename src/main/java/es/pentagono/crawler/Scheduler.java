package es.pentagono.crawler;

import java.util.List;
import java.util.TimerTask;

public class Scheduler extends TimerTask {
    private List<Task> tasks;

    public void addTask(Task task) {
        tasks.add(task);
    }
    @Override
    public void run() {
        for (Task task : tasks) {
            task.execute();
        }
    }
}
