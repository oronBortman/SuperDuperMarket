import java.util.Map;

public class MenuOptionForReadingXMLFile {
    private SDKBase base;
    private HandleJAXB handleJAXB;

    public MenuOptionForReadingXMLFile(SDKBase base)
    {
        this.base = base;
        this.handleJAXB = new HandleJAXB();
    }

    public void readFromXMLFile() {
        boolean errorInShops = readShopsFromXMLFile();
        boolean errorInItems;
        if(errorInShops == false)
        {
            errorInItems = readItemsFromXMLFile();
            if(errorInItems == false)
            {
                handleJAXB.addItemsToStoresFromXml("ex1-small.xml", base.getStoresSerialIDMap(),base.getItemsSerialIDMap());
            }
        }


    }
    private boolean readShopsFromXMLFile() {

        System.out.println("read from XML file");

        boolean error;
        Map<Integer, Shop>  storesSerialIDMap = handleJAXB.createStoresSerialIDMapFromXml("ex1-small.xml");
        error=false;
        if(storesSerialIDMap != null)
        {
            base.setStoresSerialIDMap(storesSerialIDMap);
            Map<SDKLocation, Shop>  storesLocationMap = handleJAXB.createStoresLocationMapFromXml("ex1-small.xml");
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

        Map<Integer, SDKItem> itemsSerialIDMap = handleJAXB.createItemsSerialIDMapFromXml("ex1-small.xml");

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
