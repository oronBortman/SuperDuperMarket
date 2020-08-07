import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private Date date;
    private Store storeUsed;
    private Map<Integer, OrderedItem> orderedItems;

    Order(Store storeUsed)
    {
        Map<Integer, OrderedItem> orderedItems = new HashMap<Integer, OrderedItem>();

    }
    public Date getDate()
    {
        return date;
    }

    public Store getShop()
    {
        return storeUsed;
    }

    public Map<Integer, OrderedItem> getOrderedItems()
    {
        return orderedItems;
    }

    public Store getStoreUsed() {
        return storeUsed;
    }

    public int calcTotalAmountOfItemsType()
    {
        return orderedItems.size();
    }





}
