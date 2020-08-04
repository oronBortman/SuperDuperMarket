import java.util.Date;
import java.util.Map;

public class Order {
    private String serialNumber;
    private Date date;
    private Shop shopUsed;
    private boolean orderApprovedByCustomer;
    private Map<String, OrderedItem> orderedItems;

    public Date getDate()
    {
        return date;
    }

    public Shop getShop()
    {
        return shopUsed;
    }

    //TODO
    public int calcSumAmountOfItemsType()
    {
        return 1;
    }

    //TODO
    public int calcSumOfItems()
    {
        return 1;
    }

    //TODO
    public int calcDeliveryPrice()
    {
        return 1;
    }

    //TODO
    public int calcTotalPriceOfOrder()
    {
        return 1;
    }

    //TODO
    public int calcTotalPriceOfItems()
    {
        return 1;
    }

    //TODO
    public int calcAmoutOfCertainItem(OrderedItem item)
    {
        return 1;
    }

    //TODO
    public int calcSumOfPriceOfCertainItem(OrderedItem item)
    {
        return 1;
    }

}
