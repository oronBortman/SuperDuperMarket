package logic.order.CustomerOrder;

import logic.order.ClosedOrder;
import logic.order.Order;
import logic.order.StoreOrder.ClosedStoreOrder;

import java.util.Date;
import java.util.Map;

public class ClosedCustomerOrder extends Order implements ClosedOrder {

    Integer SerialNumber;
    Map<Integer, ClosedStoreOrder> closedStoresOrderMapByStoreSerialID;

    public ClosedCustomerOrder(Date date,Map<Integer, ClosedStoreOrder> closedStoresOrderMapByStoreSerialID , boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        this.closedStoresOrderMapByStoreSerialID = closedStoresOrderMapByStoreSerialID;
    }

    public Map<Integer, ClosedStoreOrder> getClosedStoresOrderMapByStoreSerialID() {
        return closedStoresOrderMapByStoreSerialID;
    }

    public double getDeliveryPriceAfterOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getDeliveryPriceAfterOrder();
    }

    public double getDeliveryPriceAfterOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.getDeliveryPriceAfterOrder()).sum();

    }

    public double getTotalPriceOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getTotalPriceOfOrder();
    }

    public double getTotalPriceOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {

        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.getTotalPriceOfOrder()).sum();
    }

    public int getTotalAmountOfItemsByUnitOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getTotalAmountOfItemsByUnit();
    }

    public int getTotalAmountOfItemsByUnitOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToInt(x->x.getTotalAmountOfItemsByUnit()).sum();
    }


    public int getTotalAmountOfItemTypesOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getTotalAmountOfItemTypes();
    }

    public int getTotalAmountOfItemTypesOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToInt(x->x.getTotalAmountOfItemTypes()).sum();
    }

    public double getTotalPriceOfItemsOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getTotalPriceOfItems();
    }

    public double getTotalPriceOfItemsOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.getTotalPriceOfItems()).sum();

    }

    @Override
    public Integer getSerialNumber() {
        return SerialNumber;
    }

    @Override
    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem) {
        return false;
    }
}
