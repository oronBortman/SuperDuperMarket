import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StaticOrder {

    private Date date;
    private Store storeUsed;
    private Map<Integer, OrderedItem> orderedItems;

    public StaticOrder(Store storeUsed, Date date)
    {
        orderedItems = new HashMap<Integer, OrderedItem>();
        this.storeUsed = storeUsed;
        this.date = date;
    }

    public StaticOrder(Store storeUsed, Map<Integer, OrderedItem> orderedItems, Date date )
    {
        this.date = date;
        this.orderedItems = orderedItems;
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

    public int getAmountOfCertainItemByUnit(int serialId)
    {
        int amountOfCertainItemByUnit;
        if(orderedItems.containsKey(serialId))
        {
            amountOfCertainItemByUnit = orderedItems.get(serialId).getAmountOfItemOrderedByUnits();
        }
        else
        {
            amountOfCertainItemByUnit = 0;
        }
        return amountOfCertainItemByUnit;
    }

    public boolean checkIfItemExistsInOrder(int serialId)
    {
        return orderedItems.containsKey(serialId);
    }

    public OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }

}
