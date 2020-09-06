package InterfaceConsole;

import exceptions.*;
import logic.BusinessLogic;

import javax.xml.bind.JAXBException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MenuOptionForReadingXMLFile {

    public boolean checkIfPathHasXmlExtension(String filePath)
    {
        return filePath.toLowerCase().endsWith(".xml");
    }

    public boolean checkIfFileExists(String filePath)
    {
        return Files.exists(Paths.get(filePath));
    }

    public String readXmlFileName()
    {
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        boolean goodChoice;
        int inputOfItemSerialId = 0;
        String xmlFileName;
        do {
            System.out.println("Please enter the xml file you want to load from");
            xmlFileName = sc.nextLine();
            if(!checkIfPathHasXmlExtension(xmlFileName))
            {
                System.out.println("The file you entered doesn't end with the extension .xml - Please try again");
                goodChoice=false;
            }
            else if (!checkIfFileExists(xmlFileName))
            {
                System.out.println("The path you entered doesn't exist. Please try again");
                goodChoice=false;
            }
            else
            {
                goodChoice = true;
            }
        }
        while (!goodChoice);
        return xmlFileName;
    }

    public boolean readFromXMLFile(BusinessLogic baseFromXml, String xmlFile) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDException {
        boolean loadXmlSuccessfully;
        boolean readShopsSuccefully;
        boolean readItemsSuccefully;
        try
        {
            readShopsSuccefully=readShopsFromXMLFile(xmlFile, baseFromXml);
            if(readShopsSuccefully)
            {
                readItemsSuccefully = readItemsFromXMLFile(xmlFile, baseFromXml);
                if(readItemsSuccefully)
                {
                    baseFromXml.addItemsToStoresFromXml(xmlFile);
                    loadXmlSuccessfully = true;
                }
                else
                {
                    loadXmlSuccessfully = false;
                }
            }
            else
            {
                loadXmlSuccessfully = false;
            }
        }
        catch(SerialIDNotExistException e) {
            System.out.println("An item with serial id " + e.getSerialId() + " that you tried to add to the store " + e.getName() + " doesn't exist in Super Duper Market.");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }

        catch(DuplicateSerialIDException e)
        {
            System.out.println("There is more than one item with serial id " + e.getSerialId() + " that is defined in the store " + e.getName() + ".\n" +
                    "An item can defined only once in store");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }
        catch(ItemNotExistInStoresException e)
        {
            System.out.println("An item with serial id " + e.getItem().getSerialNumber() + " doesn't exist in any store in Super Duper Market.");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }
        catch(Exception e)
        {
            loadXmlSuccessfully = false;
        }
        return loadXmlSuccessfully;
    }

    public boolean readFromXMLFile(BusinessLogic baseFromXml) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDException {
        boolean loadXmlSuccessfully;
        boolean readShopsSuccefully;
        boolean readItemsSuccefully;
        try
        {
            String xmlFile = readXmlFileName();
            readShopsSuccefully=readShopsFromXMLFile(xmlFile, baseFromXml);
            if(readShopsSuccefully)
            {
                readItemsSuccefully = readItemsFromXMLFile(xmlFile, baseFromXml);
                if(readItemsSuccefully)
                {
                    baseFromXml.addItemsToStoresFromXml(xmlFile);
                    loadXmlSuccessfully = true;
                }
                else
                {
                    loadXmlSuccessfully = false;
                }
            }
            else
            {
                loadXmlSuccessfully = false;
            }
        }
        catch(SerialIDNotExistException e) {
            System.out.println("An item with serial id " + e.getSerialId() + " that you tried to add to the store " + e.getName() + " doesn't exist in Super Duper Market.");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }

        catch(DuplicateSerialIDException e)
        {
            System.out.println("There is more than one item with serial id " + e.getSerialId() + " that is defined in the store " + e.getName() + ".\n" +
                            "An item can defined only once in store");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }
        catch(ItemNotExistInStoresException e)
        {
            System.out.println("An item with serial id " + e.getItem().getSerialNumber() + " doesn't exist in any store in Super Duper Market.");
            System.out.println("Please fix your xml file and try again\n");
            loadXmlSuccessfully = false;
        }
        catch(Exception e)
        {
            loadXmlSuccessfully = false;
        }
        return loadXmlSuccessfully;
    }
    private boolean readShopsFromXMLFile(String xmlFileName, BusinessLogic baseFromXml) {
        boolean readShopsSuccefully;
        try
        {
            baseFromXml.createStoresSerialIDMapFromXml(xmlFileName);
            baseFromXml.createStoresLocationMapFromXml(xmlFileName);
            readShopsSuccefully=true;
        }
        catch (DuplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the store " + e.getName() + " is not unique.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch( InvalidCoordinateXException e)
        {
            System.out.println("The coordinate X with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch( InvalidCoordinateYException e)
        {
            System.out.println("The coordinate Y with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch(Exception e)
        {
            readShopsSuccefully=false;
        }
        return readShopsSuccefully;
    }




    private boolean readItemsFromXMLFile(String xmlFileName, BusinessLogic baseFromXml) {
        boolean readItemsSuccessfully;
        try
        {
            baseFromXml.createItemsSerialIDMapFromXml(xmlFileName, 2);
            readItemsSuccessfully=true;
        }
        catch (DuplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the item " + e.getName()   + " is not unique" + " in Super Duper Market");
            System.out.println("Please fix your xml file and try again\n");
            readItemsSuccessfully=false;
        }
        catch (Exception e)
        {
            readItemsSuccessfully=false;
        }
        return readItemsSuccessfully;
    }

}
