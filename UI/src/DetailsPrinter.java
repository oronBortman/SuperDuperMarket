import java.util.List;
import java.util.Set;

public class DetailsPrinter {
    private Logic base;

    public DetailsPrinter(Logic base)
    {
        this.base = base;
    }

   /* public void showStoreWithSellsItemsDetails() {
        System.out.println("show shop details");
        Set<Integer> setOfShopSerial = base.getSetOfStoresSerialID();
        Shop shop;
        for(Integer shopSerialID : setOfShopSerial)
        {
            shop = base.getStoreBySerialID(shopSerialID);

            System.out.println("Serial number of Store: " + shopSerialID);
            System.out.println("Name of Store:" + shop.getName());
            System.out.println("List of items the Store sells:");
            showSelledItemsDetailsOfStore(shopSerialID);
            //TODO:
           // showOrdersDetailsOfStore(shopSerialID);
            System.out.println("PPK: " + shop.getPPK());
        }

    }*/

    public void showStoresDetails(boolean showItemsInStore, boolean showOrderDetailsOfStore) {
        System.out.println("show shop general details");
        Set<Integer> setOfShopSerial = base.getSetOfStoresSerialID();
        Store store;
        for(Integer shopSerialID : setOfShopSerial)
        {
            store = base.getStoreBySerialID(shopSerialID);

            System.out.println("Serial number of Store: " + shopSerialID);
            System.out.println("Name of Store:" + store.getName());
            System.out.println("List of items the Store sells:");
            System.out.println("PPK: " + store.getPPK());
            if(showItemsInStore)
            {
                showSelledItemsDetailsOfStore(shopSerialID, true, true);
            }
            if(showOrderDetailsOfStore)
            {
                //TODO complete showOrders
                //showOrdersDetailsOfStore(shopSerialID);
                System.out.println("Total payment of delivers till now:" + store.calcProfitOfDelivers());
            }
        }

    }

    public void showSelledItemsDetailsOfStore(Integer storeSerialID, boolean showItemsSoldDetails, boolean showOrdersDetails) {
        Store store = base.getStoreBySerialID(storeSerialID);
        Set<Integer> setOfItemsSerialID = store.getSetOfItemsSerialID();
        SelledItemInStore item;

        for(Integer itemSerialID : setOfItemsSerialID)
        {
            item = store.getItemySerialID(itemSerialID);

            System.out.println("a.Serial ID:" + itemSerialID);
            System.out.println("b.Name:" + item.getName());
            System.out.println("c.Type of buying:" + item.getTypeOfMeasureStr());
            System.out.println("d.Price per unit:" + item.getPricePerUnit());
            if(showItemsSoldDetails)
            {
                System.out.println("e.Total items that sold in store " + base.getHowManyTimesTheItemSoled(itemSerialID));
            }
            if(showOrdersDetails)
            {
                System.out.print("Orders that was done from this store:");

            }
            System.out.println("\n");
        }
    }

