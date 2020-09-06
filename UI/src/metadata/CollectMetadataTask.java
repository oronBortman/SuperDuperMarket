package metadata;

import commonUI.*;
import exceptions.DuplicateSerialIDException;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import exceptions.TaskIsCanceledException;
import jaxb.schema.generated.*;
import logic.BusinessLogic;
import logic.order.GeneralMethods;

import javax.xml.bind.JAXBException;

public class CollectMetadataTask extends Task<Boolean> {

    private String fileName;
    private Consumer<Runnable> onCancel;
    private BusinessLogic businessLogic;

    private final int SLEEP_TIME = 100;

    public CollectMetadataTask(String fileName, Consumer<Runnable> onCancel, BusinessLogic businessLogic) {
        this.fileName = fileName;
        this.onCancel = onCancel;
        this.businessLogic = businessLogic;

    }

    @Override
    protected Boolean call() throws Exception {

        try {

            updateMessage("Fetching file...");
            BufferedReader reader = Files.newBufferedReader(
                    Paths.get(fileName),
                    StandardCharsets.UTF_8);

            updateMessage("Reading items..");
            //updateProgress(0, totalNum);
            System.out.println(fileName);
            readItemsFromXML();
            updateMessage("Reading Users..");
            //updateProgress(0, totalNum);
            System.out.println(fileName);
            readUsersFromXML();

            updateMessage("Done...");
        } catch (IOException e) {
            e.printStackTrace();
        }
     catch (TaskIsCanceledException e) {
        SuperDuperMarketUtils.log("Task was canceled !");
        onCancel.accept(null);
    }
    updateMessage("Done...");
        return Boolean.TRUE;

    }

    public void readItemsFromXML() throws FileNotFoundException, JAXBException, DuplicateSerialIDException {
        InputStream inputStream = new FileInputStream(fileName);
        System.out.println("Reading file");
        SuperDuperMarketDescriptor descriptor = GeneralMethods.deserializeFrom(inputStream);
        SDMItems items = descriptor.getSDMItems();
        List<SDMItem> listOfItems = items.getSDMItem();
        int totalNum = listOfItems.size();
        System.out.println(totalNum);
        int counter=0;

        updateProgress(counter, totalNum);
        counter++;

        for(SDMItem item : listOfItems)
        {
            try
            {
                businessLogic.addItemsSerialIDMapFromXml(item);

                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                updateProgress(counter, totalNum);
                counter++;
            }
            catch (DuplicateSerialIDException error)
            {
                //TODO
                //Change to error
                super.cancelled();
                updateMessage("Found duplicate key!");
                updateProgress(0, totalNum);
                System.out.println("Found duplicate Key!");
                throw new TaskIsCanceledException();

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    public void readUsersFromXML() throws FileNotFoundException, JAXBException, DuplicateSerialIDException {
        InputStream inputStream = new FileInputStream(fileName);
        System.out.println("Reading file");
        SuperDuperMarketDescriptor descriptor = GeneralMethods.deserializeFrom(inputStream);
        SDMCustomers users = descriptor.getSDMCustomers();
        List<SDMCustomer> listOfUsers = users.getSDMCustomer();
        int totalNum = listOfUsers.size();
        System.out.println(totalNum);
        int counter=0;

        updateProgress(counter, totalNum);
        counter++;

        for(SDMCustomer user : listOfUsers)
        {
            try
            {
                businessLogic.addUserSerialIDMapFromXml(user);
                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                updateProgress(counter, totalNum);
                counter++;
            }
            catch (DuplicateSerialIDException error)
            {
                //TODO
                //Change to error
                super.cancelled();
                updateMessage("Found duplicate key!");
                updateProgress(0, totalNum);
                System.out.println("Found duplicate Key!");
                throw new TaskIsCanceledException();

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }


}