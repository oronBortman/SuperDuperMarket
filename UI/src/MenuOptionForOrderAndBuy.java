import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class MenuOptionForOrderAndBuy {

    private Logic base;
    private DetailsPrinter detailsPrinter;

    public MenuOptionForOrderAndBuy(Logic base) {
        this.base = base;
        detailsPrinter = new DetailsPrinter(base);
    }

    public void orderAndBuy() {
        Set<Integer> setOfItemsSerialID = base.getSetOfItemsSerialID();
        Date date = inputDate();
        int inputSerialIdOfShop = inputSerialIDOfShop();
        int coordinateX = inputCoordinate("x");
        int cooridnateY = inputCoordinate("y");
        SDMLocation locationOfUser = new SDMLocation(coordinateX, cooridnateY);
        Store store = base.getStoreBySerialID(inputSerialIdOfShop);
        OpenedOrder openedOrder = new OpenedOrder(store, date);
        inputItemsUntilQuitSign( store, openedOrder);
        //TODO
        //need to check if basket is empty and then exit and not completing the order?
        //need to show user details of order and close it
        detailsPrinter.showItemsDetailsOfOpenedOrder(openedOrder);
        System.out.println("Price Per Kilometer: " + store.getPPK());
        System.out.println("Air distance from store: " + locationOfUser.getAirDistanceToOtherLocation(store.getLocationOfShop()));
        System.out.println("Delivery price: " + openedOrder.calcDeliveryPrice(locationOfUser));
        if(inputIfUserApprovesOrder())
        {
            ClosedOrder closedOrder = openedOrder.closeOrder(locationOfUser);
            base.addClosedOrderToHistory(closedOrder);
            store.addClosedOrderToHistory(closedOrder);
        }
    }

    public boolean inputIfUserApprovesOrder()
    {
        System.out.println("Enter y to approve the other or any other key if you don't approve");
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        String choiceOfUser = sc.nextLine();
        return choiceOfUser.equals("y");
    }

    public Date inputDate()
    {
// To take the input
        Scanner scanner = new Scanner(System.in);
        boolean dateIsValid;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm-hh:mm");
        Date dateParsed=null;
        String inputDate;

        do{
            try {
                System.out.println("Enter the Date in the following format: dd/mm-hh:mm");
                inputDate = scanner.nextLine();
                //Parsing the String
                dateParsed = dateFormat.parse(inputDate);
                dateIsValid = true;
                System.out.println("The date is:" + dateFormat.format(dateParsed));
            } catch (ParseException e) {
                System.out.println("You enetered invalid date.");
                dateIsValid=false;
            }
        } while(dateIsValid == false);
        return dateParsed;
    }
    public void inputItemsUntilQuitSign(Store store, OpenedOrder openedOrder)
    {
        int storeSerialId = store.getSerialNumber();
        String choiceOfUser;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object

        do {
            detailsPrinter.showItemsInSystemAndPricesOfStore(storeSerialId);
            int inputSerialIdOfItem = inputItemSerialId(storeSerialId);
            SelledItemInStore selledItem = store.getItemBySerialID(inputSerialIdOfItem);
            String nameOfItem = selledItem.getName();
            int priceOfItem = selledItem.getPricePerUnit();
            Item.TypeOfMeasure typeOfMeasure = base.getItemBySerialID(inputSerialIdOfItem).getTypeOfMeasure();

            switch (typeOfMeasure) {
                case Quantity:
                    int quantityOfItem = inputQuantityOfItemToBuy(inputSerialIdOfItem);
                    if(openedOrder.checkIfItemAlreadyExistsInOrder(inputSerialIdOfItem))
                    {
                        OrderedItemByQuantity orderedItemByQuantity = (OrderedItemByQuantity)openedOrder.getItemInOrder(inputSerialIdOfItem);
                        orderedItemByQuantity.increaseAmountOfItemOrderedByUnits(quantityOfItem);
                    }
                    else
                    {
                        OrderedItemByQuantity orderedItemByQuantity = new OrderedItemByQuantity(inputSerialIdOfItem, nameOfItem,priceOfItem, quantityOfItem);
                        openedOrder.addItemToItemsMapOfOrder(orderedItemByQuantity);
                    }
                    break;
                case Weight:
                    double weightOfItem = inputWeightOfItemToBuy(inputSerialIdOfItem);
                    if(openedOrder.checkIfItemAlreadyExistsInOrder(inputSerialIdOfItem))
                    {
                        OrderedItemByWeight orderedItemByWeight = (OrderedItemByWeight)openedOrder.getItemInOrder(inputSerialIdOfItem);
                        orderedItemByWeight.increaseAmountOfItemOrderedByWeight(weightOfItem);
                    }
                    else
                    {
                        OrderedItemByWeight orderedItemByWeight = new OrderedItemByWeight(inputSerialIdOfItem, nameOfItem,priceOfItem, weightOfItem);
                        openedOrder.addItemToItemsMapOfOrder(orderedItemByWeight);
                    }

                    break;
                default:
                    break;
            }
            System.out.println("Enter q if you want finish the order");
            choiceOfUser = sc.nextLine();
        } while(choiceOfUser.equals("q") == false);
    }

    public int inputSerialIDOfShop() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfSerialId = 0;

        do {
            detailsPrinter.showStoresDetails(false, false);
            System.out.println("Please enter the serial id of the shop use want to buy from");
            if (sc.hasNextInt()) {
                inputOfSerialId = sc.nextInt();
                if (base.checkIfStoreExists(inputOfSerialId)) {
                    goodChoice = true;
                } else {
                    System.out.println("Store doesn't exist. Please enter the serial id of the store again.");
                }
            }
            else
            {
                System.out.println("You didn't entered a number!");
                sc.next();
            }
        }
        while (goodChoice == false);
        return inputOfSerialId;
    }

    public int inputItemSerialId(int storeSerialID)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfItemSerialId = 0;
        Store store = base.getStoreBySerialID(storeSerialID);

        do {
            detailsPrinter.showItemsInSystemAndPricesOfStore(storeSerialID);
            System.out.println("Please enter the serial id of the item to buy from");
            if (sc.hasNextInt()) {
                inputOfItemSerialId = sc.nextInt();

                if (store.checkIfItemIdExists(inputOfItemSerialId)){
                    goodChoice = true;
                } else {
                    System.out.println("The item you entered isn't exist in store. Please choose another item");
                }
            }
            else
            {
                sc.next();
                System.out.println("You didn't entered a number!");
            }
        }
        while (goodChoice == false);
        return inputOfItemSerialId;
    }

    public int inputQuantityOfItemToBuy(int itemSerialID)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int quantityOfItemToBuy=0;
        do {
            System.out.println("Please enter the quantity that you want to buy from item.");
            if (sc.hasNextInt()) {
                goodChoice = true;
                quantityOfItemToBuy= sc.nextInt();
            }
            else{
                sc.next();
                System.out.println("You didn't entered a number!");
            }
        }
        while (goodChoice == false);
        return quantityOfItemToBuy;
    }
    public float inputWeightOfItemToBuy(int itemSerialID)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        float weightOfItemToBuy=0;
        do {
            System.out.println("Please enter the weight that you want to buy from item. Enter a dot number");
            if (sc.hasNextFloat()) {
                goodChoice = true;
                weightOfItemToBuy= sc.nextFloat();
             }
            else{
                sc.next();
                System.out.println("You didn't entered a number!");
            }
        }
        while (goodChoice == false);
        return weightOfItemToBuy;
    }

    public int inputCoordinate(String nameOfCoordinate)
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfCoordinate = 0;
        do {
            System.out.println("Please enter your " + nameOfCoordinate + " coordinate");
            if (sc.hasNextInt()) {

                inputOfCoordinate = sc.nextInt();

                if (SDMLocation.checkIfLocationCoordinatesIsValid(inputOfCoordinate)) {
                    goodChoice = true;
                }
                else
                {
                    System.out.println("The location is not valid! try again");
                    sc.next();
                }
            }
            else {
                sc.next();
                System.out.println("You didn't entered a number!");
            }
        }
        while (goodChoice == false);
        return inputOfCoordinate;
    }

    public SDMLocation inputField()
    {
        int cooridnateX = inputCoordinate("x");
        int cooridnateY = inputCoordinate("y");
        return new SDMLocation(cooridnateX, cooridnateY);
    }


}

