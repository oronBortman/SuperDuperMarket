import java.text.DecimalFormat;
import java.util.*;

public class MainMenu {

    boolean loadXmlSuccessfully = false;
    enum mainMenuOptions
    {
        READ_FROM_XML_FILE(1, "Read from XML file"),
        SHOW_STORE_DETAILS(2, "Show store details"),
        SHOW_SYSTEM_ITEM_DETAILS(3, "Show system items details"),
        ORDER_AND_BUY(4, "Order and buy"),
        SHOW_ORDERS_HISTORY(5, "Show orders history"),
        EXIT(6, "Exit");

        mainMenuOptions(int optionNum, String meaning)
        {
            this.optionNum = optionNum;
            this.meaning = meaning;
        }

        int optionNum;
        String meaning;


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

    DetailsPrinter detailsPrinter;
    MenuOptionForReadingXMLFile menuOptionForReadingXMLFile;
    MenuOptionForOrderAndBuy menuOptionForOrderAndBuy;

    Logic base = new Logic();

    public MainMenu()
    {
       // Logic base = new Logic();
        menuOptionForReadingXMLFile = new MenuOptionForReadingXMLFile();
        menuOptionForOrderAndBuy = new MenuOptionForOrderAndBuy(base);
        detailsPrinter = new DetailsPrinter(base);
    }

    /*public static String convertDoubleToDecimal(double num)
    {
        return new DecimalFormat("##.##").format(num);
    }*/
    public static String convertDoubleToDecimal(double num)
    {
        return String.format("%.2f", num);
    }

  /*  public static String convertIntToDecimal(Integer num)
    {
        return String.format("%.2d", num.doubleValue());
    }*/

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
                //TODO
                try
                {
                    Logic baseFromXml = new Logic();
                    boolean loadXmlSuccessfully = menuOptionForReadingXMLFile.readFromXMLFile(baseFromXml);
                    if(loadXmlSuccessfully)
                    {
                        this.loadXmlSuccessfully = true;
                        this.base = baseFromXml;
                        menuOptionForOrderAndBuy = new MenuOptionForOrderAndBuy(baseFromXml);
                        detailsPrinter = new DetailsPrinter(baseFromXml);
                        System.out.println("Xml file loaded successfully to Super Duper Market :)\n");
                    }
                }
                catch(Exception e)
                {
                }
                break;
            case SHOW_STORE_DETAILS:
                if(loadXmlSuccessfully == true)
                {
                    detailsPrinter.showStoresDetails(true, true);
                }
                else
                {
                    System.out.println("Can't show store details because an xml file wasn't loaded.\n Please choose option " +
                            mainMenuOptions.READ_FROM_XML_FILE.getOptionNum() +" to load an xml file to Super Duper Market");
                }
                break;
            case SHOW_SYSTEM_ITEM_DETAILS:
                if(loadXmlSuccessfully == true)
                {
                    detailsPrinter.showSytemItemDetails();
                }
                else
                {
                    System.out.println("Can't show items details of Super Duper Market because an xml file wasn't loaded.\n Please choose option " +
                            mainMenuOptions.READ_FROM_XML_FILE.getOptionNum() +" to load an xml file to Super Duper Market");
                }

                break;
            case ORDER_AND_BUY:
                //TODO
                if(loadXmlSuccessfully == true)
                {
                    menuOptionForOrderAndBuy.orderAndBuy();
                }
                else
                {
                    System.out.println("Can't order and buy items from Super Duper Market because an xml file wasn't loaded.\n Please choose option " +
                            mainMenuOptions.READ_FROM_XML_FILE.getOptionNum() +" to load an xml file to Super Duper Market");
                }
                break;
            case SHOW_ORDERS_HISTORY:
                if(loadXmlSuccessfully == true)
                {
                    detailsPrinter.showOrdersHistory();
                }
                else
                {
                    System.out.println("Can't show orders history of Super Duper Market because an xml file wasn't loaded.\n Please choose option " +
                            mainMenuOptions.READ_FROM_XML_FILE.getOptionNum() +" to load an xml file to Super Duper Market");
                }
                break;
            default:
                break;
        }
    }

}