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
        Map<Integer, Shop> storesSerialIDMap = new HashMap<Integer, Shop>();
        Map<Integer, SDKItem> itemsSerialIDMap = new HashMap<Integer, SDKItem>();
        Map<Integer, Order> ordersSerialIDMap = new HashMap<Integer, Order>();
        currentOrderSerialIDInSDK = 1;
    }

    public Order getOrderBySerialID(Integer orderSerialID)
    {
        return ordersSerialIDMap.get(orderSerialID);
    }
    public Map<Integer, Shop> getStoresSerialIDMap()
    {
        return storesSerialIDMap;
    }
    public Map<Integer, SDKItem> getItemsSerialIDMap()
    {
        return itemsSerialIDMap;
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

    public int getHowManyShopsSellesAnItem(Integer itemID)
    {
        int howMuchShopsSellsTheItem=0;
        for(Map.Entry<Integer, Shop> entry: storesSerialIDMap.entrySet())
        {
            Shop shop = entry.getValue();
            if(shop.checkIfItemIdExists(itemID))
            {
                howMuchShopsSellsTheItem++;
            }
        }
        return howMuchShopsSellsTheItem;
    }

    public int sumAllPricesOfItemInShops(Integer itemID)
    {
        int sumOfAllPricesOfItemInShops=0;
        for(Map.Entry<Integer, Shop> entry: storesSerialIDMap.entrySet())
        {
            Shop shop = entry.getValue();
            if(shop.checkIfItemIdExists(itemID))
            {
                sumOfAllPricesOfItemInShops+=shop.getItemySerialID(itemID).getPricePerUnit();
            }
        }
        return sumOfAllPricesOfItemInShops;
    }

    public int getAvgPriceOfItemInSDK(Integer itemID)
    {
        int sumOfAllPricesOfItemInShops = sumAllPricesOfItemInShops(itemID);
        int howMuchShopsSellsTheItem = getHowManyShopsSellesAnItem(itemID);
        int aveargePriceOfItemInSDK = sumOfAllPricesOfItemInShops /  howMuchShopsSellsTheItem;
        return aveargePriceOfItemInSDK;

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

    public boolean checkIfItemIdExists(int itemSerialID)
    {
        return itemsSerialIDMap.containsKey(itemSerialID);
    }
    public boolean checkIfStoreExists(int storeSerialID)
    {
        return storesSerialIDMap.containsKey(storeSerialID);
    }


}
