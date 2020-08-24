import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class OpenedStoreOrder extends Order {

    Store storeUsed;

    public OpenedStoreOrder(Store store, Date date)
    {
        super(date);
        this.storeUsed = store;
    }

    public double calcDeliverPriceFromStore(SDMLocation inputLocation, Store store) {
        SDMLocation storeLocation = store.getLocationOfShop();
        int PPK = store.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }

    public double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcTotalDeliveryPrice(inputLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenedStoreOrder that = (OpenedStoreOrder) o;
        return Objects.equals(storeUsed, that.storeUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeUsed);
    }

    public double calcTotalPriceOfItems()
    {
        return getOrderedItems().values().stream().mapToDouble(OrderedItem::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getAmountOfItemOrderedByUnits).sum();
    }

    /*public OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/

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
        return new ClosedStoreOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, storeUsed,getOrderedItems(), getDate());
    }

    public abstract double calcTotalDeliveryPrice(SDMLocation inputLocation);

    public double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcTotalDeliveryPrice(inputLocation);
    }

    public double calcTotalPriceOfItems()
    {
        return getOrderedItems().values().stream().mapToDouble(OrderedItem::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getAmountOfItemOrderedByUnits).sum();
    }



    public int calcTotalAmountOfItemsType()
    {
        return getOrderedItems().size();
    }

}
