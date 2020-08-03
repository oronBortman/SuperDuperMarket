import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Scanner;
import java.util.Set;

public class Menu {

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
        System.out.println("1.Read from XML File");
        System.out.println("2.Show store detials");
        System.out.println("3.Show system item details");
        System.out.println("4.Order and buy");
        System.out.println("5.Show orders history");
        System.out.println("6.Exit");
    }

    public void chooseOptionFromMainMenuUntilExit() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        Integer userChoice;
        boolean goodChoice;
        boolean exitChosen = false;
        int firstOptionNum = 1;
        int lastOptionNum = 6;

        printHeadline();
        do {
            printMainMenu();
            if (sc.hasNextInt()) {
                userChoice = sc.nextInt();
                if (userChoice >= firstOptionNum && userChoice < lastOptionNum) {
                    chooseOptionsFromMenu(userChoice);
                    goodChoice = true;
                } else if (userChoice == lastOptionNum) {
                    System.out.println("Thank you for using Super Duper Market and goodbye");
                    goodChoice = true;
                    exitChosen = true;
                } else {
                    goodChoice = false;
                    System.out.println("You didn't entered a number between " + firstOptionNum + " to " + lastOptionNum);
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
        if (userChoice == 1) {
            readFromXMLFile();

        } else if (userChoice == 2) {
            showStoreDetails();

        } else if (userChoice == 3) {
            showSytemItemDetails();

        } else if (userChoice == 4) {
            orderAndBuy();

        } else if (userChoice == 5) {
            showOrdersHistory();
        }
    }
}