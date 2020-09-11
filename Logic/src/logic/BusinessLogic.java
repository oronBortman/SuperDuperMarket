package logic;

import exceptions.*;
import jaxb.schema.generated.*;
import logic.order.ClosedOrder;
import logic.order.CustomerOrder.ClosedCustomerOrder;
import logic.order.GeneralMethods;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.itemInOrder.OrderedItem;
import logic.discount.Discount;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class BusinessLogic {

    private Map<SDMLocation, Store> storesLocationMap;
    private Map<Integer, Store> storesSerialIDMap;
    private Map<Integer, Item> itemsSerialIDMap;
    private Map<Integer, ClosedCustomerOrder> ordersSerialIDMap;
    private Map<Integer, Customer> usersSerialIDMap;
    private List<Discount> discounts;
    private static Integer currentOrderSerialIDInSDK = 1;

    public BusinessLogic()
    {
        //TODO
        //Check if it's the right place to allocate the new order
        storesLocationMap = new HashMap<SDMLocation, Store>();
        storesSerialIDMap = new HashMap<Integer, Store>();
        itemsSerialIDMap = new HashMap<Integer, Item>();
        ordersSerialIDMap = new HashMap<Integer, ClosedCustomerOrder>();
        usersSerialIDMap = new HashMap<Integer, Customer>();
        //TODO
        //Need to delete this lines after reading users from xml
        usersSerialIDMap.put(123, new Customer(123, "Oron", new SDMLocation(1,2)));

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

    public List<Item> getItemsList()
    {
        return new ArrayList<Item>(itemsSerialIDMap.values());
    }
    public List<Customer> getUsersList()
    {
        return new ArrayList<Customer>(usersSerialIDMap.values());
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
        return GeneralMethods.<Integer, ClosedCustomerOrder>getSetOfDictionary(ordersSerialIDMap);
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

    public void addClosedOrderToHistory(ClosedCustomerOrder order)
    {
        for(ClosedStoreOrder closedStoreOrder : order.getClosedStoresOrderMapByStoreSerialID().values())
        {
            closedStoreOrder.setSerialNumber(currentOrderSerialIDInSDK);
        }
        ordersSerialIDMap.put(currentOrderSerialIDInSDK, order);
        currentOrderSerialIDInSDK++;
        //TODO
        //Check if it's right to create new order after closing the one before
    }

    public Integer getHowManyShopsSellesAnItem(Integer itemID)
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

    public Integer sumAllPricesOfItemInShops(Integer itemID)
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

    public Integer getAvgPriceOfItemInSDK(Integer itemID)
    {
        int sumOfAllPricesOfItemInShops = sumAllPricesOfItemInShops(itemID);
        int howMuchShopsSellsTheItem = getHowManyShopsSellesAnItem(itemID);
        int aveargePriceOfItemInSDK;

        if(howMuchShopsSellsTheItem != 0)
        {
            aveargePriceOfItemInSDK = sumOfAllPricesOfItemInShops /  howMuchShopsSellsTheItem;
        }
        else
        {
            aveargePriceOfItemInSDK=0;
        }
        return aveargePriceOfItemInSDK;

    }
    final int MIN_PRICE_INITIALIZE = -1;



    //TODO
    public Double getTotalAmountOfSoledItem(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().filter(closedOrder -> closedOrder.checkIfItemAlreadyExistsInOrder(itemID)).mapToDouble(x -> x.getTotalAmountOfItemTypesOfStoreOrderBySerialIDOfItem(itemID)).sum();
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

    public void addItemsSerialIDMapFromXml(SDMItem item) throws DuplicateItemSerialIDException, JAXBException, FileNotFoundException {

        if(itemsSerialIDMap != null  && itemsSerialIDMap.containsKey(item.getId()))
        {
            throw new DuplicateItemSerialIDException(item.getId(), item.getName());
        }
        else
        {
            Item itemToAddToMap = new Item(item);
            itemsSerialIDMap.put(item.getId(), itemToAddToMap);
        }

    }

    public void addUserSerialIDMapFromXml(SDMCustomer user) throws DuplicateCustomerSerialIDException, JAXBException, FileNotFoundException {

        if(usersSerialIDMap != null  && usersSerialIDMap.containsKey(user.getId()))
        {
            throw new DuplicateCustomerSerialIDException(user.getId(), user.getName());
        }
        else
        {
            Customer userToAddToMap = new Customer(user);
            usersSerialIDMap.put(user.getId(), userToAddToMap);
        }
    }


    public void addStoreSerialIDMapFromXml(SDMStore store) throws JAXBException, FileNotFoundException, DuplicateStoreSerialIDException {

        if(storesSerialIDMap != null  && storesSerialIDMap.containsKey(store.getId()))
        {
            throw new DuplicateStoreSerialIDException(store.getId(), store.getName());
        }
        else
        {
            Store storeToAddToMap = new Store(store);
            storesSerialIDMap.put(store.getId(), storeToAddToMap);
        }
    }


    public void checkIfThereIsItemNotInStore() throws ItemNotExistInStoresException {
        for(Map.Entry<Integer, Item> item : itemsSerialIDMap.entrySet())
        {
            if(checkIfItemExistsInStores(item.getKey()) == false)
            {
                throw new ItemNotExistInStoresException(item.getValue());
            }
        }
    }

    public void addItemToStoreFromSDMSell(SDMSell sdmSell, int storeID) throws ItemWithSerialIDNotExistInSDMException, DuplicateItemSerialIDInStoreException, StoreNotExistException
    {
        int itemID = sdmSell.getItemId();
        Store store = getStoreBySerialID(storeID);

        if(checkIfItemIdExists(itemID) == false)
        {
            throw new ItemWithSerialIDNotExistInSDMException(itemID);
        }
        else if(store == null)
        {
            throw new StoreNotExistException(storeID);
        }
        else if(checkIfItemExistsInStore(storeID, itemID))
        {
            String itemName = getItemBySerialID(itemID).getName();
            throw new DuplicateItemSerialIDInStoreException(storeID, store.getName(), itemID, itemName );
        }
        else
        {
            Item item = getItemBySerialID(itemID);
            int priceOfItem = sdmSell.getPrice();
            addItemToStore(storeID, itemID, priceOfItem);
        }
    }

    public void addDiscountToStoreFromSDMSell(SDMDiscount sdmDiscount, int storeID) throws ItemWithSerialIDNotExistInSDMException, DuplicateItemSerialIDInStoreException, StoreNotExistException, ItemNotExistInStoresException, DuplicateDiscountNameException, ItemIDNotExistInAStoreException {
        Store store = getStoreBySerialID(storeID);
        store.addDiscountToStoreFromXML(sdmDiscount);
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


    public int getIDOfShopWithCheapestItem(int itemSerialID)
    {
        final int MIN_PRICE_INITIALIZE = -1;
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


}

