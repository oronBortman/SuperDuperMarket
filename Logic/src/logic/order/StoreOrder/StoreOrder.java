package logic.order.StoreOrder;

import logic.Store;
import logic.order.Order;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StoreOrder extends Order {

    Store storeUsed;
    private Map<Integer, OrderedItemFromStore> orderedItems;

    public StoreOrder(Store store, Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        this.storeUsed = store;
        orderedItems = new HashMap<Integer, OrderedItemFromStore>();
    }

    public StoreOrder(Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        orderedItems = new HashMap<Integer, OrderedItemFromStore>();
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

    public Map<Integer, OrderedItemFromStore> getOrderedItems()
    {
        return orderedItems;
    }

    public OrderedItemFromStore getItemInOrder(int serialIDOfItem) {
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
