package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import jaxb.schema.generated.SDMStore;

import java.util.*;

public class Store {
    private SimpleIntegerProperty serialNumber;
    private SimpleStringProperty name;
    private Map<Integer, AvailableItemInStore> ItemsSerialIDMap;
    private Map<Integer, ClosedOrder> ordersSerialIDMap;
    private SimpleIntegerProperty PPK;
    private SDMLocation SDMLocationOfShop;

    public Store(Integer serialNumber, String name, int PPK, SDMLocation SDMLocationOfShop)
    {
        ItemsSerialIDMap = new HashMap<Integer, AvailableItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedOrder>();
        this.serialNumber = new SimpleIntegerProperty(serialNumber);
        this.name = new SimpleStringProperty(name);
        this.PPK = new SimpleIntegerProperty(PPK);
        this.SDMLocationOfShop = SDMLocationOfShop;
    }

    public Store(SDMStore shop)
    {
        ItemsSerialIDMap = new HashMap<Integer, AvailableItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedOrder>();
        this.serialNumber = new SimpleIntegerProperty(shop.getId());
        this.name = new SimpleStringProperty(shop.getName());
        this.PPK = new SimpleIntegerProperty(shop.getDeliveryPpk());
        SDMLocation location = new SDMLocation(shop.getLocation());
        this.SDMLocationOfShop = location;
    }

    public List<Item> getItemsList()
    {
        return new ArrayList<Item>(ItemsSerialIDMap.values());
    }

    private void addItemToShop(AvailableItemInStore availableItemInStore)
    {
        ItemsSerialIDMap.put(availableItemInStore.getSerialNumber(), availableItemInStore);
    }

    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, AvailableItemInStore>getSetOfDictionary(ItemsSerialIDMap);

    }

    public Set<Integer> getSetOfOrdersSerialID()
    {
        return GeneralMethods.<Integer, ClosedOrder>getSetOfDictionary(ordersSerialIDMap);

    }

    public boolean checkIfItemIdIsUnique(String serialNumber)
    {
        return ItemsSerialIDMap.containsKey(serialNumber) ;
    }

    //TODO
    public double calcProfitOfDelivers()
    {
        return ordersSerialIDMap.values().stream().mapToDouble(ClosedOrder::getDeliveryPriceAfterOrder).sum();
    }

    public String getName()
    {
        return name.getValue();
    }

    public AvailableItemInStore getItemBySerialID(Integer serialID)
    {
        return ItemsSerialIDMap.get(serialID);
    }

    public ClosedOrder getOrderBySerialID(Integer serialID)
    {
        return ordersSerialIDMap.get(serialID);
    }


    public Integer getSerialNumber() {
        return this.serialNumber.getValue();
    }

    public Integer getPPK() {
        return this.PPK.getValue();
    }

    public SDMLocation getLocationOfShop()
    {
        return SDMLocationOfShop;
    }

    public boolean checkIfItemIdExists(int itemSerialNumber)
    {
        return ItemsSerialIDMap.containsKey(itemSerialNumber);
    }

    public void addItemToItemSSerialIDMap(AvailableItemInStore item)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), item);
    }

    public void addClosedOrderToHistory(ClosedOrder order)
    {
        ordersSerialIDMap.put(order.getSerialNumber(), order);
    }

    public int getAmountOfOrder()
    {
        return ordersSerialIDMap.size();
    }
    public Map<Integer, AvailableItemInStore> getStoresSerialIDMap()
    {
        return ItemsSerialIDMap;
    }
    public int getAmountOfItemSoledByUnit(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().mapToInt(closedOrder -> closedOrder.getAmountOfCertainItemByUnit(itemID)).sum();
    }
    public double getAmountOfItemSoledByTypeOfMeasure(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().mapToDouble(closedOrder -> closedOrder.getAmountOfCertainItemByTypeOfMeasure(itemID)).sum();
    }

    public boolean checkIfItemIsTheOnlyOneInStore(Integer itemID)
    {
        return ItemsSerialIDMap.size() == 1 && checkIfItemIdExists(itemID);
    }
    public void removeItemFromStore(int itemID)
    {
        ItemsSerialIDMap.remove(itemID);
    }
    public void addItemToStore(Item item, int priceOfItem)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), new AvailableItemInStore(item, priceOfItem));
    }
    public void updatePriceOfItem(int itemID, int priceOfItem)
    {
        ItemsSerialIDMap.get(itemID).setPricePerUnit(priceOfItem);
    }
}

