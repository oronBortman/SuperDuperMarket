package exceptions;

import logic.Customer;

import javax.xml.stream.Location;

public class CustomerLocationIsIdenticalToCustomerException extends Throwable {

    Customer firstCustomer;
    Customer secondCustomer;
    public CustomerLocationIsIdenticalToCustomerException(Customer firstCustomer, Customer secondCustomer)
    {
        this.firstCustomer = firstCustomer;
        this.secondCustomer = secondCustomer;
    }

    public Customer getFirstCustomer() {
        return firstCustomer;
    }

    public Customer getSecondCustomer() {
        return secondCustomer;
    }
}
