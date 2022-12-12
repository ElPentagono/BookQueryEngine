package es.pentagono;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Scheduler {
    private final Timer timer;
    private final List<Task> tasks = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

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
                    executor.execute(task::execute);
                }
            }
        };
    }
}
