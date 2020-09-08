package metadata;

import commonUI.*;
import exceptions.*;
import javafx.concurrent.Task;
import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import jaxb.schema.generated.*;
import logic.BusinessLogic;
import logic.discount.Discount;
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

    private final int SLEEP_TIME = 1;

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
            updateMessage("Reading items to Stores..");
            readItemsToStoresFromXML();
            updateMessage("Reading discounts to Stores..");
            readDiscountsToStoresFromXML();

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
        SuperDuperMarketUtils.sleepForAWhile(2000);
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
            catch (Exception exception)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw exception;
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
            catch (Exception exception)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw exception;

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
            catch (Exception exception)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw exception;

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }


    public void readItemsToStoresFromXML() throws FileNotFoundException, TaskIsCanceledException, JAXBException, DuplicateStoreSerialIDException, DuplicateItemSerialIDInStoreException, ItemWithSerialIDNotExistInSDMException, StoreNotExistException, ItemNotExistInStoresException {
        int counter=0;
        updateProgress(counter, 1);
        SuperDuperMarketUtils.sleepForAWhile(1);
        for(SDMStore store : stores)
        {
            try
            {
                readItemsToAStoreFromXML(store);
                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                System.out.println(counter);
            }
            catch (Exception exception)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw exception;
            }
            SuperDuperMarketUtils.sleepForAWhile(1);
        }
        try
        {
            businessLogic.checkIfThereIsItemNotInStore();
        }
        catch (Exception exception)
        {
            cancelled();
            SuperDuperMarketUtils.log("Task was canceled !");
            onCancel.accept(null);
            throw exception;
        }
    }

    public void readItemsToAStoreFromXML(SDMStore store) throws FileNotFoundException, TaskIsCanceledException, JAXBException, DuplicateItemSerialIDInStoreException, ItemWithSerialIDNotExistInSDMException, StoreNotExistException, DuplicateStoreSerialIDException {
        int counter=0;
        SuperDuperMarketUtils.sleepForAWhile(1);
        updateMessage("Reading items to the store " + store.getName() + "...");
        SDMPrices pricesInStore = store.getSDMPrices();
        //TODO
        //What to do if price is empty?
        List<SDMSell> sdmSellList = pricesInStore.getSDMSell();
        updateProgress(counter, sdmSellList.size());
        SuperDuperMarketUtils.sleepForAWhile(1);

        for(SDMSell sdmSell : sdmSellList)
        {
            businessLogic.addItemToStoreFromSDMSell(sdmSell, store.getId());
            if (isCancelled()) {
                System.out.println("is Cancelled");

                throw new TaskIsCanceledException();
            }
            System.out.println(counter);
            counter++;
            updateProgress(counter, sdmSellList.size());
            SuperDuperMarketUtils.sleepForAWhile(1);
        }
    }

    public void readDiscountsToStoresFromXML() throws FileNotFoundException, TaskIsCanceledException, JAXBException, DuplicateStoreSerialIDException, DuplicateItemSerialIDInStoreException, ItemNotExistInStoresException, StoreNotExistException, DuplicateDiscountNameException, ItemWithSerialIDNotExistInSDMException, ItemIDNotExistInAStoreException {
        int counter=0;
        updateProgress(counter, stores.size());
        SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        for(SDMStore store : stores)
        {
            try
            {
                readDiscountsToAStoreFromXML(store);
                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                System.out.println(counter);
                counter++;
                updateProgress(counter, stores.size());
                SuperDuperMarketUtils.sleepForAWhile(1);
            }
            catch (Exception exception)
            {
                cancelled();
                SuperDuperMarketUtils.log("Task was canceled !");
                onCancel.accept(null);
                throw exception;

            }
            SuperDuperMarketUtils.sleepForAWhile(SLEEP_TIME);
        }
    }

    public void readDiscountsToAStoreFromXML(SDMStore store) throws FileNotFoundException, TaskIsCanceledException, JAXBException, DuplicateItemSerialIDInStoreException, ItemWithSerialIDNotExistInSDMException, StoreNotExistException, DuplicateStoreSerialIDException, ItemNotExistInStoresException, DuplicateDiscountNameException, ItemIDNotExistInAStoreException {
        int counter=0;
        SuperDuperMarketUtils.sleepForAWhile(1000);
        System.out.println(("Reading discounts to the store " + store.getName() + "..."));
        updateMessage("Reading discounts to the store " + store.getName() + "...");
        //TODO
        //What to do if price is empty?
        List<SDMDiscount> sdmDiscountList = store.getSDMDiscounts();
        if(store.getSDMDiscounts() == null)
        {
            System.out.println("There are no discounts at the store " + store.getName());
        {
            //.getSDMDiscount(),
         }
        updateProgress(counter, sdmDiscountList.size());
        SuperDuperMarketUtils.sleepForAWhile(1000);
        if(sdmDiscountList == null)
        {
            System.out.println("There are no discounts at the store " + store.getName());
        }
        else
        {
            for(SDMDiscount sdmDiscount : sdmDiscountList)
            {
                System.out.println(sdmDiscount.getName());
                businessLogic.addDiscountToStoreFromSDMSell(sdmDiscount, store.getId());
                if (isCancelled()) {
                    System.out.println("is Cancelled");

                    throw new TaskIsCanceledException();
                }
                System.out.println("Succeed in reading the discount " + sdmDiscount.getName() + " to the store " + store.getName());
                counter++;
                updateProgress(counter, sdmDiscountList.size());
                SuperDuperMarketUtils.sleepForAWhile(1);
            }
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