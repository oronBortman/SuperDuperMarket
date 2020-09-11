package logic.order.StoreOrder;

import logic.Store;
import logic.order.Order;
import logic.order.itemInOrder.OrderedItem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StoreOrder extends Order {

    Store storeUsed;
    private Map<Integer, OrderedItem> orderedItems;

    public StoreOrder(Store store, Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        this.storeUsed = store;
        orderedItems = new HashMap<Integer, OrderedItem>();
    }

    public StoreOrder(Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        orderedItems = new HashMap<Integer, OrderedItem>();
    }
    @Override
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem)
    {
        boolean itemAlreadyExistsInOrder=false;
        if(orderedItems != null)
        {
            itemAlreadyExistsInOrder = orderedItems.containsKey(serialIDOfItem);
        }
        return itemAlreadyExistsInOrder;
    }

    public Map<Integer, OrderedItem> getOrderedItems()
    {
        return orderedItems;
    }

    public OrderedItem getItemInOrder(int serialIDOfItem) {
        return getOrderedItems().get(serialIDOfItem);
    }

    public Store getStoreUsed() {
        return storeUsed;
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


    public double getAmountOfCertainItemByTypeOfMeasure(int serialId)
    {
        double amountOfCertainItemByUnit;
        if(orderedItems.containsKey(serialId))
        {
            amountOfCertainItemByUnit = orderedItems.get(serialId).getTotalAmountOfItemOrderedByTypeOfMeasure();
        }
        else
        {
            amountOfCertainItemByUnit = 0;
        }
        return amountOfCertainItemByUnit;
    }

}
