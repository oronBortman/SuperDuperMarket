import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private Date date;
    private Store storeUsed;
    private Map<Integer, OrderedItem> orderedItems;

    Order(Store storeUsed)
    {
        orderedItems = new HashMap<Integer, OrderedItem>();
        this.storeUsed = storeUsed;
    }
    //TODO
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

    public void addItemToItemsMapOfOrder(OrderedItem orderedItem)
    {
        orderedItems.put(orderedItem.getSerialNumber(), orderedItem);
    }

}
