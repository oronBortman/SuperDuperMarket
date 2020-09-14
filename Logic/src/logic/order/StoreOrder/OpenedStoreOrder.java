package logic.order.StoreOrder;

import logic.AvailableItemInStore;
import logic.Item;
import logic.SDMLocation;
import logic.Store;
import logic.discount.Discount;
import logic.discount.IfYouBuySDM;
import logic.discount.Offer;
import logic.order.OpenedOrder;
import logic.order.itemInOrder.OrderedItemFromSale;
import logic.order.itemInOrder.OrderedItemFromStore;
import logic.order.itemInOrder.OrderedItemFromStoreByQuantity;
import logic.order.itemInOrder.OrderedItemFromStoreByWeight;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

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

    public Map<Integer, Double> getItemsAmountLeftToUseInSalesMap() {
        return itemsAmountLeftToUseInSalesMap;
    }

    public Map<String, Discount> getDiscountsInStoresThatAreValidInOrder() {
        return discountsInStoresThatAreValidInOrder;
    }

    public List<OrderedItemFromStore> generateListOfOrdereItemsNotFromSale()
    {
        return getOrderedItemsNotFromSale().values().stream().collect(toCollection(ArrayList::new));
    }

    public List<OrderedItemFromSale> generateListOfOrderedItemFromSaleWithDiscountName()
    {
        List<OrderedItemFromSale> orderedItemFromSaleListWithDiscountNames = new ArrayList<>();
        for(Map.Entry<String, Map<Integer, OrderedItemFromStore>> stringMapEntry : getOrderedItemsFromSale().entrySet())
        {
            Map<Integer, OrderedItemFromStore> orderedItemFromStoreMap = stringMapEntry.getValue();
            String discountName = stringMapEntry.getKey();
            for(Map.Entry<Integer, OrderedItemFromStore> orderedItemFromStoreEntry : orderedItemFromStoreMap.entrySet())
            {
                OrderedItemFromStore orderedItemFromStore = orderedItemFromStoreEntry.getValue();
                orderedItemFromSaleListWithDiscountNames.add(new OrderedItemFromSale(discountName, orderedItemFromStore));
            }
        }
        return orderedItemFromSaleListWithDiscountNames;
    }

    public List<Discount> generateListOfDiscountsInStoresThatAreValidInOrder() {
        return discountsInStoresThatAreValidInOrder.values().stream().collect(toCollection(ArrayList::new));
    }

    public void addToOrderedItemFromOfferList(String discountName) {
        for(Offer offer : getDiscountByName(discountName).getThenYouGet().getOfferList())
        {
            addToOrderedItemsFromSale(discountName, offer);
        }
    }


    public void addToOrderedItemsFromSale(String discountName, Offer offer)
    {
        Integer itemID = offer.getItemId();
        Integer price = offer.getForAdditional();
        Double quantity = offer.getQuantity();
        AvailableItemInStore availableItemInStore = new AvailableItemInStore(storeUsed.getItemBySerialID(itemID));
        availableItemInStore.setPricePerUnit(price);
        Map<String, Map<Integer, OrderedItemFromStore>> orderedItemFromStoreByDiscountNameMap = getOrderedItemsFromSale();
        OrderedItemFromStore orderedItemFromStore = null;

         if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Quantity)
         {
             orderedItemFromStore = new OrderedItemFromStoreByQuantity(availableItemInStore, quantity);
         }
         else if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Weight) {
             orderedItemFromStore = new OrderedItemFromStoreByWeight(availableItemInStore, quantity);
         }

        if(orderedItemFromStoreByDiscountNameMap.containsKey(discountName))
        {
            Map<Integer,OrderedItemFromStore> orderedItemFromStoreMap = getOrderedItemsMapFromSaleByDiscountName(discountName);
            if(orderedItemFromStoreMap != null)
            {
                if(orderedItemFromStoreMap.containsKey(itemID))
                {
                    orderedItemFromStore = orderedItemFromStoreMap.get(itemID);
                    orderedItemFromStore.addQuantity(quantity);
                }
                else
                {
                    orderedItemFromStoreMap.put(itemID, orderedItemFromStore);
                }
            }
        }
        else
        {
            addingNewOrderMapToMap(discountName, orderedItemFromStore);
        }
    }

    public void addingNewOrderMapToMap(String discountName, OrderedItemFromStore orderedItemFromStore) {
        Map<Integer, OrderedItemFromStore> orderedItemFromStoreMap = new HashMap<Integer, OrderedItemFromStore>();
        orderedItemFromStoreMap.put(orderedItemFromStore.getSerialNumber(), orderedItemFromStore);
        getOrderedItemsFromSale().put(discountName, orderedItemFromStoreMap);
    }


    /*public void addItemToDiscount(String discountName, OrderedItemFromStore orderedItemFromStore)
    {
        if(getOrderedItemsFromSale().containsKey(discountName))
        {
            Map<Integer,OrderedItemFromStore> orderedItemFromStoreMap = getOrderedItemsMapFromSaleByDiscountName(discountName);
            if(orderedItemFromStoreMap != null && orderedItemFromStoreMap.containsKey(orderedItemFromStore.getSerialNumber()))
            {
                OrderedItemFromStore orderedItemFromStore = orderedItemFromStoreMap.get(itemID);
                orderedItemFromStore.addQuantity(quantity);
            }
        }
    }*/

    public void initializeDiscountInStoresThatValidInOrder()
    {
        List<Discount> discountsFromStore = generateDiscountsFromStoreByOrderedItems();
        for(Discount discount : discountsFromStore)
        {
            discountsInStoresThatAreValidInOrder.put(discount.getName(), discount);
        }
    }

    public void initializeItemsAmountLeftToUseInStoresThatValidInOrder()
    {
        List<Discount> discountsFromStore = generateDiscountsFromStoreByOrderedItems();
        for(Discount discount : discountsFromStore)
        {
            IfYouBuySDM ifYouBuySDM = discount.getIfYouBuySDM();
            Integer itemSerialID = ifYouBuySDM.getItemId();
            Double itemQuantity = getOrderedItemsNotFromSale().get(itemSerialID).getTotalAmountOfItemOrderedByTypeOfMeasure();
            itemsAmountLeftToUseInSalesMap.put(itemSerialID,itemQuantity);
        }
    }


    public void applyOneOfDiscountOnStore(String discountName, Offer offer)
    {
        System.out.println("Apply one of discount in store");
        addToOrderedItemsFromSale(discountName, offer);
        System.out.println("Added one of discount in store");
        applyDiscountInformationWithChangesAfterDiscountApply(discountName);
    }

    public void applyAllOrNothingDiscountOnStore(String discountName)
    {
        System.out.println("Apply All Or Nothing of discount in store");
        addToOrderedItemFromOfferList(discountName);
        System.out.println("Added All Or Nothing of discount in store");
        applyDiscountInformationWithChangesAfterDiscountApply(discountName);
    }

    public void applyDiscountInformationWithChangesAfterDiscountApply(String discountName)
    {
        Discount discount = discountsInStoresThatAreValidInOrder.get(discountName);
        if(checkIfSaleCanBeApplyed(discount))
        {
            updateQuantityInOrderedItem(discount);
            if(checkIfSaleCanBeApplyed(discount) == false)
            {
                itemsAmountLeftToUseInSalesMap.remove(discount.getIfYouBuySDM().getItemId());
                discountsInStoresThatAreValidInOrder.remove(discountName);
            }
        }
    }

    public boolean checkIfSaleCanBeApplyed(Discount discount)
    {
        IfYouBuySDM ifYouBuySDM = discount.getIfYouBuySDM();
        Double quantityFromSale = ifYouBuySDM.getQuantity();
        Integer itemSerialID = ifYouBuySDM.getItemId();
        Double currentQuantityFromOrder = itemsAmountLeftToUseInSalesMap.get(itemSerialID);
        Double newQuantity = currentQuantityFromOrder - quantityFromSale;
        System.out.println("Sale can be applied: " + (newQuantity >=0));
        return newQuantity >= 0;
    }

    public void updateQuantityInOrderedItem(Discount discount)
    {
        IfYouBuySDM ifYouBuySDM = discount.getIfYouBuySDM();
        Double quantityFromSale = ifYouBuySDM.getQuantity();
        System.out.println("Quantity from sale:" + quantityFromSale);
        Integer itemSerialID = ifYouBuySDM.getItemId();
        Double currentQuantityFromOrder = itemsAmountLeftToUseInSalesMap.get(itemSerialID);
        System.out.println("Quantity in store:" + currentQuantityFromOrder);
        Double newQuantity = currentQuantityFromOrder - quantityFromSale;
        System.out.println("New quantity for sale After decrease:" + newQuantity);
        itemsAmountLeftToUseInSalesMap.put(itemSerialID, newQuantity);
    }

    public Discount getDiscountByName(String discountName)
    {
        return discountsInStoresThatAreValidInOrder.get(discountName);
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
        return calcTotalPriceOfItemsNotFromSale() + calcTotalDeliveryPrice(inputLocation);
    }

    @Override
    public Double calcTotalPriceOfItemsNotFromSale()
    {
        return getOrderedItemsNotFromSale().values().stream().mapToDouble(OrderedItemFromStore::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
    }

    public Double calcTotalPriceOfItemsFromSale()
    {
        Map<String, Map<Integer, OrderedItemFromStore>> orderedItemsFromSale = getOrderedItemsFromSale();
        Double totalPrice=0.0;
        for(Map<Integer, OrderedItemFromStore> orderedItemFromStoreMap : orderedItemsFromSale.values())
        {
            totalPrice+=orderedItemFromStoreMap.values().stream().mapToDouble(OrderedItemFromStore::getTotalPriceOfItemOrderedByTypeOfMeasure).sum();
        }
        return totalPrice;
    }

    public Double calcTotalAmountOfItemsNotFromSaleByUnit()
    {
        return getOrderedItemsNotFromSale().values().stream().mapToDouble(OrderedItemFromStore::getAmountOfItemOrderedByUnits).sum();
    }

    public Double calcTotalAmountOfItemsFromSaleByUnit()
    {
        Map<String, Map<Integer, OrderedItemFromStore>> orderedItemsFromSale = getOrderedItemsFromSale();
        Double totalAmount=0.0;
        for(Map<Integer, OrderedItemFromStore> orderedItemFromStoreMap : orderedItemsFromSale.values())
        {
            totalAmount+=orderedItemFromStoreMap.values().stream().mapToDouble(OrderedItemFromStore::getAmountOfItemOrderedByUnits).sum();
        }
        return totalAmount;
    }

    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/



    public Integer calcTotalAmountOfItemsTypeNotFromSale()
    {
        return getOrderedItemsNotFromSale().size();
    }

    public Integer calcTotalAmountOfItemsTypeFromSale()
    {
        Map<String, Map<Integer, OrderedItemFromStore>> orderedItemsFromSale = getOrderedItemsFromSale();
        Integer totalAmount=0;
        for(Map<Integer, OrderedItemFromStore> orderedItemFromStoreMap : orderedItemsFromSale.values())
        {
            totalAmount+=orderedItemFromStoreMap.size();
        }
        return totalAmount;
    }


    public ClosedStoreOrder closeOrder(SDMLocation location)
    {
        Double totalPriceOfItems = calcTotalPriceOfItemsNotFromSale();
        Double deliveryPriceAfterOrderIsDone = calcTotalDeliveryPrice(location);
        Double totalPriceOfOrderAfterItsDone = calcTotalPriceOfOrder(location);
        Double totalAmountOfItemsByUnit = calcTotalAmountOfItemsNotFromSaleByUnit();
        Integer totalAmountOfItemsType = calcTotalAmountOfItemsTypeNotFromSale();
        ClosedStoreOrder closedStoreOrder = new ClosedStoreOrder(deliveryPriceAfterOrderIsDone, totalPriceOfOrderAfterItsDone,totalAmountOfItemsByUnit, totalAmountOfItemsType, totalPriceOfItems, storeUsed, getOrderedItemsNotFromSale(), getDate(), isOrderStatic());
        getStoreUsed().addClosedOrderToHistory(closedStoreOrder);
        return closedStoreOrder;
    }
}
