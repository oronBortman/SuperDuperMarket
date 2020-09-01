package logic;

import exceptions.*;
import jaxb.schema.generated.*;
import logic.*;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Base {

    private Map<SDMLocation, Store> storesLocationMap;
    private Map<Integer, Store> storesSerialIDMap;
    private Map<Integer, Item> itemsSerialIDMap;
    private Map<Integer, ClosedOrder> ordersSerialIDMap;

    private static Integer currentOrderSerialIDInSDK = 1;

    public Base()
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
    public void deleteDetailsOnSuperDuperMarket()
    {
        storesLocationMap.clear();
        storesSerialIDMap.clear();
        itemsSerialIDMap.clear();
        ordersSerialIDMap.clear();
        currentOrderSerialIDInSDK = 1;
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

    public List<Item> getItemsList()
    {
        return new ArrayList<Item>(itemsSerialIDMap.values());
    }


    public List<Store> getStoresList()
    {
        return new ArrayList<Store>(storesSerialIDMap.values());
    }
    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, Item>getSetOfDictionary(itemsSerialIDMap);
    }

    public boolean checkIfLocationIsAsStores(SDMLocation location)
    {
        Set<SDMLocation> setOfLocations = storesLocationMap.keySet();
        return location.checkIfCoordinatesMatchToListOfLocations(setOfLocations);
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
    final int MIN_PRICE_INITIALIZE = -1;

    public int getIDOfShopWithCheapestItem(int itemSerialID)
    {
        int minPrice=MIN_PRICE_INITIALIZE;
        int storeSerialIDWithCheapestItem=0;
        for (Map.Entry<Integer, Store> entry : storesSerialIDMap.entrySet())
        {
            int storeSerialId = entry.getKey();
            Store store = entry.getValue();
            AvailableItemInStore item = store.getItemBySerialID(itemSerialID);
            if(item != null)
            {
                int itemPriceInStore = item.getPricePerUnit();
                if(minPrice == MIN_PRICE_INITIALIZE || itemPriceInStore < minPrice)
                {
                    minPrice=itemPriceInStore;
                    storeSerialIDWithCheapestItem = storeSerialId;
                }
            }
        }
        return storeSerialIDWithCheapestItem;
    }

    public HashMap<Store, List<OrderedItem>> getMapOfShopWithCheapestItemsFromSet(Set<OrderedItem> listOfOrderedItems)
    {
        Store store;
        int storeID;
        HashMap<Store, List<OrderedItem>> mapOfShopsWithCheapestItems = new HashMap<Store, List<OrderedItem>>() ;
        for(OrderedItem orderedItem: listOfOrderedItems)
        {
            storeID = getIDOfShopWithCheapestItem(orderedItem.getSerialNumber());
            store = storesSerialIDMap.get(storeID);
            AvailableItemInStore item = store.getItemBySerialID(orderedItem.getSerialNumber());
            if(item != null)
            {
                int pricePerUnit =  store.getItemBySerialID(orderedItem.getSerialNumber()).getPricePerUnit();
                orderedItem.setPricePerUnit(pricePerUnit);
                if(mapOfShopsWithCheapestItems.get(storeID) == null)
                {
                    mapOfShopsWithCheapestItems.put(store, new ArrayList<OrderedItem>());
                }
                mapOfShopsWithCheapestItems.get(store).add(orderedItem);
            }
        }
        return mapOfShopsWithCheapestItems;
    }

    //TODO
    public double getTotalAmountOfSoledItem(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().filter(closedOrder -> closedOrder.checkIfItemExistsInOrder(itemID)).mapToDouble(x -> x.getAmountOfCertainItemByTypeOfMeasure(itemID)).sum();
    }
//        return ordersSerialIDMap.values().stream().filter(closedOrder -> closedOrder.getTotalAmountOfSoledItem(itemID).sum();
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


    public void createStoresSerialIDMapFromXml(String xmlName) throws DuplicateSerialIDException, InvalidCoordinateException, JAXBException, FileNotFoundException {
        //InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        InputStream inputStream = new FileInputStream(xmlName);
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMStores shops = descriptor.getSDMStores();
        List<SDMStore> listOfStores = shops.getSDMStore();

        for(SDMStore store : listOfStores)
        {
            Location location = store.getLocation();
            if(storesSerialIDMap != null && storesSerialIDMap.containsKey(store.getId()))
            {
                throw new DuplicateSerialIDException(store.getId(), store.getName());
            }
            else if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getX()) == false)
            {
                throw new InvalidCoordinateXException(location.getX(), store.getName());

            }
            else if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getY()) == false)
            {
                throw new InvalidCoordinateYException(location.getY(), store.getName());

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

    public void createStoresLocationMapFromXml(String xmlName) throws InvalidCoordinateException, DuplicateLocationException, JAXBException, FileNotFoundException {
        //InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        InputStream inputStream = new FileInputStream(xmlName);
        storesLocationMap = new HashMap<SDMLocation, Store>();
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMStores shops = descriptor.getSDMStores();
        List<SDMStore> listOfStores = shops.getSDMStore();

        for(SDMStore store : listOfStores)
        {
            Location location = store.getLocation();
            if(SDMLocation.checkIfLocationCoordinatesIsValid(location.getX()) == false)
            {
                throw new InvalidCoordinateXException(location.getX(), store.getName());
            }
            else if(storesLocationMap != null  && storesLocationMap.containsKey(location))
            {
                throw new DuplicateLocationException(location.getX(), location.getY(), store.getName());
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

    public void createItemsSerialIDMapFromXml(String xmlName) throws DuplicateSerialIDException, JAXBException, FileNotFoundException {
        //InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        InputStream inputStream = new FileInputStream(xmlName);
        SuperDuperMarketDescriptor descriptor = GeneralMethods.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
        SDMItems items = descriptor.getSDMItems();
        List<SDMItem> listOfItems = items.getSDMItem();

        for(SDMItem item : listOfItems)
        {
            if(itemsSerialIDMap != null  && itemsSerialIDMap.containsKey(item.getId()))
            {
                throw new DuplicateSerialIDException(item.getId(), item.getName());
            }
            else
            {
                Item itemToAddToMap = new Item(item);
                itemsSerialIDMap.put(item.getId(), itemToAddToMap);
            }
        }
    }

    public void addItemsToStoresFromXml(String xmlName) throws SerialIDNotExistException, DuplicateSerialIDException, JAXBException, FileNotFoundException, ItemNotExistInStoresException {
        //InputStream inputStream = Logic.class.getResourceAsStream(xmlName);
        InputStream inputStream = new FileInputStream(xmlName);
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
                    throw new DuplicateSerialIDException(itemSerialID, storeName);
                } else {
                    sellsDetailsMap.put(itemSerialID, itemPrice);
                    Item itemInSDM = itemsSerialIDMap.get(itemSerialID);
                    String itemNameInSDM = itemInSDM.getName();
                    Item.TypeOfMeasure itemMeasureTypeInSDK = itemInSDM.getTypeOfMeasure();

                    AvailableItemInStore availableItemInStore = new AvailableItemInStore(itemSerialID, itemNameInSDM, itemMeasureTypeInSDK, itemPrice);
                    storesSerialIDMap.get(shopSerialID).addItemToItemSSerialIDMap(availableItemInStore);
                }
            }

        }
        for(Map.Entry<Integer, Item> item : itemsSerialIDMap.entrySet())
        {
            if(checkIfItemExistsInStores(item.getKey()) == false)
            {
                throw new ItemNotExistInStoresException(item.getValue());
            }
        }
    }

    public boolean checkIfItemExistsInStores(int itemSerialID)
    {
        boolean itemIsBeingSelled=false;
        for(Store store : storesSerialIDMap.values())
        {
            if(store.checkIfItemIdExists(itemSerialID) == true)
            {
                itemIsBeingSelled=true;
            }
        }
        return itemIsBeingSelled;
    }

    public void addItemToStore(int storeID, int itemID, int priceOfItem)
    {
        storesSerialIDMap.get(storeID).addItemToStore(itemsSerialIDMap.get(itemID), priceOfItem);
    }

    public void removeItemFromStore(int storeID, int itemID)
    {
        storesSerialIDMap.get(storeID).removeItemFromStore(itemID);
    }


    public void updatePriceOfItemInStore(int storeID, int itemID, int priceOfItem)
    {
        storesSerialIDMap.get(storeID).updatePriceOfItem(itemID,priceOfItem);
    }

    public boolean checkIfOnlyCertainStoreSellesItem(int itemID, int storeID)
    {
        boolean onlyInputStoreSellesItem=true;
        for(Map.Entry<Integer, Store> entry : storesSerialIDMap.entrySet())
        {
            if( entry.getKey() != storeID && entry.getValue().checkIfItemIdExists(itemID))
            {
                onlyInputStoreSellesItem=false;
            }
        }
        return onlyInputStoreSellesItem;
    }

    public boolean checkIfItemExistsInStore(int storeID, int itemID)
    {
        return storesSerialIDMap.get(storeID).checkIfItemIdExists(itemID);
    }
}

