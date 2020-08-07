import jaxb.schema.generated.Location;

import java.util.Date;
import java.util.Map;

public class Order {
    private String serialNumber;
    private Date date;
    private Store storeUsed;
    private boolean orderApprovedByCustomer;
    private Map<Integer, OrderedItem> orderedItems;
    private int deliveryPriceAfterOrderIsDone;
    private int totalPriceOfOrderAfterItsDone;
    public Date getDate()
    {
        return date;
    }

    public Store getShop()
    {
        return storeUsed;
    }

    //TODO
    public int calcSumAmountOfItemsType()
    {
        return orderedItems.size();
    }

    //TODO
    public int calcSumOfItems()
    {
        return orderedItems.values().stream().mapToInt(OrderedItem::getAmountOfItemOrdered).sum();
    }

    //TODO
    public double calcDeliveryPrice(SDMLocation inputLocation)
    {
        SDMLocation storeLocation = storeUsed.getLocationOfShop();
        int PPK = storeUsed.getPPK();
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
    public int calcTotalPriceOfItems()
    {
        return orderedItems.values().stream().mapToInt(OrderedItem::getTotalPriceOfItemOrderedByAmount).sum();
    }

    //TODO
    public int calcAmoutOfCertainItem(int serialIdOfItem)
    {
        return orderedItems.get(serialIdOfItem).getAmountOfItemOrdered();
    }

    //TODO
    public int calcSumOfPriceOfCertainItem(int serialIdOfItem)
    {
        return orderedItems.get(serialIdOfItem).getTotalPriceOfItemOrderedByAmount();
    }

    public int getDeliveryPriceAfterOrderIsDone() {
        return deliveryPriceAfterOrderIsDone;
    }

    public double getTotalPriceOfOrderAfterItsDone()
    {
        return totalPriceOfOrderAfterItsDone;
    }
}
