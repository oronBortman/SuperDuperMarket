import java.util.Date;
import java.util.Map;

public abstract class OpenedOrder extends Order{

    public OpenedOrder(Date date)
    {
        super(date);
    }

    //TODO
    public abstract double calcTotalDeliveryPrice(SDMLocation inputLocation);

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
}
