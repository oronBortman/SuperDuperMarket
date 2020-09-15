package exceptions;

import logic.Customer;
import logic.Store;

import javax.xml.stream.Location;

public class StoreLocationIsIdenticalToCustomerException extends Exception {
    Store store;
    Customer customer;

    public StoreLocationIsIdenticalToCustomerException(Store store, Customer customer)
    {
        this.store = store;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }
}
