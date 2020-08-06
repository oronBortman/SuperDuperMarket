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
        boolean errorInItems;
        if(errorInShops == false)
        {
            errorInItems = readItemsFromXMLFile();
            if(errorInItems == false)
            {
                HandleJAXB.createSellsFromXml("ex1-small.xml", base.getStoresSerialIDMap(),base.getItemsSerialIDMap());
            }
        }


    }
    private boolean readShopsFromXMLFile() {

        System.out.println("read from XML file");

        boolean error;
        Map<Integer, Shop>  storesSerialIDMap = HandleJAXB.createStoresSerialIDMapFromXml("ex1-small.xml");
        error=false;
        if(storesSerialIDMap != null)
        {
            base.setStoresSerialIDMap(storesSerialIDMap);
            Map<SDKLocation, Shop>  storesLocationMap = HandleJAXB.createStoresLocationMapFromXml("ex1-small.xml");
            if(storesLocationMap == null)
            {
                error=true;
            }
            else
            {
                base.setStoresLocationMap(storesLocationMap);
            }
        }
        else
        {
            error=true;
        }
        return error;
    }

    private boolean readItemsFromXMLFile() {
        System.out.println("read from XML file");

        Map<Integer, SDKItem> itemsSerialIDMap = HandleJAXB.createItemsSerialIDMapFromXml("ex1-small.xml");

        boolean error;

        if (itemsSerialIDMap != null) {
            base.setOfItemsSerialID(itemsSerialIDMap);
            error=false;
        }
        else
        {
            error=true;
        }
        return error;
    }

}
