import java.util.*;

public class DynamicOrderMenu extends OrderMenu{
    private Base base;
    DetailsPrinter detailsPrinter;
    public DynamicOrderMenu(Base base)
    {
        this.base=base;
        detailsPrinter = new DetailsPrinter(base);
    }

    public SDMLocation inputLocation()
    {
        int coordinateX;
        int cooridnateY;
        boolean locationIsValid;
        System.out.println("Enter your location:");
        coordinateX = inputCoordinate("x");
        cooridnateY = inputCoordinate("y");
        SDMLocation location = new SDMLocation(coordinateX, cooridnateY);
        locationIsValid = base.checkIfLocationIsAsStores(location) == false;
        if(locationIsValid == false) {
            System.out.println("You entered the same location of one of the stores.\n");
            return null;
        }
        else
        {
            return location;
        }
    }

    public void makeDynamicOrder() {
        Set<Integer> setOfItemsSerialID = base.getSetOfItemsSerialID();
        Date date = inputDate();
        SDMLocation locationOfUser = inputLocation();
        if(locationOfUser == null) { return;} //Exit from OrderAndBuy if the location of user is invalid
        OpenedDynamicOrder openedOrder = new OpenedDynamicOrder(date);
        inputItemsUntilQuitSign(openedOrder);
        Set <OrderedItem> orderedItems= new HashSet<OrderedItem>(openedOrder.getOrderedItems().values());
        HashMap<Store, List<OrderedItem>> mapOfStoresIDWithSelectedItems = base.getMapOfShopWithCheapestItemsFromSet(orderedItems);
        openedOrder.setMapOfStoresIDWithSelectedItems(mapOfStoresIDWithSelectedItems);
        openedOrder.updateItemsByCheapest();
        //TODO
        //need to check if basket is empty and then exit and not completing the order?
        //need to show user details of order and close it
        printSumDetailsOfOrder(openedOrder, locationOfUser);
        if(inputIfUserApprovesOrder())
        {
            ClosedDynamicOrder closedDynamicOrder = openedOrder.closeOrder(locationOfUser);
            base.addClosedOrderToHistory(closedDynamicOrder);
        }
    }

    public void printSumDetailsOfOrder(OpenedDynamicOrder openedOrder, SDMLocation locationOfUser) {
        detailsPrinter.showItemsDetailsOfOpenedOrder(openedOrder);
        System.out.println("Delivery price: " + MainMenu.convertDoubleToDecimal(openedOrder.calcTotalDeliveryPrice(locationOfUser)));
    }


    public int inputItemSerialId()
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice = false;
        int inputOfItemSerialId = 0;

        do {
            detailsPrinter.showSytemItemDetails();
            System.out.println("Please enter the serial id of the item to buy from");
            if (sc.hasNextInt()) {
                inputOfItemSerialId = sc.nextInt();

                if (base.checkIfItemIdExists(inputOfItemSerialId)){
                    goodChoice = true;
                } else {
                    System.out.println("The item you entered isn't exist in system. Please choose another item");
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

    public void inputItemsUntilQuitSign(OpenedDynamicOrder openedOrder)
    {
        String choiceOfUser;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object

        do {
            detailsPrinter.showSytemItemDetails();
            int inputSerialIdOfItem = inputItemSerialId();
            Item.TypeOfMeasure typeOfMeasure = base.getItemBySerialID(inputSerialIdOfItem).getTypeOfMeasure();
            String nameOfItem = base.getItemBySerialID(inputSerialIdOfItem).getName();
            switch (typeOfMeasure) {
                case Quantity:
                    int quantityOfItem = inputQuantityOfItemToBuy(inputSerialIdOfItem);
                    if(openedOrder.checkIfItemExistsInOrder(inputSerialIdOfItem))
                    {
                        OrderedItemByQuantity orderedItemByQuantity = (OrderedItemByQuantity)openedOrder.getItemInOrder(inputSerialIdOfItem);
                        orderedItemByQuantity.increaseAmountOfItemOrderedByUnits(quantityOfItem);
                    }
                    else
                    {
                        OrderedItemByQuantity orderedItemByQuantity = new OrderedItemByQuantity(inputSerialIdOfItem, nameOfItem, quantityOfItem);
                        openedOrder.addItemToItemsMapOfOrder(orderedItemByQuantity);
                    }
                    break;
                case Weight:
                    double weightOfItem = inputWeightOfItemToBuy(inputSerialIdOfItem);
                    if(openedOrder.checkIfItemExistsInOrder(inputSerialIdOfItem))
                    {
                        OrderedItemByWeight orderedItemByWeight = (OrderedItemByWeight)openedOrder.getItemInOrder(inputSerialIdOfItem);
                        orderedItemByWeight.increaseAmountOfItemOrderedByWeight(weightOfItem);
                    }
                    else
                    {
                        OrderedItemByWeight orderedItemByWeight = new OrderedItemByWeight(inputSerialIdOfItem, nameOfItem, weightOfItem);
                        openedOrder.addItemToItemsMapOfOrder(orderedItemByWeight);
                    }

                    break;
                default:
                    break;
            }
            System.out.println("Enter q if you want finish or enter any other key to continue with the order\"");
            choiceOfUser = sc.nextLine();
        } while(choiceOfUser.toLowerCase().equals("q") == false);
    }

}
