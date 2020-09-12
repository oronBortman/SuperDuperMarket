package logic.order.StoreOrder;

import logic.SDMLocation;
import logic.Store;
import logic.order.OpenedOrder;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.util.Date;

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
    public Double calcTotalDeliveryPrice(SDMLocation inputLocation) {
        SDMLocation storeLocation = storeUsed.getLocationOfShop();
        int PPK = storeUsed.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }

    public void addItemToItemsMapOfOrder(OrderedItemFromStore orderedItemFromStore)
    {
        getOrderedItems().put(orderedItemFromStore.getSerialNumber(), orderedItemFromStore);
    }

    /*public Double calcDeliverPriceFromStore(SDMLocation inputLocation, Store store) {
        SDMLocation storeLocation = store.getLocationOfShop();
        int PPK = store.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }*/


    @Override
    public Double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcTotalDeliveryPrice(inputLocation);
    }

    @Override
    public Double calcTotalPriceOfItems()
    {
        return getOrderedItems().values().stream().mapToDouble(OrderedItemFromStore::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    @Override
    public Integer calcTotalAmountOfItemsByUnit()
    {
        return new Integer(getOrderedItems().values().stream().mapToInt(OrderedItemFromStore::getAmountOfItemOrderedByUnits).sum());
    }

    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/



    public Integer calcTotalAmountOfItemsType()
    {
        return getOrderedItems().size();
    }


    public ClosedStoreOrder closeOrder(SDMLocation location)
    {
        Double totalPriceOfItems = calcTotalPriceOfItems();
        Double deliveryPriceAfterOrderIsDone = calcTotalDeliveryPrice(location);
        Double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        Integer totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        Integer totalAmountOfItemsType = calcTotalAmountOfItemsType();
        ClosedStoreOrder closedStoreOrder = new ClosedStoreOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, storeUsed,getOrderedItems(), getDate(), isOrderStatic());
        getStoreUsed().addClosedOrderToHistory(closedStoreOrder);
        return closedStoreOrder;
    }
}
