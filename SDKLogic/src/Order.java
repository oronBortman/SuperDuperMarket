import java.util.Date;
import java.util.Map;

public class Order {
    private String serialNumber;
    private Date date;
    private Shop shopUsed;
    private boolean orderApprovedByCustomer;
    private Map<String, OrderedItem> orderedItems;

    private int calcSumAmountOfItemsType()
    {
        return 1;
    }

    private int calcSumOfItems()
    {
        return 1;
    }

    private int calcDeliveryPrice()
    {
        return 1;
    }

    private int calcTotalPriceOfOrder()
    {
        return 1;
    }

    private int calcAmoutOfCertainItem(OrderedItem item)
    {
        return 1;
    }

    private int calcSumOfPriceOfCertainItem(OrderedItem item)
    {
        return 1;
    }
}
