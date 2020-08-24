import jaxb.schema.generated.SDMStore;

import java.util.*;

public class Store {
    private Integer serialNumber;
    private String name;
    private Map<Integer, SelledItemInStore> ItemsSerialIDMap;
    private Map<Integer, ClosedOrder> ordersSerialIDMap;
    private int PPK;
    private SDMLocation SDMLocationOfShop;

    Store(Integer serialNumber, String name, int PPK, SDMLocation SDMLocationOfShop)
    {
        ItemsSerialIDMap = new HashMap<Integer, SelledItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedOrder>();
        this.serialNumber = serialNumber;
        this.name = name;
        this.PPK = PPK;
        this.SDMLocationOfShop = SDMLocationOfShop;
    }

    public Store(SDMStore shop)
    {
        ItemsSerialIDMap = new HashMap<Integer, SelledItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedOrder>();
        this.serialNumber = shop.getId();
        this.name = shop.getName();
        this.PPK = shop.getDeliveryPpk();
        SDMLocation location = new SDMLocation(shop.getLocation());
        this.SDMLocationOfShop = location;
    }

    private void addItemToShop(SelledItemInStore selledItemInStore)
    {
        ItemsSerialIDMap.put(selledItemInStore.getSerialNumber(), selledItemInStore);
    }

    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, SelledItemInStore>getSetOfDictionary(ItemsSerialIDMap);

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
        return name;
    }

    public SelledItemInStore getItemBySerialID(Integer serialID)
    {
        return ItemsSerialIDMap.get(serialID);
    }

    public ClosedOrder getOrderBySerialID(Integer serialID)
    {
        return ordersSerialIDMap.get(serialID);
    }


    public Integer getSerialNumber() {
        return this.serialNumber;
    }

    public int getPPK() {
        return this.PPK;
    }

    public SDMLocation getLocationOfShop()
    {
        return SDMLocationOfShop;
    }

    public boolean checkIfItemIdExists(int itemSerialNumber)
    {
        return ItemsSerialIDMap.containsKey(itemSerialNumber);
    }

    public void addItemToItemSSerialIDMap(SelledItemInStore item)
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
    public Map<Integer, SelledItemInStore> getStoresSerialIDMap()
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
    public void removeItemFromStore(int itemID)
    {
        ItemsSerialIDMap.remove(itemID);
    }
    public void addItemToStore(Item item, int priceOfItem)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), new SelledItemInStore(item, priceOfItem));
    }
    public void updatePriceOfItem(int itemID, int priceOfItem)
    {
        ItemsSerialIDMap.get(itemID).setPricePerUnit(priceOfItem);
    }
}

