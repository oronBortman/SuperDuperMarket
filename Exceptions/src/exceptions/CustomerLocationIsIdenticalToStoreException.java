package exceptions;

import logic.Customer;
import logic.Store;

import javax.xml.stream.Location;

public class CustomerLocationIsIdenticalToStoreException extends Throwable {
   Customer customer;
   Store store;

    public CustomerLocationIsIdenticalToStoreException(Customer customer, Store store)
    {
        this.customer = customer;
        this.store = store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }
}
