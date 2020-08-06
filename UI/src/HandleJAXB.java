import jaxb.schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleJAXB {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.schema.generated";

    public static SDKLocation createSDKLocationFromLocationObject(Location location)
    {
        int coordinateX = location.getX();
        int coordinateY = location.getY();
        return(new SDKLocation(coordinateX, coordinateY));
    }

    public static Shop readStoreFromXml(SDMStore shop)
    {
        //    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
        System.out.println("serial number of first store is: " + shop.getId());
        System.out.println("name of first store is: " + shop.getName());
        System.out.println("PPK of first store is: " + shop.getDeliveryPpk());
        System.out.println("x of first store is: " + shop.getLocation().getX());
        System.out.println("y of first store is: " + shop.getLocation().getY());

        int shopID = shop.getId();
        String shopName = shop.getName();
        int shopPpk = shop.getDeliveryPpk();
        SDKLocation location = createSDKLocationFromLocationObject(shop.getLocation());

        //TODO
        //Add applicative checkings that the parameters are good

        return(new Shop(shopID, shopName, shopPpk, location));
    }

    public static SDKItem readItemFromXml(SDMItem item)
    {
        //    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
        System.out.println("serial number item is: " + item.getId());
        System.out.println("name of item is: " + item.getName());
        System.out.println("Purchase category of item is: " + item.getPurchaseCategory());
        int itemID = item.getId();
        String itemName = item.getName();
        String itemPurchaseCategoryStr = item.getPurchaseCategory();
        SDKItem.TypeOfMeasure itemPurchaseCategory = SDKItem.TypeOfMeasure.convertStringToEnum(itemPurchaseCategoryStr);

        //TODO
        //Add applicative checkings that the parameters are good

        return(new SDKItem(itemID, itemName, itemPurchaseCategory));
    }



    public static Map<Integer, Shop> createStoresSerialIDMapFromXml(String xmlName) {
        InputStream inputStream = MainMenu.class.getResourceAsStream(xmlName);
        Map<Integer, Shop> storesSerialIDMap = new HashMap<Integer, Shop>();
        boolean duplicateSerialIDOfStore=false;
        try {
            //    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMStores shops = descriptor.getSDMStores();
            List<SDMStore> listOfStores = shops.getSDMStore();

            for(SDMStore store : listOfStores)
            {
                if(storesSerialIDMap.containsKey(store.getId()))
                {
                    System.out.println("The shop " + store.getName() + "serial id is not unique.");
                    System.out.println("Please fix your xml file and try again");
                    duplicateSerialIDOfStore=true;
                    return null;
                }
                else
                {
                    //TODO
                    //Add that if something go wrong get out and return that something got wrong
                    Shop storeToAddToMap = readStoreFromXml(store);
                    storesSerialIDMap.put(storeToAddToMap.getSerialNumber(), storeToAddToMap);
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return storesSerialIDMap;
    }

    public static Map<SDKLocation, Shop>  createStoresLocationMapFromXml(String xmlName) {
        InputStream inputStream = MainMenu.class.getResourceAsStream(xmlName);
        Map<SDKLocation, Shop> storesLocationMap = new HashMap<SDKLocation, Shop>();
        boolean duplicateSerialIDOfStore=false;
        try {
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMStores shops = descriptor.getSDMStores();
            List<SDMStore> listOfStores = shops.getSDMStore();
            for(SDMStore store : listOfStores)
            {
                Location location = store.getLocation();
                if(SDKLocation.checkIfLocationCoordinatesIsValid(location.getX()) == false)
                {
                    System.out.println("The shop " + store.getName() + "coordinate x is not valid.");
                    System.out.println("Please fix your xml file and try again");
                    return null;
                }
                else if(storesLocationMap.containsKey(location))
                {
                    System.out.println("Found shops with same Location! Please fix your xml file and try again\"");
                    return null;
                }
                else
                {
                    //TODO
                    //Add that if something go wrong get out and return that something got wrong
                    Shop shopToAddToMap = readStoreFromXml(store);
                    storesLocationMap.put(new SDKLocation(location.getX(), location.getY()), shopToAddToMap);
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return storesLocationMap;
    }


    public static Map<Integer, SDKItem> createItemsSerialIDMapFromXml(String xmlName) {
        InputStream inputStream = MainMenu.class.getResourceAsStream(xmlName);
        Map<Integer, SDKItem> itemSerialIDMap = new HashMap<Integer, SDKItem>();
        boolean duplicateSerialIDOfItem=false;
        try {
            //    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMItems items = descriptor.getSDMItems();
            List<SDMItem> listOfItems = items.getSDMItem();

            for(SDMItem item : listOfItems)
            {
                if(itemSerialIDMap.containsKey(item.getId()))
                {
                    System.out.println("The item " + item.getName() + "serial id is not unique.");
                    System.out.println("Please fix your xml file and try again");
                    duplicateSerialIDOfItem=true;
                    return null;
                }
                else
                {
                    //TODO
                    //Add that if something go wrong get out and return that something got wrong
                    SDKItem itemToAddToMap = readItemFromXml(item);
                    itemSerialIDMap.put(item.getId(), itemToAddToMap);
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return itemSerialIDMap;
    }
    public static boolean createSellsFromXml(String xmlName, Map<Integer, Shop> storesSerialIDMap, Map<Integer, SDKItem> itemsSerialIDMap) {
        InputStream inputStream = MainMenu.class.getResourceAsStream(xmlName);
        Map<Integer, Integer> storesSellsIDMap = new HashMap<Integer, Integer>();
        boolean error=false;

        try {
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMStores shops = descriptor.getSDMStores();
            List<SDMStore> listOfStores = shops.getSDMStore();

            for(SDMStore store : listOfStores)
            {
                SDMPrices prices = store.getSDMPrices();
                List<SDMSell> sellsDetails = prices.getSDMSell();
                Map<Integer, Integer> sellsDetailsMap = new HashMap<Integer, Integer>();
                Integer shopSerialID = store.getId();
                for(SDMSell singleSellDetails : sellsDetails)
                {
                    int itemSerialID = singleSellDetails.getItemId();
                    int itemPrice = singleSellDetails.getPrice();

                    if(itemsSerialIDMap.containsKey(itemSerialID) == false)
                    {
                        System.out.println("An item with serial id " + itemSerialID + " doesn't exist.");
                        System.out.println("Please fix your xml file and try again");
                        error=true;
                        return error;
                    }
                    else if(sellsDetailsMap.containsKey(itemSerialID))
                    {
                        System.out.println("The item " + itemsSerialIDMap.get(itemSerialID).getName() + " serial id " + itemSerialID + " isn't unique in SDMSell" + " of the store " + store.getName());
                        System.out.println("Please fix your xml file and try again");
                        error=true;
                        return error;
                    }
                    else
                    {
                        sellsDetailsMap.put(itemSerialID,itemPrice);
                        SDKItem itemInSDK = itemsSerialIDMap.get(itemSerialID);
                        String itemNameInSDK = itemInSDK.getName();
                        SDKItem.TypeOfMeasure itemMeasureTypeInSDK = itemInSDK.getTypeOfMeasure();

                        SelledItemInStore selledItemInStore = new SelledItemInStore(itemSerialID, itemNameInSDK, itemMeasureTypeInSDK);
                        storesSerialIDMap.get(shopSerialID).addItemToItemSSerialIDMap(selledItemInStore);
                    }
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return error;
    }
    private static <K> K deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (K) u.unmarshal(in);
    }

}
