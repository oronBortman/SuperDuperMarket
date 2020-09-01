package InterfaceConsole;

import logic.BusinessLogic;

import java.util.Scanner;

public class UpdatingProductsOfStoreMenu {
    private BusinessLogic businessLogic;
    private DetailsPrinter detailsPrinter;

    enum UpdatingProductsOptions {
        REMOVE_ITEM(1, "Remove item from store"),
        ADD_ITEM(2, "Add item to store"),
        UPDATING_PRICE(3, "Update price of item in store");


        int optionNum;
        String meaning;

        UpdatingProductsOptions(int optionNum, String meaning) {
            this.optionNum = optionNum;
            this.meaning = meaning;
        }

        public int getOptionNum() {
            return optionNum;
        }

        public String getMeaning() {
            return meaning;
        }

        public static UpdatingProductsOptions convertOptionNumToEnum(int choiceNum) {
            for (UpdatingProductsOptions option : UpdatingProductsOptions.values()) {
                if (option.getOptionNum() == choiceNum) {
                    return option;
                }
            }
            return null;
        }

        public void printOption() {
            System.out.println(optionNum + "." + meaning);
        }

        public static boolean containsChoiceNum(int choiceNum) {
            boolean choiceNumExist = false;
            for (UpdatingProductsOptions option : UpdatingProductsOptions.values()) {
                if (option.getOptionNum() == choiceNum) {
                    choiceNumExist = true;
                }
            }
            return choiceNumExist;
        }

        public Boolean numberEqualsToOptionNum(int choiceNum) {
            return optionNum == choiceNum;
        }
    }

    public UpdatingProductsOfStoreMenu(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        detailsPrinter = new DetailsPrinter(businessLogic);
    }

    public void printUpdatingProductsOfStoreMenu() {
        System.out.println("Choose your option by entering the number of option and after it press enter:");
        System.out.println("For example: For choosing the option \"" + UpdatingProductsOptions.REMOVE_ITEM.getMeaning() + "\" enter the number 1 and after it press enter");
        UpdatingProductsOptions.REMOVE_ITEM.printOption();
        UpdatingProductsOptions.ADD_ITEM.printOption();
        UpdatingProductsOptions.UPDATING_PRICE.printOption();

    }

