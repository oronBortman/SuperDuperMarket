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
        System.out.println("show shop general details\n");
        Set<Integer> setOfShopSerial = base.getSetOfStoresSerialID();
        Store store;
        for(Integer shopSerialID : setOfShopSerial)
        {
            store = base.getStoreBySerialID(shopSerialID);

            System.out.println("Serial number of Store: " + shopSerialID);
            System.out.println("Name of Store:" + store.getName());
            System.out.println("PPK: " + store.getPPK());
            if(showItemsInStore)
            {
                System.out.println("List of items the Store sells:");
                System.out.println("\n");
                showSelledItemsDetailsOfStore(shopSerialID, true);
            }
            if(showOrderDetailsOfStore)
            {
                showOrdersDetailsOfStore(shopSerialID);
                if(store.getSetOfOrdersSerialID().isEmpty() == false)
                {
                    System.out.println("Total payment of delivers till now:" + store.calcProfitOfDelivers());
                }
                else
                {
                    System.out.println("There were no delivers because there were no orders");
                }
            }
            System.out.println("//--------------------------------------------------------------------------------//\n");
        }

    }

    public void showSelledItemsDetailsOfStore(Integer storeSerialID, boolean showItemsSoldDetails) {
        Store store = base.getStoreBySerialID(storeSerialID);
        Set<Integer> setOfItemsSerialID = store.getSetOfItemsSerialID();
        SelledItemInStore item;

        for(Integer itemSerialID : setOfItemsSerialID)
        {
            item = store.getItemBySerialID(itemSerialID);
            System.out.println("    Name:" + item.getName());
            System.out.println("    Serial ID:" + itemSerialID);
            System.out.println("    Type of buying:" + item.getTypeOfMeasureStr());
            System.out.println("    Price per unit:" + item.getPricePerUnit());
            if(showItemsSoldDetails)
            {
                System.out.println("    Total items that sold in store: " + store.getAmountOfItemSoled(itemSerialID));
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
                SelledItemInStore itemInStore = store.getItemBySerialID(itemSerialID);
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
        Set<Integer> setOfSerialIDOfOrdersInStore = store.getSetOfOrdersSerialID();
        if(setOfSerialIDOfOrdersInStore.isEmpty() == false)
        {
            System.out.println("Orders that was done from this store:");
            for(int serialIdOfOrder : setOfSerialIDOfOrdersInStore)
            {
                ClosedOrder closedOrder = store.getOrderBySerialID(serialIdOfOrder);
                System.out.println("Serial key of order: " + serialIdOfOrder);
                //TODO
                //System.out.println("a. Date:" + closedOrder.getDate().toString());
                System.out.println("Amount of items in order:" + closedOrder.getTotalAmountOfItemsByUnit());
                System.out.println("Total price of items:" + closedOrder.getTotalPriceOfItems());
                System.out.println("Delivery price:" + closedOrder.getDeliveryPriceAfterOrder());
                System.out.println("Total price of order:" + closedOrder.getTotalPriceOfOrder());
                System.out.println("--------------------------------------------------------");
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
        System.out.println("\n");
    }

}
