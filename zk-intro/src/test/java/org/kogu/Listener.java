package org.kogu;

import com.tangosol.util.Base;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

public final class Listener extends Base implements MapListener {
    @Override
    public void entryInserted(MapEvent mapEvent) {
        out("entry inserted: " + mapEvent);
    }

    @Override
    public void entryUpdated(MapEvent mapEvent) {
        out("entry updated: " + mapEvent);
    }

    @Override
    public void entryDeleted(MapEvent mapEvent) {
        out("entry deleted: " + mapEvent);
    }
}
