import java.util.Map;

public class OpenedOrder extends Order{

    public OpenedOrder(Store store)
    {
        super(store);
    }

    public double calcDeliveryPrice(SDMLocation inputLocation)
    {
        SDMLocation storeLocation = getStoreUsed().getLocationOfShop();
        int PPK = getStoreUsed().getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }

    //TODO
    public double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcDeliveryPrice(inputLocation);
    }

    //TODO
    public double calcTotalPriceOfItems()
    {
        return getOrderedItems().values().stream().mapToDouble(OrderedItem::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getAmountOfItemOrderedByUnits).sum();
    }

    public OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }

    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem)
    {
        Map<Integer, OrderedItem> orderedItems = getOrderedItems();
        boolean itemAlreadyExistsInOrder=false;
        if(orderedItems != null)
        {
            itemAlreadyExistsInOrder = getOrderedItems().containsKey(serialIDOfItem);
        }
        return itemAlreadyExistsInOrder;
    }

    public ClosedOrder closeOrder(SDMLocation location)
    {
        double totalPriceOfItems = calcTotalPriceOfItems();
        double deliveryPriceAfterOrderIsDone = calcDeliveryPrice(location);
        double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        int totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        int totalAmountOfItemsType = calcTotalAmountOfItemsType();
        return new ClosedOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, getStoreUsed());
    }


    public int calcTotalAmountOfItemsType()
    {
        return getOrderedItems().size();
    }
}
