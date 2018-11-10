package org.kogu.workerCluster;

import com.google.common.collect.Iterators;
import com.tangosol.net.*;
import com.tangosol.net.cache.TypeAssertion.WithTypesAssertion;
import com.tangosol.util.Base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.kogu.workerCluster.Util.*;

public class WorkerListener extends Base implements MemberListener {

    @Override
    public void memberJoined(MemberEvent memberEvent) {
        out("-------\njoined: " + memberEvent + "\n-------");

        NamedCache<String, String> workers = CacheFactory.getCache(workerCache);

        // TODO
        String workerId = memberEvent.toString();
        workers.put(workerId, workerId);

        displayCache(taskCache);
        allocateTasks2Workers();
    }

    @Override
    public void memberLeaving(MemberEvent memberEvent) {
        out("-------\nleaving: " + memberEvent + "\n-------");
    }

    @Override
    public void memberLeft(MemberEvent memberEvent) {
        out("-------\nleft: " + memberEvent + "\n-------");

        allocateTasks2Workers();
    }
}
