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

    public void showStoreDetails(boolean showItemsInStore, boolean showOrderDetailsOfStore) {
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
        List<Order> listOfOrdersInStore = store.getListOfOrders();
        if(listOfOrdersInStore.isEmpty() == false)
        {
            for(Order order : listOfOrdersInStore)
            {
                System.out.println("Orders that was done from this store:");
                System.out.println("a. Date:" + order.getDate().toString());
                System.out.println("a. Amount of items in order:" + order.calcSumOfItems());
                System.out.println("c. Total price of items:" + order.calcTotalPriceOfItems());
                System.out.println("d. Delivery price:" + order.getDeliveryPriceAfterOrderIsDone());
                System.out.println("c. Total price of order:" + order.getTotalPriceOfOrderAfterItsDone());
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
        Set<Integer> setOfOrdersInStore = base.getSetOfOrdersSerialID();
        Order order;
        Store store;

        if(setOfOrdersInStore.isEmpty() == false)
        {
            for(Integer orderSerialId : setOfOrdersInStore)
            {
                order = base.getOrderBySerialID(orderSerialId);
                store = order.getShop();
                System.out.println("Orders that was done in Super Duper Market:");
                System.out.println("1. Serial ID of order: " + orderSerialId);
                System.out.println("2. Date: " + order.getDate().toString());
                System.out.println("3. Details about the shop that order made from:" + order.getShop().getSerialNumber());
                System.out.println("   Shop serial ID:" + store.getSerialNumber());
                System.out.println("   Shop name:" + store.getName());
                System.out.println("3. Details about items in order:" + order.getShop().getSerialNumber());
                System.out.println("   Total amount of type of items:" + order.calcSumAmountOfItemsType());
                System.out.println("   Total amount of items:" + order.calcSumOfItems());
                System.out.println("Delivery price: " + order.getDeliveryPriceAfterOrderIsDone());
                System.out.println("Total order price: " + order.getTotalPriceOfOrderAfterItsDone());
            }
        }
        else
        {
            System.out.println("There were no orders in this store");
        }
    }

    //TODO
    //To use after user added all his items to the order

    public void showItemsDetailsOfOrder()
    {

    }

}
