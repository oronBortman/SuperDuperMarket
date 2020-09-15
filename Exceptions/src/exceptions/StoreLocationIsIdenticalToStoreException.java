package exceptions;

import logic.Store;

import javax.xml.stream.Location;

public class StoreLocationIsIdenticalToStoreException extends Throwable {

    Store firstStore;
    Store secondStore;

    public StoreLocationIsIdenticalToStoreException(Store firstStore, Store secondStore)
    {
       this.firstStore = firstStore;
       this.secondStore = secondStore;
    }
}
