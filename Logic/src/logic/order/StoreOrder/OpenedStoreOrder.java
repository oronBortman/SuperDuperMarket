package logic.order.StoreOrder;

import logic.SDMLocation;
import logic.Store;
import logic.discount.Discount;
import logic.discount.IfYouBuySDM;
import logic.discount.Offer;
import logic.discount.ThenYouGetSDM;
import logic.order.OpenedOrder;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.util.*;

import static logic.common.SuperDuperMarketConstants.ONE_OF;
import static logic.common.SuperDuperMarketConstants.ALL_OR_NOTHING;

public class OpenedStoreOrder extends StoreOrder implements OpenedOrder {


    Map<String, Discount> discountsInStoresThatAreValidInOrder = new HashMap<String, Discount>();
    Map<Integer, Double> itemsAmountLeftToUseInSalesMap = new HashMap<Integer, Double>();

    public OpenedStoreOrder(Store store, Date date, boolean isOrderStatic)
    {
        super(store, date, isOrderStatic);
    }

    public OpenedStoreOrder(Date date, boolean isOrderStatic)
    {
        super(date, isOrderStatic);
    }

    @Override
    public Double calcTotalDeliveryPrice(SDMLocation inputLocation) {
        SDMLocation storeLocation = storeUsed.getLocationOfShop();
        int PPK = storeUsed.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }

    public void initializeDiscountInStoresThatValidInOrder()
    {
        List<Discount> discountsFromStore = generateDiscountsFromStoreByOrderedItems();
        for(Discount discount : discountsFromStore)
        {
            discountsInStoresThatAreValidInOrder.put(discount.getName(), discount);
        }
    }

    public void applyOneOfDiscountOnStore(String discountName)
    {
        Discount discount = discountsInStoresThatAreValidInOrder.get(discountName);
        updateQuantityInOrderedItem(discount);
        handleThenYouGet(discount);

    }

    public void updateQuantityInOrderedItem(Discount discount)
    {
        IfYouBuySDM ifYouBuySDM = discount.getIfYouBuySDM();
        Double quantityFromSale = ifYouBuySDM.getQuantity();
        Integer itemSerialID = ifYouBuySDM.getItemId();
        Double currentQuantityFromOrder = itemsAmountLeftToUseInSalesMap.get(itemSerialID);
        Double newQuantity = currentQuantityFromOrder - quantityFromSale;
    }

    public void handleThenYouGet(Discount discount)
    {
        ThenYouGetSDM thenYouGetSDM = discount.getThenYouGet();
        String operator = thenYouGetSDM.getOperator();
        if(operator.equals(ONE_OF))
        {
            //handleOneOfOperator(thenYouGetSDM.getOfferList());
        }
        else if(operator.equals(ALL_OR_NOTHING))
        {
            //handleAllOrNothingOperator(thenYouGetSDM.getOfferList());
        }
    }

    public void handleOneOfOperator(List<Offer> offerList, int itemChosenSerialID)
    {

    }

    public void handleAllOrNothingOperator(List<Offer> offerList, boolean takeAllItems)
    {

    }

    public List<Discount> generateDiscountsFromStoreByOrderedItems()
    {
        return storeUsed.generateListOfDiscountsThatContainsItemsFromList(getOrderedItemsNotFromSale().keySet());
    }

    public boolean checkIfDiscountExistsInOrderByName(String discountName)
    {
        return discountsInStoresThatAreValidInOrder.containsKey(discountName);
    }

    public void addItemToItemsMapOfOrder(OrderedItemFromStore orderedItemFromStore)
    {
        getOrderedItemsNotFromSale().put(orderedItemFromStore.getSerialNumber(), orderedItemFromStore);
    }

    /*public Double calcDeliverPriceFromStore(SDMLocation inputLocation, Store store) {
        SDMLocation storeLocation = store.getLocationOfShop();
        int PPK = store.getPPK();
        double distanceBetweenTwoLocations = inputLocation.getAirDistanceToOtherLocation(storeLocation);
        return(PPK * distanceBetweenTwoLocations);
    }*/


    @Override
    public Double calcTotalPriceOfOrder(SDMLocation inputLocation)
    {
        return calcTotalPriceOfItems() + calcTotalDeliveryPrice(inputLocation);
    }

    @Override
    public Double calcTotalPriceOfItems()
    {
        return getOrderedItemsNotFromSale().values().stream().mapToDouble(OrderedItemFromStore::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    @Override
    public Integer calcTotalAmountOfItemsByUnit()
    {
        return new Integer(getOrderedItemsNotFromSale().values().stream().mapToInt(OrderedItemFromStore::getAmountOfItemOrderedByUnits).sum());
    }

    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/



    public Integer calcTotalAmountOfItemsType()
    {
        return getOrderedItemsNotFromSale().size();
    }


    public ClosedStoreOrder closeOrder(SDMLocation location)
    {
        Double totalPriceOfItems = calcTotalPriceOfItems();
        Double deliveryPriceAfterOrderIsDone = calcTotalDeliveryPrice(location);
        Double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        Integer totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        Integer totalAmountOfItemsType = calcTotalAmountOfItemsType();
        ClosedStoreOrder closedStoreOrder = new ClosedStoreOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, storeUsed, getOrderedItemsNotFromSale(), getDate(), isOrderStatic());
        getStoreUsed().addClosedOrderToHistory(closedStoreOrder);
        return closedStoreOrder;
    }
}
