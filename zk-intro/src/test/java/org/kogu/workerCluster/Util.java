package org.kogu.workerCluster;

import com.google.common.collect.Iterators;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.TypeAssertion;
import org.kogu.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.tangosol.net.CacheFactory.getCache;
import static com.tangosol.net.CacheFactory.getTypedCache;
import static com.tangosol.util.Base.out;

public enum Util {
    ;

    public static final String workerCache = "workers";
    public static final String taskCache = "tasks";
    public static final String workerTaskMapping = "worker-task-mapping";

    public static final TypeAssertion<String, String> option = TypeAssertion.withTypes(String.class, String.class);

    public static void allocateTasks2Workers() {
        NamedCache<String, String> tasks = getTypedCache(taskCache, option);
        NamedCache<String, String> workers = getTypedCache(workerCache, option);
        NamedCache<String, String> mapping = getTypedCache(workerTaskMapping, option);
        Iterator<String> _workers = Iterators.cycle(workers.keySet());

        Map<String, String> localMap = new HashMap<>();
        for (String taskId : tasks.keySet()) {
            String workerId = _workers.next();
            String key = workerId + "_+_" + taskId;
            localMap.put(key, taskId);
        }

        mapping.putAll(localMap);

        displayCache("worker-task-mapping");
    }

    public static void displayCache(String name) {
        NamedCache<String, String> currMapping = getTypedCache(name, option);
        String s = name + " - " + currMapping.keySet();
        out("-------------------\n" + s + "\n-------------------");
    }

    public static void populateTasks() {
        CacheFactory.ensureCluster();
        NamedCache<String, String> tasks = getTypedCache(taskCache, option);

        for (Task task : tasks(5)) {
            // for simplicity -  make it <String, String>
            // no serialization issue in cli
            tasks.put(task.id, task.id);
        }

        System.out.println("# of tasks: " + tasks.size());
    }

    private static Task[] tasks(int n) {
        assert n > 0;
        Task[] xs = new Task[n];
        Arrays.setAll(xs, idx -> new Task("task:" + idx));
        return xs;
    }
}
