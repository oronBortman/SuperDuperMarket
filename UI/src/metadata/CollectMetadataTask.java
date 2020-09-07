package metadata;

import commonUI.*;
import exceptions.*;
import javafx.concurrent.Task;
import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import jaxb.schema.generated.*;
import logic.BusinessLogic;
import logic.order.GeneralMethods;
import javax.xml.bind.JAXBException;

public class CollectMetadataTask extends Task<Boolean> {

    private String fileName;
    private Consumer<Runnable> onCancel;
    private BusinessLogic businessLogic;
    private List<SDMItem> items;
    private List<SDMStore> stores;
    private List<SDMCustomer> customers;
    private Consumer<String> errorMessage;

    private final int SLEEP_TIME = 1000;

    public CollectMetadataTask(String fileName, Consumer<Runnable> onCancel, BusinessLogic businessLogic) {
        this.fileName = fileName;
        this.onCancel = onCancel;
        this.businessLogic = businessLogic;
       // this.errorMessage = errorMessage;
    }

    public void readListsFromXML() throws FileNotFoundException, JAXBException {
        InputStream inputStream = new FileInputStream(fileName);
        SuperDuperMarketDescriptor descriptor = GeneralMethods.deserializeFrom(inputStream);
        items = descriptor.getSDMItems().getSDMItem();
        stores = descriptor.getSDMStores().getSDMStore();
        customers = descriptor.getSDMCustomers().getSDMCustomer();

    }

    @Override
    protected Boolean call() throws Exception {

        try {
            System.out.println("Reading file");

            readListsFromXML();
            updateMessage("Reading items..");

            readItemsFromXML();

            updateMessage("Reading Users..");

            readUsersFromXML();

            updateMessage("Reading Stores..");
            readStoresFromXML();

            updateMessage("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TaskIsCanceledException e) {
            SuperDuperMarketUtils.log("Task was canceled !");
            onCancel.accept(null);
        }
        return Boolean.TRUE;

    }

    public void readItemsFromXML() throws JAXBException, FileNotFoundException, TaskIsCanceledException, DuplicateItemSerialIDException {
        int counter=0;
        updateProgress(counter, items.size());
        SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        for(SDMItem item : items)
        {
            try
            {
                businessLogic.addItemsSerialIDMapFromXml(item);
                if (isCancelled()) {
                    System.out.println("is Cancelled");
                    throw new TaskIsCanceledException();
                }
                System.out.println(counter);
                counter++;
                updateProgress(counter, items.size());
            }
            catch (DuplicateSerialIDException error)
            {
                cancelled();
                //TODO
                /*Platform.runLater(
                        () -> errorMessage.accept("Error: Found duplicate key!")
                );*/
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw new DuplicateItemSerialIDException(item.getId(), item.getName());

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    public void readUsersFromXML() throws FileNotFoundException, JAXBException, DuplicateCustomerSerialIDException, TaskIsCanceledException {
        int counter=0;
        updateProgress(counter, customers.size());
        SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        for(SDMCustomer user : customers)
        {
            try
            {
                businessLogic.addUserSerialIDMapFromXml(user);
                if (isCancelled()) {
                    System.out.println("is Cancelled");
                    throw new TaskIsCanceledException();
                }
                System.out.println(counter);
                counter++;
                updateProgress(counter, customers.size());
            }
            catch (DuplicateSerialIDException error)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw new DuplicateCustomerSerialIDException(user.getId(), user.getName());

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    public void readStoresFromXML() throws FileNotFoundException, TaskIsCanceledException, JAXBException, DuplicateStoreSerialIDException {
        int counter=0;
        updateProgress(counter, stores.size());
        SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        for(SDMStore store : stores)
        {
            try
            {
                businessLogic.addStoreSerialIDMapFromXml(store);
                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                System.out.println(counter);
                counter++;
                updateProgress(counter, stores.size());
            }
            catch (DuplicateSerialIDException error)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw new DuplicateStoreSerialIDException(store.getId(), store.getName());

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    @Override
    protected void  cancelled() {
        updateMessage("Canceled");
        //TODO
        //How to make it work without sleep!!!
        SuperDuperMarketUtils.sleepForAWhile(100);
        System.out.println("AAAAA!!");
        super.cancelled();

    }


}