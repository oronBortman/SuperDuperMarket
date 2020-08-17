import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenedDynamicOrder extends OpenedOrder{

    HashMap<Store, List<OrderedItem>> mapOfStoresIDWithSelectedItems;

    public OpenedDynamicOrder(Date date)
    {
        super(date);
    }
    @Override
    //TODO
    public double calcTotalDeliveryPrice(SDMLocation inputLocation)
    {
        int deliveryPrice=0;
        for(Store store : mapOfStoresIDWithSelectedItems.keySet())
        {
            deliveryPrice+=calcDeliverPriceFromStore(inputLocation,store);
        }
        return deliveryPrice;
    }

    public void setMapOfStoresIDWithSelectedItems(HashMap<Store, List<OrderedItem>> mapOfStoresIDWithSelectedItems) {
        this.mapOfStoresIDWithSelectedItems = mapOfStoresIDWithSelectedItems;
    }

    public ClosedDynamicOrder closeOrder(SDMLocation location)
    {
        double totalPriceOfItems = calcTotalPriceOfItems();
        double deliveryPriceAfterOrderIsDone = calcTotalDeliveryPrice(location);
        double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        int totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        int totalAmountOfItemsType = calcTotalAmountOfItemsType();
        return new ClosedDynamicOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, getOrderedItems(), getDate());
    }

    public void updateItemsByCheapest()
    {
        for(Map.Entry<Integer, OrderedItem> entryOrder : getOrderedItems().entrySet())
        {
            OrderedItem orderedItem = entryOrder.getValue();
            for(List<OrderedItem> listOfItems : mapOfStoresIDWithSelectedItems.values())
            {
                for(OrderedItem orderedItemInList : listOfItems)
                {
                    if(orderedItem == orderedItemInList)
                    {
                        orderedItem.setPricePerUnit(orderedItemInList.getPricePerUnit());
                    }
                }
            }
        }

    }

}
