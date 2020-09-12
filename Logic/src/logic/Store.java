package logic;

import exceptions.DuplicateDiscountNameException;
import exceptions.ItemIDNotExistInAStoreException;
import exceptions.ItemNotExistInStoresException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import jaxb.schema.generated.*;
import logic.discount.Discount;
import logic.discount.Offer;
import logic.discount.ThenYouGetSDM;
import logic.order.ClosedOrder;
import logic.order.GeneralMethods;
import logic.order.StoreOrder.ClosedStoreOrder;

import java.util.*;

public class Store {
    private Integer serialNumber;
    private String name;
    private Map<Integer, AvailableItemInStore> ItemsSerialIDMap;
    private Map<Integer, ClosedStoreOrder> ordersSerialIDMap;
    private Map<Integer, Discount> discountNameDMap;

    private Integer PPK;
    private SDMLocation SDMLocationOfShop;

    public Store(Integer serialNumber, String name, int PPK, SDMLocation SDMLocationOfShop)
    {
        ItemsSerialIDMap = new HashMap<Integer, AvailableItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedStoreOrder>();
        discountNameDMap = new HashMap<Integer, Discount>();

        this.serialNumber = serialNumber;
        this.name = name;
        this.PPK = PPK;
        this.SDMLocationOfShop = SDMLocationOfShop;
    }

    public Store(SDMStore shop)
    {
        ItemsSerialIDMap = new HashMap<Integer, AvailableItemInStore>();
        ordersSerialIDMap = new HashMap<Integer, ClosedStoreOrder>();
        discountNameDMap = new HashMap<Integer, Discount>();

        this.serialNumber = shop.getId();
        this.name = shop.getName();
        this.PPK = shop.getDeliveryPpk();
        SDMLocation location = new SDMLocation(shop.getLocation());
        this.SDMLocationOfShop = location;
    }

    public void addDiscountToStoreFromXML(SDMDiscount discount) throws DuplicateDiscountNameException, ItemIDNotExistInAStoreException {
        IfYouBuy ifYouBuySDM = discount.getIfYouBuy();
        String discountName = discount.getName();
        ThenYouGet thanYouGetS = discount.getThenYouGet();
        System.out.println("inside addDiscountToStoreFromXML");
        if(checkIfDiscountHasUniqueName(discountName) == false)
        {
            throw new DuplicateDiscountNameException(discountName, name);
        }
        else if(checkIfItemIdExists(ifYouBuySDM.getItemId()) == false)
        {
            throw new ItemIDNotExistInAStoreException(ifYouBuySDM.getItemId(), name);
        }
        else
        {

            addOffersToThenYouGet(thanYouGetS);
            System.out.println("suceed adding offer to thenYouGet");
        }

    }

    public void addOffersToThenYouGet(ThenYouGet thenYouGet) throws ItemIDNotExistInAStoreException {
        ThenYouGetSDM thenYouGetSDM = new ThenYouGetSDM(thenYouGet);
        List<SDMOffer> sdmOfferList = thenYouGet.getSDMOffer();
        System.out.println("Inside addOffersToThenYouGet method");
        for(SDMOffer sdmOffer : sdmOfferList)
        {
            System.out.println("Inside sdmOffer");
            if(checkIfItemIdExists(sdmOffer.getItemId()) == false)
            {
                System.out.println(" fail Inside sdmOffer");

                throw new ItemIDNotExistInAStoreException(sdmOffer.getItemId(), name);
            }
            else
            {
                thenYouGetSDM.addOfferToListFromSDMOffer(sdmOffer);
                System.out.println("Succeed Inside sdmOffer");

            }
        }
    }

    public boolean checkIfDiscountHasUniqueName(String nameOfDiscount)
    {
        return !discountNameDMap.containsKey(nameOfDiscount);
    }

    public List<AvailableItemInStore> getAvailableItemsList()
    {
        return new ArrayList<AvailableItemInStore>(ItemsSerialIDMap.values());
    }

    public List<Item> getItemsList()
    {
        List<Item> list = new ArrayList<Item>();
        for(AvailableItemInStore itemInStore : ItemsSerialIDMap.values())
        {
            list.add(itemInStore);
        }
        return list;
    }


    private void addItemToShop(AvailableItemInStore availableItemInStore)
    {
        ItemsSerialIDMap.put(availableItemInStore.getSerialNumber(), availableItemInStore);
    }

    public Set<Integer> getSetOfItemsSerialID()
    {
        return GeneralMethods.<Integer, AvailableItemInStore>getSetOfDictionary(ItemsSerialIDMap);

    }

    public Set<Integer> getSetOfOrdersSerialID()
    {
        return GeneralMethods.<Integer, ClosedStoreOrder>getSetOfDictionary(ordersSerialIDMap);

    }

    public boolean checkIfItemIdIsUnique(String serialNumber)
    {
        return ItemsSerialIDMap.containsKey(serialNumber) ;
    }

    //TODO
    public double calcProfitOfDelivers()
    {
        return ordersSerialIDMap.values().stream().mapToDouble(ClosedStoreOrder::getDeliveryPriceAfterOrder).sum();
    }

    public String getName()
    {
        return name;
    }

    public AvailableItemInStore getItemBySerialID(Integer serialID)
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

    public Integer getPPK() {
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

    public void addItemToItemSSerialIDMap(AvailableItemInStore item)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), item);
    }

    public void addClosedOrderToHistory(ClosedStoreOrder order)
    {
        ordersSerialIDMap.put(order.getSerialNumber(), order);
    }

    public int getAmountOfOrder()
    {
        return ordersSerialIDMap.size();
    }
    public Map<Integer, AvailableItemInStore> getStoresSerialIDMap()
    {
        return ItemsSerialIDMap;
    }
    public int getAmountOfItemSoledByUnit(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().mapToInt(closedStoreOrder -> closedStoreOrder.getAmountOfCertainItemByUnit(itemID)).sum();
    }
    public double getAmountOfItemSoledByTypeOfMeasure(Integer itemID)
    {
        return ordersSerialIDMap.values().stream().mapToDouble(closedStoreOrder -> closedStoreOrder.getAmountOfCertainItemByTypeOfMeasure(itemID)).sum();
    }

    public boolean checkIfItemIsTheOnlyOneInStore(Integer itemID)
    {
        return ItemsSerialIDMap.size() == 1 && checkIfItemIdExists(itemID);
    }
    public void removeItemFromStore(int itemID)
    {
        ItemsSerialIDMap.remove(itemID);
    }
    public void addItemToStore(Item item, int priceOfItem)
    {
        ItemsSerialIDMap.put(item.getSerialNumber(), new AvailableItemInStore(item, priceOfItem));
    }
    public void updatePriceOfItem(int itemID, int priceOfItem)
    {
        ItemsSerialIDMap.get(itemID).setPricePerUnit(priceOfItem);
    }
}

