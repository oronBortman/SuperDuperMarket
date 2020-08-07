import java.util.Date;
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
        double distanceBetweenTwoLocations = distanceBetweenTwoLocations(inputLocation, storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }

    public double distanceBetweenTwoLocations(SDMLocation firstLocation, SDMLocation secondLocation)
    {
        int pow = 2;
        int differenceBetweenXCoordinates = firstLocation.differenceBetweenXCoordinates(secondLocation.getX());
        double powOfDifferenceBetweenXCoordinates = Math.pow(differenceBetweenXCoordinates, pow);
        int differenceBetweenYCoordinates = firstLocation.differenceBetweenYCoordinates(secondLocation.getY());
        double powOfDifferenceBetweenYCoordinates = Math.pow(differenceBetweenYCoordinates, pow);
        double sumOfPowOfCooridnateDifferences = differenceBetweenXCoordinates + differenceBetweenYCoordinates;
        return(Math.sqrt(sumOfPowOfCooridnateDifferences));
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

    //TODO
    public int calcTotalAmountOfCertainItemByUnit(int serialIdOfItem)
    {
        return getOrderedItems().get(serialIdOfItem).getTotalPriceOfItemOrderedByUnits();
    }

    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getTotalPriceOfItemOrderedByUnits).sum();
    }

    //TODO
    public double calcTotalPriceOfCertainItem(int serialIdOfItem)
    {
        return getOrderedItems().get(serialIdOfItem).getTotalPriceOfItemOrderedByTypeOfMeasure();
    }

    public OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }

    public void addItemToItemsMapOfOrder(OrderedItem orderedItem)
    {
        getOrderedItems().put(orderedItem.getSerialNumber(), orderedItem);
    }
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem)
    {
        return getOrderedItems().containsKey(serialIDOfItem);
    }

    public ClosedOrder closeOrder(SDMLocation location)
    {
        double deliveryPriceAfterOrderIsDone = calcDeliveryPrice(location);
        double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        int totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        int totalAmountOfItemsType = calcTotalAmountOfItemsType();
        return new ClosedOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, getStoreUsed());
    }


}
