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

    private void showStoreDetails() {
        System.out.println("show store details");

    }

    private void showSelledItemDetailsOfStore(String storeSerialID) {
        Shop shop = base.getStoreBySerialID(storeSerialID);
        Set<String> setOfItemsSerialID = shop.getSetOfItemsSerialID();
        SelledItemInStore item;
        System.out.println("Serial number of Store: " + storeSerialID);
        System.out.println("Name of Store:" + shop.getName());
        System.out.println("List of items the Store sells:" + shop.getName());

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

    private void showSytemItemDetails() {
        System.out.println("read system items details");
    }

    private void orderAndBuy() {
        System.out.println("order and buy");
    }

    private void showOrdersHistory() {
        System.out.println("show orders history");
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