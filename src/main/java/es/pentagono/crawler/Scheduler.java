package es.pentagono.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Scheduler {
    private final Timer timer;
    private final List<Task> tasks = new ArrayList<>();

    public Scheduler(Timer timer) {
        this.timer = timer;
    }

    public void add(Task task) {
        tasks.add(task);
    }
    
    public void start() {
        timer.schedule(timertask(), 1000);
    }

    private TimerTask timertask() {
        return new TimerTask() {
            @Override
            public void run() {
                for (Task task : tasks) {
                    task.execute();
                }
            }
        };
    }
}
