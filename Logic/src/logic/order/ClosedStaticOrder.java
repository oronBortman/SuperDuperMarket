package logic.order;

import logic.order.itemInOrder.OrderedItem;
import logic.Store;

import java.util.Date;
import java.util.Map;

public class ClosedStaticOrder extends ClosedOrder {
    Store storeUsed;
    public ClosedStaticOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Store storeUsed, Map<Integer, OrderedItem> orderedItems, Date date) {
        super(deliveryPrice, totalPriceOfOrder, totalAmountOfItemsByUnit, totalAmountOfItemTypes, totalPriceOfItems, orderedItems, date);
        this.storeUsed = storeUsed;
    }

    public Store getStoreUsed() {
        return storeUsed;
    }
}


