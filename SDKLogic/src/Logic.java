import jaxb.schema.generated.*;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.*;

public class Logic {

    private Map<SDMLocation, Store> storesLocationMap;
    private Map<Integer, Store> storesSerialIDMap;
    private Map<Integer, Item> itemsSerialIDMap;
    private Map<Integer, ClosedOrder> ordersSerialIDMap;

    private static Integer currentOrderSerialIDInSDK = 1;

    Logic()
    {
        //TODO
        //Check if it's the right place to allocate the new order
        storesLocationMap = new HashMap<SDMLocation, Store>();
        storesSerialIDMap = new HashMap<Integer, Store>();
        itemsSerialIDMap = new HashMap<Integer, Item>();
        ordersSerialIDMap = new HashMap<Integer, ClosedOrder>();
        currentOrderSerialIDInSDK = 1;
    }

    public static Integer getCurrentOrderSerialIDInSDK() {
        return currentOrderSerialIDInSDK;
    }

    public ClosedOrder getOrderBySerialID(Integer orderSerialID)
    {
        return ordersSerialIDMap.get(orderSerialID);
    }
    public Map<Integer, Store> getStoresSerialIDMap()
    {
        return storesSerialIDMap;
    }
    public Map<Integer, Item> getItemsSerialIDMap()
    {
        return itemsSerialIDMap;
    }

