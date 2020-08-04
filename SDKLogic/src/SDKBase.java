import java.util.*;

public class SDKBase {

    private Map<Location, Shop> storesLocationMap;
    private Map<String, Shop> storesSerialIDMap;
    private Map<String, SDKItem> itemsSerialIDMap;
    private Map<Integer, Order> ordersSerialIDMap;

    private static Integer currentOrderSerialIDInSDK;

    SDKBase()
    {
        //TODO
        //Check if it's the right place to allocate the new order
        storesLocationMap = new HashMap<Location, Shop>();
        Map<String, Shop> storesSerialIDMap = new HashMap<String, Shop>();
        Map<String, SDKItem> itemsSerialIDMap = new HashMap<String, SDKItem>();
        Map<Integer, Order> ordersSerialIDMap = new HashMap<Integer, Order>();
        currentOrderSerialIDInSDK = 1;
    }

    public Order getOrderBySerialID(Integer orderSerialID)
    {
        return ordersSerialIDMap.get(orderSerialID);
    }

    public Set<String> getSetOfStoresSerialID()
    {
        return GeneralMethods.<String, Shop>getSetOfDictionary(storesSerialIDMap);
    }

    public Set<String> getSetOfItemsSerialID()
    {
        return GeneralMethods.<String, SDKItem>getSetOfDictionary(itemsSerialIDMap);

    }

    public Set<Integer> getSetOfOrdersSerialID()
    {
        return GeneralMethods.<Integer, Order>getSetOfDictionary(ordersSerialIDMap);
    }

    public SDKItem getItemySerialID(String serialID)
    {
        return itemsSerialIDMap.get(serialID);
    }

    public Shop getStoreBySerialID(String shopID)
    {
        return storesSerialIDMap.get(shopID);
    }

    public SDKItem getItemBySerialID(String itemID)
    {
        return itemsSerialIDMap.get(itemID);
    }

    public void closeOrderAndAddItToHistory(Order order)
    {
        ordersSerialIDMap.put(currentOrderSerialIDInSDK, order);
        currentOrderSerialIDInSDK++;
        //TODO
        //Check if it's right to create new order after closing the one before
    }

    //TODO
    public int getHowManyShopsSellesAnItem(String itemID)
    {
        return 1;
    }
    //TODO
    public int getAvgPriceOfItemInSDK(String itemID)
    {
        return 1;
    }

    //TODO
    public int getHowManyTimesTheItemSoled(String itemID)
    {
        return 1;
    }

}
