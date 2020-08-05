import jaxb.schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
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



    public static List<Shop> readStoresFromXml(String xmlName) {
        InputStream inputStream = Menu.class.getResourceAsStream(xmlName);
        try {
            //    Shop(String serialNumber, String name, int PPK, Location locationOfShop)
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMStores shops = descriptor.getSDMStores();
            List<SDMStore> listOfStores = shops.getSDMStore();
            List<Shop> storesList = new ArrayList<Shop>();
            for(SDMStore store : listOfStores)
            {
                //TODO
                //Add that if something go wrong get out and return that something got wrong
                Shop shop = readStoreFromXml(store);
                storesList.add(shop);
            }
            return storesList;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<SDKItem> readItemsFromXml(String xmlName) {
        InputStream inputStream = Menu.class.getResourceAsStream(xmlName);
        try {
            SuperDuperMarketDescriptor descriptor = HandleJAXB.<SuperDuperMarketDescriptor>deserializeFrom(inputStream);
            SDMItems items = descriptor.getSDMItems();
            List<SDMItem> listOfItems = items.getSDMItem();
            List<SDKItem> itemsList = new ArrayList<SDKItem>();
            for(SDMItem item : listOfItems)
            {
                //TODO
                //Add that if something go wrong get out and return that something got wrong
                SDKItem itemToAddToList = readItemFromXml(item);
                itemsList.add(itemToAddToList);
            }
            return itemsList;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static <K> K deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (K) u.unmarshal(in);
    }

}
