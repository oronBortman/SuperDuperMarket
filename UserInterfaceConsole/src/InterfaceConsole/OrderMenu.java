package InterfaceConsole;

import logic.SDMLocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Scanner;

public class OrderMenu {

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
                System.out.println("You didn't entered a whole number!");
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

    public boolean inputIfUserApprovesOrder()
    {
        System.out.println("Enter y to approve the other or any other key if you don't approve");
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        String choiceOfUser = sc.nextLine();
        return choiceOfUser.toLowerCase().equals("y");
    }

    public Date inputDate() {
        Scanner scanner = new Scanner(System.in);
        boolean dateIsValid;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");
        dateFormat.setLenient(false);
        Date dateParsed=null;
        String inputDate;

        do{
            try {
                System.out.println("Enter the Date in the following format: dd/MM-hh:mm");
                inputDate = scanner.nextLine();
                //Parsing the String
                dateParsed = dateFormat.parse(inputDate);


                System.out.println("The date is:" + dateFormat.format(dateParsed));
                dateIsValid = true;
            }

            catch(ParseException e)
            {
                System.out.println("Invalid date");
                dateIsValid=false;
            }
            catch(Exception e)
            {
                System.out.println("Invalid date");
                dateIsValid=false;
            }
        } while(dateIsValid == false);
        return dateParsed;
    }

}
