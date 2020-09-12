package logic.order.CustomerOrder;

import logic.SDMLocation;
import logic.order.OpenedOrder;
import logic.order.Order;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

import java.util.*;
import java.util.stream.Collectors;

public class OpenedCustomerOrder extends Order implements OpenedOrder {

    Map<Integer, OpenedStoreOrder> openedStoresOrderMap;
    SDMLocation customerLocation;

    public OpenedCustomerOrder(Date date, SDMLocation customerLocation, boolean isOrderStatic) {
        super(date, isOrderStatic);
        this.customerLocation = customerLocation;
        openedStoresOrderMap = new HashMap<Integer, OpenedStoreOrder>();
    }


    public Map<Integer, OpenedStoreOrder> getOpenedStoresOrderMap() {
        return openedStoresOrderMap;
    }

    public List<OpenedStoreOrder> getListOfOpenedStoreOrder()
    {
        ArrayList<OpenedStoreOrder> listOfValues
                = openedStoresOrderMap.values().stream().collect(
                Collectors.toCollection(ArrayList::new));
        return listOfValues;
    }
    public SDMLocation getCustomerLocation() {
        return customerLocation;
    }

    @Override
    public Double calcTotalDeliveryPrice(SDMLocation inputLocation) {
        return 0.0;
    }

    @Override
    public Double calcTotalPriceOfOrder(SDMLocation inputLocation) {
        return 0.0;
    }

    @Override
    public Double calcTotalPriceOfItems() {
        return 0.0;
    }

    @Override
    public Integer calcTotalAmountOfItemsByUnit() {
        return 0;
    }

    @Override
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem) {
        return false;
    }

    @Override
    public Integer calcTotalAmountOfItemsType() {
        return 0;
    }

    public ClosedCustomerOrder closeCustomerOrder()
    {
        Map<Integer, ClosedStoreOrder> closedStoresOrderMapByStoreSerialID = new HashMap<Integer, ClosedStoreOrder>();

        for(OpenedStoreOrder openedStoreOrder : openedStoresOrderMap.values())
        {
            int serialNumber = openedStoreOrder.getStoreUsed().getSerialNumber();
            ClosedStoreOrder closedStoreOrder = openedStoreOrder.closeOrder(customerLocation);
            closedStoresOrderMapByStoreSerialID.put(serialNumber, closedStoreOrder);
        }

        return new ClosedCustomerOrder(getDate(), closedStoresOrderMapByStoreSerialID, isOrderStatic());
    }

    public void addStoreOrder(OpenedStoreOrder openedStoreOrder)
    {
        openedStoresOrderMap.put(openedStoreOrder.getStoreUsed().getSerialNumber(), openedStoreOrder);
    }
}
