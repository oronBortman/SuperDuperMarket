package logic.order.StoreOrder;

import logic.BusinessLogic;
import logic.order.ClosedOrder;
import logic.order.itemInOrder.OrderedItemFromStore;
import logic.Store;

import java.util.Date;
import java.util.Map;

public class ClosedStoreOrder extends StoreOrder{

    private Integer serialNumber;
    private double deliveryPrice;
    private double totalPriceOfOrder;
    private Double totalAmountOfItemsByUnit;
    private int totalAmountOfItemTypes;
    private double totalPriceOfItems;


    public ClosedStoreOrder(double deliveryPrice, double totalPriceOfOrder, Double totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Store storeUsed, Map<Integer, OrderedItemFromStore> orderedItems, Date date, boolean isOrderStatic) {
        super(date, isOrderStatic);
        this.serialNumber = BusinessLogic.getCurrentOrderSerialIDInSDK();
        this.deliveryPrice = deliveryPrice;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalAmountOfItemsByUnit = totalAmountOfItemsByUnit;
        this.totalAmountOfItemTypes = totalAmountOfItemTypes;
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Store getStoreUsed() {
        return storeUsed;
    }

    public Double getDeliveryPriceAfterOrder() {
        return deliveryPrice;
    }

    public Double getTotalPriceOfOrder()
    {
        return totalPriceOfOrder;
    }

    public Double getTotalAmountOfItemsByUnit() {
        return totalAmountOfItemsByUnit;
    }

    public Integer getTotalAmountOfItemTypes() {
        return totalAmountOfItemTypes;
    }

    public Double getTotalPriceOfItems()
    {
        return totalPriceOfItems;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

}


