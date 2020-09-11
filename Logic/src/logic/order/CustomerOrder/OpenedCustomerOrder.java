package logic.order.CustomerOrder;

import logic.SDMLocation;
import logic.order.OpenedOrder;
import logic.order.Order;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OpenedCustomerOrder extends Order implements OpenedOrder {

    Map<Integer, OpenedStoreOrder> openedStoresOrderMap;
    SDMLocation customerLocation;

    public OpenedCustomerOrder(Date date, SDMLocation customerLocation, boolean isOrderStatic) {
        super(date, isOrderStatic);
        openedStoresOrderMap = new HashMap<Integer, OpenedStoreOrder>();
    }


    @Override
    public double calcTotalDeliveryPrice(SDMLocation inputLocation) {
        return 0;
    }

    @Override
    public double calcTotalPriceOfOrder(SDMLocation inputLocation) {
        return 0;
    }

    @Override
    public double calcTotalPriceOfItems() {
        return 0;
    }

    @Override
    public int calcTotalAmountOfItemsByUnit() {
        return 0;
    }

    @Override
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem) {
        return false;
    }

    @Override
    public int calcTotalAmountOfItemsType() {
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
}