    public Set<Integer> getSetOfStoresSerialID()
    {
        return GeneralMethods.<Integer, Store>getSetOfDictionary(storesSerialIDMap);
    }

    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, Item>getSetOfDictionary(itemsSerialIDMap);

    }

    public Set<Integer> getSetOfOrdersSerialID()
    {
        return GeneralMethods.<Integer, ClosedOrder>getSetOfDictionary(ordersSerialIDMap);
    }

    public Item getItemySerialID(Integer serialID)
    {
        return itemsSerialIDMap.get(serialID);
    }

    public Store getStoreBySerialID(Integer shopID)
    {
        return storesSerialIDMap.get(shopID);
    }

    public Item getItemBySerialID(Integer itemID)
    {
        return itemsSerialIDMap.get(itemID);
    }

    public void addClosedOrderToHistory(ClosedOrder order)
    {
        ordersSerialIDMap.put(currentOrderSerialIDInSDK, order);
        currentOrderSerialIDInSDK++;
        //TODO
        //Check if it's right to create new order after closing the one before
    }

    public int getHowManyShopsSellesAnItem(Integer itemID)
    {
        int howMuchShopsSellsTheItem=0;
        for(Map.Entry<Integer, Store> entry: storesSerialIDMap.entrySet())
        {
            Store store = entry.getValue();
            if(store.checkIfItemIdExists(itemID))
            {
                howMuchShopsSellsTheItem++;
            }
        }
        return howMuchShopsSellsTheItem;
    }

    public int sumAllPricesOfItemInShops(Integer itemID)
    {
        int sumOfAllPricesOfItemInShops=0;
        for(Map.Entry<Integer, Store> entry: storesSerialIDMap.entrySet())
        {
            Store store = entry.getValue();
            if(store.checkIfItemIdExists(itemID))
            {
                sumOfAllPricesOfItemInShops+= store.getItemBySerialID(itemID).getPricePerUnit();
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

    public void setStoresSerialIDMap(Map<Integer, Store> shopsSerialIdMap)
    {
        storesSerialIDMap = shopsSerialIdMap;
    }

    public void setStoresLocationMap(Map<SDMLocation, Store> shopsLocationMap)
    {
        storesLocationMap = shopsLocationMap;
    }
    public void setOfItemsSerialID(Map<Integer, Item> itemsSerialIdMap)
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


    public void createStoresSerialIDMapFromXml(String xmlName) throws duplicateSerialIDException, invalidCoordinateException, JAXBException {
        InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMStores shops = descriptor.getSDMStores();
        List<SDMStore> listOfStores = shops.getSDMStore();

        for(SDMStore store : listOfStores)
        {
            Location location = store.getLocation();
            if(storesSerialIDMap != null && storesSerialIDMap.containsKey(store.getId()))
            {
                throw new duplicateSerialIDException(store.getId(), store.getName());
            }
            else if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getX()) == false)
            {
                throw new invalidCoordinateXException(location.getX(), store.getName());

            }
            else if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getY()) == false)
            {
                throw new invalidCoordinateYException(location.getY(), store.getName());

            }

            else
            {
                //TODO
                //Add that if something go wrong get out and return that something got wrong
                Store storeToAddToMap = new Store(store);
                storesSerialIDMap.put(storeToAddToMap.getSerialNumber(), storeToAddToMap);
            }
        }
    }

    public void createStoresLocationMapFromXml(String xmlName) throws invalidCoordinateException, duplicateLocationException, JAXBException {
        InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        storesLocationMap = new HashMap<SDMLocation, Store>();
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMStores shops = descriptor.getSDMStores();
        List<SDMStore> listOfStores = shops.getSDMStore();

        for(SDMStore store : listOfStores)
        {
            Location location = store.getLocation();
            if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getX()) == false)
            {
                throw new invalidCoordinateXException(location.getX(), store.getName());
            }
            else if(storesLocationMap != null  && storesLocationMap.containsKey(location))
            {
                throw new duplicateLocationException(location.getX(), location.getY(), store.getName());
            }
            else
            {
                //TODO
                //Add that if something go wrong get out and return that something got wrong
                Store storeToAddToMap = new Store(store);
                storesLocationMap.put(new SDMLocation(location.getX(), location.getY()), storeToAddToMap);
            }
        }

    }

    public void createItemsSerialIDMapFromXml(String xmlName) throws duplicateSerialIDException, JAXBException {
        InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMItems items = descriptor.getSDMItems();
        List<SDMItem> listOfItems = items.getSDMItem();

        for(SDMItem item : listOfItems)
        {
            if(itemsSerialIDMap != null  && itemsSerialIDMap.containsKey(item.getId()))
            {
                throw new duplicateSerialIDException(item.getId(), item.getName());
            }
            else
            {
                Item itemToAddToMap = new Item(item);
                itemsSerialIDMap.put(item.getId(), itemToAddToMap);
            }
        }
    }

    public void addItemsToStoresFromXml(String xmlName) throws SerialIDNotExistException, duplicateSerialIDException, JAXBException {
        InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        Map<Integer, Integer> storesSellsIDMap = new HashMap<Integer, Integer>();
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMStores shops = descriptor.getSDMStores();
        List<SDMStore> listOfStores = shops.getSDMStore();

        for (SDMStore store : listOfStores) {
            SDMPrices prices = store.getSDMPrices();
            List<SDMSell> sellsDetails = prices.getSDMSell();
            Map<Integer, Integer> sellsDetailsMap = new HashMap<Integer, Integer>();
            Integer shopSerialID = store.getId();

            for (SDMSell singleSellDetails : sellsDetails) {
                int itemSerialID = singleSellDetails.getItemId();
                int itemPrice = singleSellDetails.getPrice();
                String storeName = store.getName();
                if (itemsSerialIDMap != null && itemsSerialIDMap.containsKey(itemSerialID) == false) {
                    throw new SerialIDNotExistException(itemSerialID, storeName);
                } else if (sellsDetailsMap.containsKey(itemSerialID)) {
                    throw new duplicateSerialIDException(itemSerialID, storeName);
                } else {
                    sellsDetailsMap.put(itemSerialID, itemPrice);
                    Item itemInSDM = itemsSerialIDMap.get(itemSerialID);
                    String itemNameInSDM = itemInSDM.getName();
                    Item.TypeOfMeasure itemMeasureTypeInSDK = itemInSDM.getTypeOfMeasure();

                    SelledItemInStore selledItemInStore = new SelledItemInStore(itemSerialID, itemNameInSDM, itemMeasureTypeInSDK, itemPrice);
                    storesSerialIDMap.get(shopSerialID).addItemToItemSSerialIDMap(selledItemInStore);
                }
            }
        }
    }
}
