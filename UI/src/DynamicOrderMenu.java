import java.util.Scanner;

public class DynamicOrderMenu extends OrderMenu{
    private Logic base;
    DetailsPrinter detailsPrinter;
    public DynamicOrderMenu(Logic base)
    {
        this.base=base;
        detailsPrinter = new DetailsPrinter(base);
    }
    public void makeDynamicOrder()
    {
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

    public void inputItemsUntilQuitSign(DynamicOrder openedOrder)
    {
        String choiceOfUser;
        Scanner sc = new Scanner(System.in);  // Create a Scanner object

        do {
            detailsPrinter.showSytemItemDetails();
            int inputSerialIdOfItem = inputItemSerialId();
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
        } while(choiceOfUser.toLowerCase().equals("q") == false);
    }

}
