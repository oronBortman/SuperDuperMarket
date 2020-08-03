import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SDKBase {

    private Map<Location, Shop> storesLocationMap = new HashMap<Location, Shop>();
    private Map<String, Shop> storesSerialIDMap;
    private Map<String, SDKItem> itemsSerialIDMap;


    public Set<String> getStoresSerialIDMap()
    {
        return GeneralMethods.<String, Shop>getSetOfDictionary(storesSerialIDMap);
    }

    public Set<String> getItemsSerialIDMap()
    {
        return GeneralMethods.<String, SDKItem>getSetOfDictionary(itemsSerialIDMap);

    }

    public Shop getStoreBySerialID(String serialID)
    {
        return storesSerialIDMap.get(serialID);
    }

    public SDKItem getItemBySerialID(String itemID)
    {
        return itemsSerialIDMap.get(itemID);
    }


}
