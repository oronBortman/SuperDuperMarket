import java.util.*;

public class Shop {
    private Integer serialNumber;
    private String name;
    private Map<Integer, SelledItemInStore> ItemsSerialIDMap;
    private List<Order> listOfOrders;
    private int PPK;
    private SDKLocation SDKLocationOfShop;

    Shop(Integer serialNumber, String name, int PPK, SDKLocation SDKLocationOfShop)
    {
        ItemsSerialIDMap = new HashMap<Integer, SelledItemInStore>();
        this.serialNumber = serialNumber;
        this.name = name;
        this.PPK = PPK;
        this.SDKLocationOfShop = SDKLocationOfShop;
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

    public SDKLocation getLocationOfShop()
    {
        return SDKLocationOfShop;
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