    public void updatingProductsOfStore() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        int storeID = inputSerialIDOfStore();
        printUpdatingProductsOfStoreMenu();
        int userChoice;
        boolean goodChoice = false;
        do
        {
            if (sc.hasNextInt()) {
                userChoice = sc.nextInt();
                if (UpdatingProductsOptions.containsChoiceNum(userChoice)) {
                    if (UpdatingProductsOptions.REMOVE_ITEM.numberEqualsToOptionNum(userChoice)) {
                        goodChoice=true;
                        removeItemFromStore(storeID);
                    } else if (UpdatingProductsOptions.ADD_ITEM.numberEqualsToOptionNum(userChoice)) {
                        goodChoice=true;
                        addItemToStore(storeID);
                    } else if (UpdatingProductsOptions.UPDATING_PRICE.numberEqualsToOptionNum(userChoice)){
                        goodChoice=true;
                        updatePriceOfItemInStore(storeID);
                    }
                } else {
                    goodChoice=false;
                    System.out.println("You didn't entered a valid number. Please try again");
                }
            }
            else{
                goodChoice=false;
                System.out.println("You didn't entered a number!");
                sc.next();

            }
        } while(goodChoice == false);

    }

    public void removeItemFromStore(int storeID)
    {
        int itemID;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        boolean skip=false;
        int inputOfSerialId = 0;

        do {
            System.out.println("Details of the items in store:");
            detailsPrinter.showSelledItemsDetailsOfStoreAndFilterByParams(storeID,false);
            System.out.println("Enter the id of the item you want to delete from the store");

            if (sc.hasNextInt()) {
                itemID = sc.nextInt();
                if(!businessLogic.getStoreBySerialID(storeID).checkIfItemIdExists(itemID))
                {
                    goodChoice=false;
                    System.out.println("The store doesn't sells the item, therefore you can't remove the item from the store");
                }
                else if(businessLogic.getStoreBySerialID(storeID).checkIfItemIsTheOnlyOneInStore(itemID))
                {
                    goodChoice=false;
                    skip=true;
                    System.out.println("This is the only item in the store, therefore you can't remove it from the store");
                }
                else if(businessLogic.checkIfOnlyCertainStoreSellesItem(storeID,itemID))
                {
                    goodChoice=false;
                    System.out.println("Only this store sells the item, therefore you can't remove the item from the store");
                }
                else
                {
                    businessLogic.removeItemFromStore(storeID,itemID);
                    System.out.println("logic.Item removed successfully from store :)");
                    goodChoice=true;
                }
            }
            else {
                System.out.println("You didn't ente1red a number!");
                goodChoice=false;
                sc.next();
            }
        }
        while (goodChoice == false && skip == false);
    }

    public void addItemToStore(int storeID)
    {
        int itemID;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        boolean skip=false;
        do
        {
            System.out.println("Details of the items in Super Duper Market:");
            detailsPrinter.showSytemItemDetails();
            System.out.println("Enter the id of the item you want to add to the store");
            if (sc.hasNextInt()) {
                itemID = sc.nextInt();
                if(!businessLogic.checkIfItemIdExists(itemID))
                {
                    goodChoice=false;
                    System.out.println("The item doesn't exist in Super Duper Market, therefore you can't add the item with this serial id to the store");
                }
                else if(businessLogic.checkIfItemExistsInStore(storeID, itemID))
                {
                    goodChoice=false;
                    skip=true;
                    System.out.println("The item already defined in store, therefore you can't add the item to the store");
                }
                else
                {
                    goodChoice = askForPriceAndAddItemToStore(storeID, itemID);
                }
            }
            else {
                System.out.println("You didn't entered a number!");
                goodChoice=false;
                sc.next();
            }
        } while (goodChoice == false && skip==false);

    }

    public boolean askForPriceAndAddItemToStore(int storeID, int itemID)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        int price;
        boolean goodChoice=false;
        do
        {
            System.out.println("Enter price of item");
            if (sc.hasNextInt()) {
                price = sc.nextInt();
                if(price<=0)
                {
                    goodChoice=false;
                    System.out.println("price must be a positive number");
                }
                else
                {
                    goodChoice=true;
                    businessLogic.addItemToStore(storeID,itemID, price);
                    System.out.println("logic.Item was added successfully to the store :)");
                }
            }
            else {
                goodChoice=false;
                System.out.println("You didn't entered a number!");
                sc.next();
            }
        }
        while(goodChoice == false);
        return goodChoice;
    }

    public boolean askForPriceAndUpdateItemToStore(int storeID, int itemID)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        int price;
        boolean goodChoice=false;
        do
        {
            System.out.println("Enter price of item");
            if (sc.hasNextInt()) {
                price = sc.nextInt();
                if(price<=0)
                {
                    goodChoice=false;
                    System.out.println("price must be a positive number");
                }
                else
                {
                    goodChoice=true;
                    businessLogic.updatePriceOfItemInStore(storeID,itemID, price);
                    System.out.println("Price of item updated successfully :)");
                }
            }
            else {
                goodChoice=false;
                System.out.println("You didn't entered a number!");
                sc.next();
            }
            return goodChoice;
        }
        while(goodChoice == false);
    }

    public void updatePriceOfItemInStore(int storeID)
    {
        int itemID;
        boolean goodChoice=false;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object

        do {
            System.out.println("Details of the item in store:");
            detailsPrinter.showSelledItemsDetailsOfStoreAndFilterByParams(storeID,false);
            System.out.println("Enter the id of the item you want to update");
            if (sc.hasNextInt()) {
                itemID = sc.nextInt();
                if(!businessLogic.checkIfItemExistsInStore(storeID,itemID))
                {
                    goodChoice=false;
                    System.out.println("The item doesn't exist in store, therefore you can't update it's price");
                }
                else
                {
                    goodChoice = askForPriceAndUpdateItemToStore(storeID, itemID);
                }
            }
            else {
                goodChoice=false;
                System.out.println("You didn't entered a number!");
                sc.next();
            }
        }
        while(goodChoice == false);
    }


    public int inputSerialIDOfStore() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfSerialId = 0;

        do {
            detailsPrinter.showStoresDetailsAndFilterByParams(false, false);
            System.out.println("Please enter the serial id of the store you want to update");
            if (sc.hasNextInt()) {
                inputOfSerialId = sc.nextInt();
                if (businessLogic.checkIfStoreExists(inputOfSerialId)) {
                    goodChoice = true;
                } else {
                    goodChoice=false;
                    System.out.println("logic.Store doesn't exist. Please enter the serial id of the store again.");
                }
            }
            else
            {
                goodChoice=false;
                System.out.println("You didn't entered a number!");
                sc.next();
            }
        }
        while (goodChoice == false);
        return inputOfSerialId;
    }

}


