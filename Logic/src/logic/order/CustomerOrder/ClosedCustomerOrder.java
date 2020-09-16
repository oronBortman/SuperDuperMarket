package logic.order.CustomerOrder;

import logic.order.ClosedOrder;
import logic.order.Order;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;

public class ClosedCustomerOrder extends Order {

    Integer SerialNumber;
    Map<Integer, ClosedStoreOrder> closedStoresOrderMapByStoreSerialID;

    public ClosedCustomerOrder(LocalDate date, Map<Integer, ClosedStoreOrder> closedStoresOrderMapByStoreSerialID , boolean isOrderStatic)
    {
        super(date, isOrderStatic);
        this.closedStoresOrderMapByStoreSerialID = closedStoresOrderMapByStoreSerialID;
    }

    public Map<Integer, ClosedStoreOrder> getClosedStoresOrderMapByStoreSerialID() {
        return closedStoresOrderMapByStoreSerialID;
    }

    public double getDeliveryPriceAfterOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).calcTotalDeliveryPrice();
    }

    public double getDeliveryPriceAfterOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.calcTotalDeliveryPrice()).sum();

    }

    public double getTotalPriceOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).calcTotalDeliveryPrice();
    }

    public double getTotalPriceOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {

        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.calcTotalDeliveryPrice()).sum();
    }

    public Double getTotalAmountOfItemsByUnitOfStoreOrderBySerialIDOfStore(int serialID)
    {
        return closedStoresOrderMapByStoreSerialID.get(serialID).getTotalAmountOfItemsByUnit();
    }

    public Double getTotalAmountOfItemsByUnitOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.getTotalAmountOfItemsByUnit()).sum();
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
        return closedStoresOrderMapByStoreSerialID.get(serialID).calcTotalPriceOfItems();
    }

    public double getTotalPriceOfItemsOfStoreOrderBySerialIDOfItem(int serialIDOfItem)
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().filter(closedStoreOrder -> closedStoreOrder.checkIfItemAlreadyExistsInOrder(serialIDOfItem)).mapToDouble(x->x.calcTotalPriceOfItems()).sum();

    }

    public List<ClosedStoreOrder> generateListOfClosedStoreOrders()
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().collect(toCollection(ArrayList::new));
    }

    public void setSerialNumber(Integer serialNumber) {
        this.SerialNumber = serialNumber;
    }

    public Double getTotalItemCostInOrder()
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().mapToDouble(x->x.calcTotalPriceOfItems()).sum();
    }

    public Double getTotalDeliveryPriceInOrder()
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().mapToDouble(x->x.calcTotalDeliveryPrice()).sum();

    }

    public Double getTotalOrderPrice()
    {
        return closedStoresOrderMapByStoreSerialID.values().stream().mapToDouble(x->x.calcTotalPriceOfOrder()).sum();

    }

    public Integer getSerialNumber() {
        return SerialNumber;
    }

    public boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem) {
        return false;
    }
}
