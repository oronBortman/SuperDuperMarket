package logic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Order {

    private Date date;
    private Map<Integer, OrderedItem> orderedItems;

    public Order(Date date)
    {
        orderedItems = new HashMap<Integer, OrderedItem>();
        this.date = date;
    }

    public Order(Map<Integer, OrderedItem> orderedItems, Date date )
    {
        this.date = date;
        this.orderedItems = orderedItems;
    }
    //TODO
    public Date getDate()
    {
        return date;
    }

    public Map<Integer, OrderedItem> getOrderedItems()
    {
        return orderedItems;
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

    public boolean checkIfItemExistsInOrder(int serialId)
    {
        return orderedItems.containsKey(serialId);
    }

    public OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }

}
