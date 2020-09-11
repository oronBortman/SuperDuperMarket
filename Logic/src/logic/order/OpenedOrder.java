package logic.order;

import logic.order.itemInOrder.OrderedItem;
import logic.SDMLocation;
import logic.Store;

import java.util.Date;
import java.util.Map;

public interface OpenedOrder {

    //TODO
    public abstract double calcTotalDeliveryPrice(SDMLocation inputLocation);

    public double calcTotalPriceOfOrder(SDMLocation inputLocation);

    public abstract double calcTotalPriceOfItems();

    public abstract int calcTotalAmountOfItemsByUnit();
    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/

    public abstract boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem);

    public abstract int calcTotalAmountOfItemsType();
}
