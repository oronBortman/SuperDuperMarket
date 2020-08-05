import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuOptionForReadingXMLFile {
    private SDKBase base;

    public MenuOptionForReadingXMLFile(SDKBase base)
    {
        this.base = base;
    }

    public void readFromXMLFile() {
        boolean errorInShops = readShopsFromXMLFile();
        boolean errorInItems = readItemsFromXMLFile();
        if(errorInShops == false)
        {
            errorInItems = readItemsFromXMLFile();
        }


    }
    private boolean readShopsFromXMLFile() {

        System.out.println("read from XML file");

        List<Shop> listOfShops = HandleJAXB.readStoresFromXml("ex1-small.xml");

        Map<Integer, Shop> storesSerialIDMap = new HashMap<Integer, Shop>();
        Map<SDKLocation, Shop> storesLocationMap = new HashMap<SDKLocation, Shop>();


        boolean duplicateSerialIdOfShopFound = base.setStoresSerialIDMapParameterOfMethodFromList(listOfShops, storesSerialIDMap);
        boolean duplicateLocationOfShopFound = base.setStoresLocationMapParameterOfMethodFromList(listOfShops, storesLocationMap);

        if(duplicateSerialIdOfShopFound)
        {
            System.out.println("Found shops with same Serial ID! Please fix your xml file and try again");
        }
        else if(duplicateLocationOfShopFound)
        {
            System.out.println("Found shops with same location. Please fix your xml file and try again");
        }
        else
        {
            base.setStoresSerialIDMap(storesSerialIDMap);
            base.setStoresLocationMap(storesLocationMap);
        }
        return duplicateSerialIdOfShopFound && duplicateLocationOfShopFound;
    }

    private boolean readItemsFromXMLFile() {
        System.out.println("read from XML file");

        List<SDKItem> listOfItems = HandleJAXB.readItemsFromXml("ex1-small.xml");

        Map<Integer, SDKItem> itemsSerialIDMap = new HashMap<Integer, SDKItem>();

        boolean duplicateSerialIdOfItemFound = base.setItemsSerialIDMapParameterOfMethodFromList(listOfItems, itemsSerialIDMap);

        if (duplicateSerialIdOfItemFound) {
            System.out.println("Found items with same Serial ID!! Please fix your xml file and try again");
        } else {
            base.setOfItemsSerialID(itemsSerialIDMap);
        }
        return duplicateSerialIdOfItemFound;
    }

}
