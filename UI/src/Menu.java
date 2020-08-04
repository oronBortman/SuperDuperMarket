import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Menu {

    enum mainMenuOptions
    {
        READ_FROM_XML_FILE(1, "Read from XML file"),
        SHOW_STORE_DETAILS(2, "Show store details"),
        SHOW_SYSTEM_ITEM_DETAILS(3, "Show system items details"),
        ORDER_AND_BUY(4, "Order and buy"),
        SHOW_ORDERS_HISTORY(5, "Show orders history"),
        EXIT(6, "Exit");

        int optionNum;
        String meaning;

        mainMenuOptions(int optionNum, String meaning)
        {
            this.optionNum = optionNum;
            this.meaning = meaning;
        }

        public int getOptionNum() {
            return optionNum;
        }

        public String getMeaning() {
            return meaning;
        }

        public static mainMenuOptions convertOptionNumToEnum(int choiceNum)
        {
            for(mainMenuOptions option : mainMenuOptions.values())
            {
                if(option.getOptionNum() == choiceNum)
                {
                    return option;
                }
            }
            return null;
        }

        public void printOption()
        {
            System.out.println(optionNum + "." + meaning);
        }

        public static boolean containsChoiceNum(int choiceNum)
        {
            boolean choiceNumExist = false;
            for(mainMenuOptions option : mainMenuOptions.values())
            {
                if(option.getOptionNum() == choiceNum)
                {
                    choiceNumExist = true;
                }
            }
            return choiceNumExist;
        }

        public Boolean numberEqualsToOptionNum(int choiceNum)
        {
            return optionNum == choiceNum;
        }
    }

    SDKBase base = new SDKBase();

    private void readFromXMLFile() {
        System.out.println("read from XML file");
    }

    //TODO
    //To use after user added all his items to the order

    private void showItemsDetailsOfOrder()
    {

    }

    private void showStoreDetails() {
        System.out.println("show shop details");
        Set<String> setOfShopSerial = base.getSetOfStoresSerialID();
        Shop shop;
        for(String shopSerialID : setOfShopSerial)
        {
            shop = base.getStoreBySerialID(shopSerialID);

            System.out.println("Serial number of Store: " + shopSerialID);
            System.out.println("Name of Store:" + shop.getName());
            System.out.println("List of items the Store sells:" + shop.getName());
            showSelledItemsDetailsOfStore(shopSerialID);
            showOrdersDetailsOfStore(shopSerialID);
            System.out.println("PPK: " + shop.getPPK());
            System.out.println("Total payment of delivers till now:" + shop.calcProfitOfDelivers());
        }

    }

    private void showSelledItemsDetailsOfStore(String storeSerialID) {
        Shop shop = base.getStoreBySerialID(storeSerialID);
        Set<String> setOfItemsSerialID = shop.getSetOfItemsSerialID();
        SelledItemInStore item;


        for(String itemSerialID : setOfItemsSerialID)
        {
            item = shop.getItemySerialID(itemSerialID);

            System.out.println("a. Serial IDL" + itemSerialID);
            System.out.println("a. Name:" + item.getName());
            System.out.println("Type of buying:" + item.getTypeOfMeasure());
            System.out.println("Price per unit:" + item.getPricePerUnit());
            //TODO
            System.out.println("Total items that sold in store");
            System.out.print("Orders that was done from this store:");
            //If there orders

        }
    }

    private void showOrdersDetailsOfStore(String storeSerialID) {
        Shop shop = base.getStoreBySerialID(storeSerialID);
        List<Order> listOfOrdersInStore = shop.getListOfOrders();
        if(listOfOrdersInStore.isEmpty() == false)
        {
            for(Order order : listOfOrdersInStore)
            {
                System.out.println("Orders that was done from this store:");
                System.out.println("a. Date:" + order.getDate().toString());
                System.out.println("a. Amount of items in order:" + order.calcSumOfItems());
                System.out.println("c. Total price of items:" + order.calcTotalPriceOfItems());
                System.out.println("d. Delivery price:" + order.calcDeliveryPrice());
                System.out.println("c. Total price of order:" + order.calcTotalPriceOfOrder());
            }
        }
        else
        {
            System.out.println("There were no orders in this store");
        }

    }
    private void showSytemItemDetails() {
        Set<String> setOfItemsSerialID = base.getSetOfItemsSerialID();
        SDKItem item;

        for (String itemSerialID : setOfItemsSerialID) {
            item = base.getItemySerialID(itemSerialID);
            System.out.println("1.Serial IDL" + itemSerialID);
            System.out.println("2.Name:" + item.getName());
            System.out.println("3.Type of buying:" + item.getTypeOfMeasure());
            System.out.println("4.How many shops selles the item: " + base.getHowManyShopsSellesAnItem(itemSerialID));
            System.out.println("5.Average price of item in Super Duper Market: " + base.getAvgPriceOfItemInSDK(itemSerialID));
            System.out.println("6.How many times the the item has been soled in Super Duper Market: " + base.getHowManyTimesTheItemSoled(itemSerialID));
        }
    }
    private void orderAndBuy() {
        Set<String> setOfItemsSerialID = base.getSetOfItemsSerialID();
        SDKItem item;

        for (String itemSerialID : setOfItemsSerialID) {
            item = base.getItemySerialID(itemSerialID);
            System.out.println("1.Serial IDL" + itemSerialID);
            System.out.println("2.Name:" + item.getName());
            System.out.println("3.Type of buying:" + item.getTypeOfMeasure());
            System.out.println("4.How many shops selles the item: " + base.getHowManyShopsSellesAnItem(itemSerialID));
            System.out.println("5.Average price of item in Super Duper Market: " + base.getAvgPriceOfItemInSDK(itemSerialID));
            System.out.println("6.How many times the the item has been soled in Super Duper Market: " + base.getHowManyTimesTheItemSoled(itemSerialID));
        }
    }

    private void showOrdersHistory()
    {
        Set<Integer> setOfOrdersInStore = base.getSetOfOrdersSerialID();
        Order order;
        Shop shop;

        if(setOfOrdersInStore.isEmpty() == false)
        {
            for(Integer orderSerialId : setOfOrdersInStore)
            {
                order = base.getOrderBySerialID(orderSerialId);
                shop = order.getShop();
                System.out.println("Orders that was done in Super Duper Market:");
                System.out.println("1. Serial ID of order: " + orderSerialId);
                System.out.println("2. Date: " + order.getDate().toString());
                System.out.println("3. Details about the shop that order made from:" + order.getShop().getSerialNumber());
                System.out.println("   Shop serial ID:" + shop.getSerialNumber());
                System.out.println("   Shop name:" + shop.getName());
                System.out.println("3. Details about items in order:" + order.getShop().getSerialNumber());
                System.out.println("   Total amount of type of items:" + order.calcSumAmountOfItemsType());
                System.out.println("   Total amount of items:" + order.calcSumOfItems());
                System.out.println("Delivery price: " + order.calcDeliveryPrice());
                System.out.println("Total order price: " + order.calcTotalPriceOfOrder());
            }
        }
        else
        {
            System.out.println("There were no orders in this store");
        }
    }


    private void printHeadline() {
        System.out.println("Welcome to Super Duper Market");
    }

    private void printMainMenu() {
        System.out.println("Choose your option by entering the number of option and after it press enter:");
        System.out.println("For example: For choosing the option \"Read from XML File\" enter the number 1 and after it press enter");
        mainMenuOptions.READ_FROM_XML_FILE.printOption();
        mainMenuOptions.SHOW_STORE_DETAILS.printOption();
        mainMenuOptions.SHOW_SYSTEM_ITEM_DETAILS.printOption();
        mainMenuOptions.ORDER_AND_BUY.printOption();
        mainMenuOptions.SHOW_ORDERS_HISTORY.printOption();
        mainMenuOptions.EXIT.printOption();
    }

    public void chooseOptionFromMainMenuUntilExit() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        Integer userChoice;
        boolean goodChoice;
        boolean exitChosen = false;

        printHeadline();
        do {
            printMainMenu();
            if (sc.hasNextInt()) {
                userChoice = sc.nextInt();
                if (mainMenuOptions.containsChoiceNum(userChoice) && mainMenuOptions.EXIT.numberEqualsToOptionNum(userChoice) == false) {
                    chooseOptionsFromMenu(userChoice);
                    goodChoice = true;
                } else if (mainMenuOptions.EXIT.numberEqualsToOptionNum(userChoice)) {
                    System.out.println("Thank you for using Super Duper Market and goodbye");
                    goodChoice = true;
                    exitChosen = true;
                } else {
                    goodChoice = false;
                    System.out.println("You didn't entered a valid number. Please try again");
                }
            } else {
                sc.next();
                System.out.println("You didn't entered a number!");
                goodChoice = false;
            }

        }
        while ((exitChosen == false) || (goodChoice == false));
    }


    private void chooseOptionsFromMenu(int userChoice) {
        mainMenuOptions option = mainMenuOptions.convertOptionNumToEnum(userChoice);
        switch(option)
        {
            case READ_FROM_XML_FILE:
                readFromXMLFile();
                break;
            case SHOW_STORE_DETAILS:
                showStoreDetails();
                break;
            case SHOW_SYSTEM_ITEM_DETAILS:
                showSytemItemDetails();
                break;
            case ORDER_AND_BUY:
                //TODO
                orderAndBuy();
                break;
            case SHOW_ORDERS_HISTORY:
                showOrdersHistory();
                break;
            default:
                break;
        }
    }
}