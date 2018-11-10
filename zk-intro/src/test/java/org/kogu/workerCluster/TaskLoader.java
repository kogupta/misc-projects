package org.kogu.workerCluster;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.events.EventInterceptor;
import com.tangosol.net.events.annotation.Interceptor;
import com.tangosol.net.events.application.LifecycleEvent;
import com.tangosol.net.events.partition.TransferEvent;
import com.tangosol.net.events.partition.cache.CacheLifecycleEvent;

import static com.tangosol.net.events.partition.cache.CacheLifecycleEvent.Type.CREATED;
import static org.kogu.workerCluster.Util.option;
import static org.kogu.workerCluster.Util.populateTasks;
import static org.kogu.workerCluster.Util.taskCache;

@Interceptor(cacheLifecycleEvents = CREATED)
public class TaskLoader implements EventInterceptor<TransferEvent> {
    @Override
    public void onEvent(TransferEvent event) {
        String s = String.format(
                "Discovered event %s for partition-id %d from remote member %s\n",
                event.getType(),
                event.getPartitionId(),
                event.getRemoteMember());

        CacheFactory.log(s, CacheFactory.LOG_INFO);

        populateTasks();
    }
}
