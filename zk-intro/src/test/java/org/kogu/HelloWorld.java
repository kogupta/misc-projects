package org.kogu;

import com.google.common.collect.Iterators;
import com.tangosol.net.CoherenceSession;
import com.tangosol.net.NamedCache;
import com.tangosol.net.Session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        Session session = new CoherenceSession();

//        populateWorkers(session);
        populateTasks(session);
        allocateTasks2Workers(session);

        Thread.sleep(60_000);

        session.close();
    }

    private static void allocateTasks2Workers(Session session) {
        NamedCache<Object, Object> tasks = session.getCache("tasks");
        NamedCache<Object, Object> workers = session.getCache("workers");
        NamedCache<Object, Object> mapping = session.getCache("worker-task-mapping");
        Iterator<Object> _workers = Iterators.cycle(workers.keySet());

        Map<String, String> localMap = new HashMap<>();
        for (Object _task : tasks.keySet()) {
            String taskId = (String) _task;
            String workerId = (String) _workers.next();
            String key = workerId + "_+_" + taskId;
            localMap.put(key, taskId);
        }

        mapping.putAll(localMap);
    }

    private static void populateTasks(Session session) {
        NamedCache<Object, Object> cache = session.getCache("tasks");
        for (Task task : tasks(5)) {
            // for simplicity -  make it <String, String>
            // no serialization issue in cli
            cache.put(task.id, task.id);
        }

        System.out.println("# of tasks: " + cache.size());
    }

    private static void populateWorkers(Session session) {
        NamedCache<Object, Object> cache = session.getCache("workers");
        cache.addMapListener(new Listener());
        for (Worker worker : workers(3)) cache.put(worker.name, worker.name);

        System.out.println("# of entries in `workers`: " + cache.size());
    }

    private static Worker[] workers(int n) {
        assert n > 0;
        Worker[] xs = new Worker[n];
        Arrays.setAll(xs, idx -> new Worker("worker-" + idx));
        return xs;
    }

    private static Task[] tasks(int n) {
        assert n > 0;
        Task[] xs = new Task[n];
        Arrays.setAll(xs, idx -> new Task("task:" + idx));
        return xs;
    }
}