package logic.order.StoreOrder;

import logic.Store;
import logic.order.Order;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StoreOrder extends Order {

    Store storeUsed;
    private Map<Integer, OrderedItemFromStore> orderedItemsNotFromSale;
    private Map<String, OrderedItemFromStore> orderedItemsFromSale;

    public StoreOrder(Store store, Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        this.storeUsed = store;
        orderedItemsNotFromSale = new HashMap<Integer, OrderedItemFromStore>();
    }

    public StoreOrder(Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        orderedItemsNotFromSale = new HashMap<Integer, OrderedItemFromStore>();
    }
    @Override
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem)
    {
        boolean itemAlreadyExistsInOrder=false;
        if(orderedItemsNotFromSale != null)
        {
            itemAlreadyExistsInOrder = orderedItemsNotFromSale.containsKey(serialIDOfItem);
        }
        return itemAlreadyExistsInOrder;
    }

    public Map<Integer, OrderedItemFromStore> getOrderedItemsNotFromSale()
    {
        return orderedItemsNotFromSale;
    }

    public OrderedItemFromStore getItemInOrder(int serialIDOfItem) {
        return getOrderedItemsNotFromSale().get(serialIDOfItem);
    }

    public Store getStoreUsed() {
        return storeUsed;
    }

    public int getAmountOfCertainItemByUnit(int serialId)
    {
        int amountOfCertainItemByUnit;
        if(orderedItemsNotFromSale.containsKey(serialId))
        {
            amountOfCertainItemByUnit = orderedItemsNotFromSale.get(serialId).getAmountOfItemOrderedByUnits();
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
        if(orderedItemsNotFromSale.containsKey(serialId))
        {
            amountOfCertainItemByUnit = orderedItemsNotFromSale.get(serialId).getTotalAmountOfItemOrderedByTypeOfMeasure();
        }
        else
        {
            amountOfCertainItemByUnit = 0;
        }
        return amountOfCertainItemByUnit;
    }

}
