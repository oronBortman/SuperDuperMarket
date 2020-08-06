import jaxb.schema.generated.SDMStore;

import java.util.*;

public class Store {
    private Integer serialNumber;
    private String name;
    private Map<Integer, SelledItemInStore> ItemsSerialIDMap;
    private List<Order> listOfOrders;
    private int PPK;
    private SDMLocation SDMLocationOfShop;

    Store(Integer serialNumber, String name, int PPK, SDMLocation SDMLocationOfShop)
    {
        ItemsSerialIDMap = new HashMap<Integer, SelledItemInStore>();
        this.serialNumber = serialNumber;
        this.name = name;
        this.PPK = PPK;
        this.SDMLocationOfShop = SDMLocationOfShop;
    }

    public Store(SDMStore shop)
    {
        ItemsSerialIDMap = new HashMap<Integer, SelledItemInStore>();
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

    public boolean checkIfItemIdIsUnique(String serialNumber)
    {
        return ItemsSerialIDMap.containsKey(serialNumber) ;
    }

    //TODO
    public int calcProfitOfDelivers()
    {
        return 1;
    }

    public String getName()
    {
        return name;
    }

    public SelledItemInStore getItemySerialID(Integer serialID)
    {
        return ItemsSerialIDMap.get(serialID);
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

    public List<Order> getListOfOrders()
    {
        return listOfOrders;
    }
    public boolean checkIfItemIdExists(int itemSerialNumber)
    {
        return ItemsSerialIDMap.containsKey(itemSerialNumber);
    }

    public void addItemToItemSSerialIDMap(SelledItemInStore item)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), item);
    }



    public Map<Integer, SelledItemInStore> getStoresSerialIDMap()
    {
        return ItemsSerialIDMap;
    }

}

