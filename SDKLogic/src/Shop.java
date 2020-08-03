import java.util.*;

public class Shop {
    private String serialNumber;
    private String name;
    private Map<String, SelledItemInStore> ItemsSerialIDMap;
    private List listOfOrders;
    private int PPK;
    private Location locationOfShop;

    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
    {
        ItemsSerialIDMap = new HashMap<String, SelledItemInStore>;
        this.serialNumber = serialNumber;
        this.name = name;
        this.PPK = PPK;
        this.locationOfShop = locationOfShop;
    }

    private void addItemToShop(SelledItemInStore selledItemInStore)
    {
        ItemsSerialIDMap.put(selledItemInStore.getSerialNumber(), selledItemInStore);
    }

    public Set<String> getSetOfItemsSerialID()
    {
        return GeneralMethods.<String, SelledItemInStore>getSetOfDictionary(ItemsSerialIDMap);

    }

    public boolean checkIfItemIdIsUnique(String serialNumber)
    {
        return ItemsSerialIDMap.containsKey(serialNumber) ;
    }

    private int calcProfitOfDelivers()
    {
        return 1;
    }

    public String getName()
    {
        return name;
    }

    public SelledItemInStore getItemySerialID(String serialID)
    {
        return ItemsSerialIDMap.get(serialID);
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public int getPPK() {
        return this.PPK;
    }

    public Location getLocationOfShop()
    {
        return locationOfShop;
    }

}

