package InterfaceConsole;

import logic.*;

import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class StaticOrderMenu extends OrderMenu{

    private BusinessLogic businessLogic;
    private DetailsPrinter detailsPrinter;

    public StaticOrderMenu(BusinessLogic businessLogic)
    {
        this.businessLogic = businessLogic;
        detailsPrinter = new DetailsPrinter(businessLogic);
    }

    public void makeStaticOrder() {
        Set<Integer> setOfItemsSerialID = businessLogic.getSetOfItemsSerialID();
        Date date = inputDate();
        int inputSerialIdOfShop = inputSerialIDOfShop();
        Store store = businessLogic.getStoreBySerialID(inputSerialIdOfShop);
        SDMLocation locationOfUser = inputLocation(store.getLocationOfShop());
        if(locationOfUser == null) { return;} //Exit from OrderAndBuy if the location of user is invalid
        OpenedStaticOrder openedOrder = new OpenedStaticOrder(store, date);
        inputItemsUntilQuitSign( store, openedOrder);
        //TODO
        //need to check if basket is empty and then exit and not completing the order?
        //need to show user details of order and close it
        printSumDetailsOfOrder(openedOrder, store.getPPK(), locationOfUser, store.getLocationOfShop());
        if(inputIfUserApprovesOrder())
        {
            ClosedOrder closedOrder = openedOrder.closeOrder(locationOfUser);
            businessLogic.addClosedOrderToHistory(closedOrder);
            store.addClosedOrderToHistory(closedOrder);
        }
    }

     public void inputItemsUntilQuitSign(Store store, OpenedStaticOrder openedOrder)
    {
        int storeSerialId = store.getSerialNumber();
        String choiceOfUser;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object

        do {
            detailsPrinter.showItemsInSystemAndPricesOfStore(storeSerialId);
            int inputSerialIdOfItem = inputItemSerialId(storeSerialId);
            AvailableItemInStore selledItem = store.getItemBySerialID(inputSerialIdOfItem);
            String nameOfItem = selledItem.getName();
            int priceOfItem = selledItem.getPricePerUnit();
            Item.TypeOfMeasure typeOfMeasure = businessLogic.getItemBySerialID(inputSerialIdOfItem).getTypeOfMeasure();

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
            System.out.println("Enter q if you want finish or enter any other key to continue with the order");
            choiceOfUser = sc.nextLine();
        } while(choiceOfUser.toLowerCase().equals("q") == false);
    }

    public int inputSerialIDOfShop() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfSerialId = 0;

        do {
            detailsPrinter.showStoresDetailsAndFilterByParams(false, false);
            System.out.println("Please enter the serial id of the shop use want to buy from");
            if (sc.hasNextInt()) {
                inputOfSerialId = sc.nextInt();
                if (businessLogic.checkIfStoreExists(inputOfSerialId)) {
                    goodChoice = true;
                } else {
                    System.out.println("logic.Store doesn't exist. Please enter the serial id of the store again.");
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
        Store store = businessLogic.getStoreBySerialID(storeSerialID);

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



    public void printSumDetailsOfOrder(OpenedStaticOrder openedOrder, int PPK, SDMLocation locationOfUser, SDMLocation locationOfShop) {
        detailsPrinter.showItemsDetailsOfOpenedOrder(openedOrder);
        System.out.println("Price Per Kilometer: " + PPK);
        System.out.println("Air distance from store: " + MainMenu.convertDoubleToDecimal(locationOfUser.getAirDistanceToOtherLocation(locationOfShop)));
        System.out.println("Delivery price: " + MainMenu.convertDoubleToDecimal(openedOrder.calcTotalDeliveryPrice(locationOfUser)));
    }

    public SDMLocation inputLocation(SDMLocation storeLocation)
    {
        int coordinateX;
        int cooridnateY;
        boolean locationIsValid;
        System.out.println("Enter your location:");
        coordinateX = inputCoordinate("x");
        cooridnateY = inputCoordinate("y");
        locationIsValid = storeLocation.checkIfCoordinatesMatchToLocation(coordinateX, cooridnateY) == false;
        if(locationIsValid == false) {
            System.out.println("You entered the same location of the store.\n" +
                    "The store is in (" + storeLocation.getX() + "," + storeLocation.getY() + "), " +
                    "and you entered the location (" + coordinateX + "," + cooridnateY + ").\n"  +
                    "You can't order from a store at your location.\n");
            return null;
        }
        else
        {
            return new SDMLocation(coordinateX, cooridnateY);
        }
    }

}