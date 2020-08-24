import java.util.Scanner;

public class MenuOptionForOrderAndBuy {

    enum OrderOptions {
        STATIC_ORDER(1, "Static order"),
        DYNAMIC_ORDER(2, "Dynamic order");

        int optionNum;
        String meaning;

        OrderOptions(int optionNum, String meaning) {
            this.optionNum = optionNum;
            this.meaning = meaning;
        }

        public int getOptionNum() {
            return optionNum;
        }

        public String getMeaning() {
            return meaning;
        }

        public static OrderOptions convertOptionNumToEnum(int choiceNum) {
            for (OrderOptions option : OrderOptions.values()) {
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
            for (MainMenu.mainMenuOptions option : MainMenu.mainMenuOptions.values()) {
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

    private Base base;
    private DetailsPrinter detailsPrinter;

    public MenuOptionForOrderAndBuy(Base base) {
        this.base = base;
        detailsPrinter = new DetailsPrinter(base);
    }

    public void printOrderTypeChoiceMenu() {
        System.out.println("Choose your option by entering the number of option and after it press enter:");
        System.out.println("For example: For choosing the option \"" + OrderOptions.STATIC_ORDER.getMeaning() + "\" enter the number 1 and after it press enter");
        OrderOptions.STATIC_ORDER.printOption();
        OrderOptions.DYNAMIC_ORDER.printOption();
    }

    public void orderAndBuy()
    {
        chooseOrderTypeMenu();
    }

    public void chooseOrderTypeMenu() {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        Integer userChoice;
        boolean goodChoice;
        do {
            printOrderTypeChoiceMenu();
            if (sc.hasNextInt()) {
                userChoice = sc.nextInt();
                if (OrderOptions.containsChoiceNum(userChoice)) {
                    if (OrderOptions.STATIC_ORDER.numberEqualsToOptionNum(userChoice)) {
                        StaticOrderMenu staticOrder = new StaticOrderMenu(base);
                        staticOrder.makeStaticOrder();
                        goodChoice = true;
                    } else if (OrderOptions.DYNAMIC_ORDER.numberEqualsToOptionNum(userChoice)) {
                        DynamicOrderMenu dynamicOrder = new DynamicOrderMenu(base);
                        dynamicOrder.makeDynamicOrder();
                        goodChoice = true;
                    } else {
                        goodChoice = false;
                    }
                } else {
                    goodChoice = false;
                    System.out.println("You didn't entered a valid number. Please try again");
                }
            } else {
                sc.next();
                System.out.println("You didn't entered a number!");
                goodChoice = false;
            }
        } while (goodChoice == false);
    }
}