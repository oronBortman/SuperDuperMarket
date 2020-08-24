import java.util.ArrayList;
import java.util.Date;

public class OpenedCustomerOrder extends Order{

    ArrayList<OpenedStoreOrder> ordersFromStores;

    public OpenedCustomerOrder(Date date)
    {
        super(date);
    }



    public void addOrderFromStore(OpenedStoreOrder openedOrderFromStore)
    {
        ordersFromStores.add(openedOrderFromStore);
    }

    public double calcTotalDeliveryPrice(SDMLocation inputLocation);
    public ClosedCustomerOrder closeOrder(SDMLocation location);


    //TODO

}
