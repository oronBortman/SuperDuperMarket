import javax.xml.stream.Location;
import java.util.*;

public class SDKBase {

    private Map<SDKLocation, Shop> storesLocationMap;
    private Map<Integer, Shop> storesSerialIDMap;
    private Map<Integer, SDKItem> itemsSerialIDMap;
    private Map<Integer, Order> ordersSerialIDMap;

    private static Integer currentOrderSerialIDInSDK;

    SDKBase()
    {
        //TODO
        //Check if it's the right place to allocate the new order
        storesLocationMap = new HashMap<SDKLocation, Shop>();
        Map<Integer, Shop> storesSerialIDMap;
        Map<Integer, SDKItem> itemsSerialIDMap;
        Map<Integer, Order> ordersSerialIDMap = new HashMap<Integer, Order>();
        currentOrderSerialIDInSDK = 1;
    }

    public Order getOrderBySerialID(Integer orderSerialID)
    {
        return ordersSerialIDMap.get(orderSerialID);
    }

    public Set<Integer> getSetOfStoresSerialID()
    {
        return GeneralMethods.<Integer, Shop>getSetOfDictionary(storesSerialIDMap);
    }

    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, SDKItem>getSetOfDictionary(itemsSerialIDMap);

    }

    public Set<Integer> getSetOfOrdersSerialID()
    {
        return GeneralMethods.<Integer, Order>getSetOfDictionary(ordersSerialIDMap);
    }

    public SDKItem getItemySerialID(Integer serialID)
    {
        return itemsSerialIDMap.get(serialID);
    }

    public Shop getStoreBySerialID(Integer shopID)
    {
        return storesSerialIDMap.get(shopID);
    }

    public SDKItem getItemBySerialID(Integer itemID)
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
    public int getHowManyShopsSellesAnItem(Integer itemID)
    {
        return 1;
    }
    //TODO
    public int getAvgPriceOfItemInSDK(Integer itemID)
    {
        return 1;
    }

    //TODO
    public int getHowManyTimesTheItemSoled(Integer itemID)
    {
        return 1;
    }

    public void setStoresSerialIDMap(Map<Integer, Shop> shopsSerialIdMap)
    {
        storesSerialIDMap = shopsSerialIdMap;
    }

    public void setStoresLocationMap(Map<SDKLocation, Shop> shopsLocationMap)
    {
        storesLocationMap = shopsLocationMap;
    }
    public void setOfItemsSerialID(Map<Integer, SDKItem> itemsSerialIdMap)
    {
        itemsSerialIDMap = itemsSerialIdMap;
    }

    public boolean setItemsSerialIDMapParameterOfMethodFromList(List<SDKItem> listOfItems, Map<Integer, SDKItem> itemsSerialIDMap)
    {
        boolean duplicateSerialIDOfItem=false;
        for(SDKItem item : listOfItems)
        {
            if(itemsSerialIDMap.containsKey(item.getSerialNumber()))
            {
                duplicateSerialIDOfItem=true;
            }
            //Check if id already exists
            itemsSerialIDMap.put(item.getSerialNumber(), item);
        }
        return duplicateSerialIDOfItem;
    }

    public boolean setStoresSerialIDMapParameterOfMethodFromList(List<Shop> listOfShops, Map<Integer, Shop> storesSerialIDMap)
    {
        boolean duplicateSerialIDOfStore=false;
        for(Shop shop : listOfShops)
        {
            if(storesSerialIDMap.containsKey(shop.getSerialNumber()))
            {
                duplicateSerialIDOfStore=true;
            }
            //Check if id already exists
            storesSerialIDMap.put(shop.getSerialNumber(), shop);
        }
        return duplicateSerialIDOfStore;
    }

    public boolean setStoresLocationMapParameterOfMethodFromList(List<Shop> listOfShops, Map<SDKLocation, Shop> storesLocationMap)
    {
        boolean duplicateLocationOfStore=false;
        for(Shop shop : listOfShops)
        {
            if(storesLocationMap.containsKey(shop.getLocationOfShop()))
            {
                duplicateLocationOfStore=true;
            }
            //Check if id already exists
            storesLocationMap.put(shop.getLocationOfShop(), shop);
        }
        return duplicateLocationOfStore;
    }

}
