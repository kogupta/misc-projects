package org.kogu;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface ResourceManager {
    // workers
    // tasks
    //   - allocated
    //   - free

    // on startup, lookup tasks
    // on worker startup, they will register
    //

    List<Task> allTasks();
    List<Task> allocatedTasks();
    List<Task> unAllocatedTasks();

    List<Worker> allWorkers();
    List<Task> tasksInWorker(String worker);
    Optional<Worker> workerDetails(String task);


    final class Task {
        final String name;
        private boolean isAssigned;

        public Task(String name) {this.name = name;}
    }

    final class Worker {
        final String id;
        private long lastBeat;
        final List<Task> allocatedTasks;

        public Worker(String id) {
            this.id = id;
            lastBeat = 0;
            allocatedTasks = new CopyOnWriteArrayList<>();
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            Runnable r = () -> { /* TODO */ }; // send heartbeat - update a timestamp in parent?
            executor.scheduleAtFixedRate(r, 30, 30, TimeUnit.SECONDS);
        }
    }

}
