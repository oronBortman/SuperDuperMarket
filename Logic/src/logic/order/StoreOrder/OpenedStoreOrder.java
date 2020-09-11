package logic.order.StoreOrder;

import logic.SDMLocation;
import logic.Store;
import logic.order.OpenedOrder;
import logic.order.itemInOrder.OrderedItem;

import java.util.Date;
import java.util.Map;

public class OpenedStoreOrder extends StoreOrder implements OpenedOrder {


    public OpenedStoreOrder(Store store, Date date, boolean isOrderStatic)
    {
        super(store, date, isOrderStatic);
    }

    public OpenedStoreOrder(Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
    }

    @Override
    public double calcTotalDeliveryPrice(SDMLocation inputLocation) {
        //TODO
        return 0;
    }

    public void addItemToItemsMapOfOrder(OrderedItem orderedItem)
    {
        getOrderedItems().put(orderedItem.getSerialNumber(), orderedItem);
    }

    public double calcDeliverPriceFromStore(SDMLocation inputLocation, Store store) {
        SDMLocation storeLocation = store.getLocationOfShop();
        int PPK = store.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }


    @Override
    public double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcTotalDeliveryPrice(inputLocation);
    }

    @Override
    public double calcTotalPriceOfItems()
    {
        return getOrderedItems().values().stream().mapToDouble(OrderedItem::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    @Override
    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getAmountOfItemOrderedByUnits).sum();
    }

    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/



    public int calcTotalAmountOfItemsType()
    {
        return getOrderedItems().size();
    }


    public ClosedStoreOrder closeOrder(SDMLocation location)
    {
        double totalPriceOfItems = calcTotalPriceOfItems();
        double deliveryPriceAfterOrderIsDone = calcTotalDeliveryPrice(location);
        double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        int totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        int totalAmountOfItemsType = calcTotalAmountOfItemsType();
        ClosedStoreOrder closedStoreOrder = new ClosedStoreOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, storeUsed,getOrderedItems(), getDate(), isOrderStatic());
        getStoreUsed().addClosedOrderToHistory(closedStoreOrder);
        return closedStoreOrder;
    }
}