    public void showItemsInSystemAndPricesOfStore(Integer storeSerialID) {
        Store store = base.getStoreBySerialID(storeSerialID);
        Set<Integer> setOfItemsSerialID = base.getSetOfItemsSerialID();

        for(Integer itemSerialID : setOfItemsSerialID)
        {
            Item itemInSystem = base.getItemBySerialID(itemSerialID);
            boolean itemExistsInStore = store.checkIfItemIdExists(itemSerialID);

            System.out.println("a.Serial ID:" + itemSerialID);
            System.out.println("b.Name:" + itemInSystem.getName());
            System.out.println("c.Type of buying:" + itemInSystem.getTypeOfMeasureStr());
            if(itemExistsInStore)
            {
                SelledItemInStore itemInStore = store.getItemySerialID(itemSerialID);
                System.out.println("d.Price per unit:" + itemInStore.getPricePerUnit());
            }
            else
            {
                System.out.println("The store doesn't sell this item");
            }
            System.out.println("\n");
        }
    }
    public void showOrdersDetailsOfStore(Integer storeSerialID) {
        Store store = base.getStoreBySerialID(storeSerialID);
        List<ClosedOrder> listOfOrdersInStore = store.getListOfOrders();
        if(listOfOrdersInStore.isEmpty() == false)
        {
            for(ClosedOrder closedOrder : listOfOrdersInStore)
            {
                System.out.println("Orders that was done from this store:");
                System.out.println("a. Date:" + closedOrder.getDate().toString());
                System.out.println("a. Amount of items in order:" + closedOrder.getTotalAmountOfItemsByUnit());
                System.out.println("c. Total price of items:" + closedOrder.getTotalPriceOfItems());
                System.out.println("d. Delivery price:" + closedOrder.getDeliveryPriceAfterOrder());
                System.out.println("c. Total price of order:" + closedOrder.getTotalPriceOfOrder());
            }
        }
        else
        {
            System.out.println("There were no orders in this store");
        }

    }
    public void showSytemItemDetails() {
        Set<Integer> setOfItemsSerialID = base.getSetOfItemsSerialID();
        Item item;

        for (Integer itemSerialID : setOfItemsSerialID) {
            item = base.getItemySerialID(itemSerialID);
            System.out.println("1.Serial ID: " + itemSerialID);
            System.out.println("2.Name:" + item.getName());
            System.out.println("3.Type of buying:" + item.getTypeOfMeasureStr());
            System.out.println("4.How many shops selles the item: " + base.getHowManyShopsSellesAnItem(itemSerialID));
            System.out.println("5.Average price of item in Super Duper Market: " + base.getAvgPriceOfItemInSDK(itemSerialID));
            System.out.println("6.How many times the the item has been soled in Super Duper Market: " + base.getHowManyTimesTheItemSoled(itemSerialID));
            System.out.println("\n");
        }
    }

    public void showOrdersHistory()
    {
        Set<Integer> setOfOrders = base.getSetOfOrdersSerialID();
        ClosedOrder closedOrder;
        Store store;

        if(setOfOrders.isEmpty() == false)
        {
            for(Integer orderSerialId : setOfOrders)
            {
                closedOrder = base.getOrderBySerialID(orderSerialId);
                store = closedOrder.getShop();
                System.out.println("Orders that was done in Super Duper Market:");
                System.out.println("Serial ID of order: " + orderSerialId);
                //TODO
                //Complete date handling
               // System.out.println("2. Date: " + closedOrder.getDate().toString());
                System.out.println("   Details about the shop that order made from:");
                System.out.println("      Shop serial ID:" + store.getSerialNumber());
                System.out.println("      Shop name:" + store.getName());
                System.out.println("   Details about items in order:");
                System.out.println("      Total amount of type of items:" + closedOrder.getTotalAmountOfItemTypes());
                System.out.println("      Total amount of items:" + closedOrder.getTotalAmountOfItemsByUnit());
                System.out.println("   Delivery price: " + closedOrder.getDeliveryPriceAfterOrder());
                System.out.println("   Total order price: " + closedOrder.getTotalPriceOfOrder());
            }
        }
        else
        {
            System.out.println("There were no orders in this store");
        }
    }

    //TODO
    //To use after user added all his items to the order

    public void showItemsDetailsOfOpenedOrder(OpenedOrder openedOrder)
    {
        openedOrder.getOrderedItems().values().stream().forEach(DetailsPrinter::showItemDetailsOfOpenedOrder);
    }

    public static void showItemDetailsOfOpenedOrder(OrderedItem orderedItem)
    {
        System.out.println("Serial ID: " + orderedItem.getSerialNumber());
        if(orderedItem instanceof OrderedItemByWeight)
        {
            System.out.println("Type of measure: Weight");
        }
        else if(orderedItem instanceof OrderedItemByQuantity)
        {
            System.out.println("Type of measure: Quantity");
        }
        System.out.println("serial ID: " + orderedItem.getName());
        System.out.println("Price per unit: " + orderedItem.getPricePerUnit());
        if(orderedItem instanceof OrderedItemByWeight)
        {
            System.out.println("Amount of this item in the order: " + ((OrderedItemByWeight) orderedItem).getAmountOfItemOrderedByWeight());
        }
        else if(orderedItem instanceof OrderedItemByQuantity)
        {
            System.out.println("Amount of this item in the order: " + orderedItem.getAmountOfItemOrderedByUnits());
        }
        System.out.println("Total price of item in order: " + orderedItem.getTotalPriceOfItemOrderedByTypeOfMeasure());
    }

}
