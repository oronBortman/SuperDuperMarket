import javax.xml.bind.JAXBException;

public class MenuOptionForReadingXMLFile {
    private Logic base;

    public MenuOptionForReadingXMLFile(Logic base)
    {
        this.base = base;
    }

    public void readFromXMLFile() throws SerialIDNotExistException, JAXBException, duplicateSerialIDException {
        try
        {
            readShopsFromXMLFile();
            readItemsFromXMLFile();
            base.addItemsToStoresFromXml("ex1-small.xml");
        }
        catch(SerialIDNotExistException e) {
            System.out.println("An item with serial id " + e.getSerialId() + " that you tried to add to the store " + e.getName() + " doesn't exist in Super Duper Market.");
            System.out.println("Please fix your xml file and try again");
        }

        catch(duplicateSerialIDException e)
        {
            System.out.println("The serial id " + e.getSerialId() + " already define in the store");
            System.out.println("Please fix your xml file and try again");

        }
        catch(Exception e)
        {

        }



    }
    private void readShopsFromXMLFile() {

        System.out.println("read from XML file");

        try
        {
            base.createStoresSerialIDMapFromXml("ex1-small.xml");
            base.createStoresLocationMapFromXml("ex1-small.xml");
        }
        catch (duplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the store " + e.getName() + " is not unique.");
            System.out.println("Please fix your xml file and try again");
        }
        catch( invalidCoordinateXException e)
        {
            System.out.println("The coordinate X with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again");
        }
        catch( invalidCoordinateYException e)
        {
            System.out.println("The coordinate Y with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again");
        }
        catch(Exception e)
        {

        }
    }

    private void readItemsFromXMLFile() {
        System.out.println("read from XML file");
        try
        {
            base.createItemsSerialIDMapFromXml("ex1-small.xml");
        }
        catch (duplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the store " + e.getName()   + " is not unique");
            System.out.println("Please fix your xml file and try again");
        }
        catch (Exception e)
        {

        }
    }

}
