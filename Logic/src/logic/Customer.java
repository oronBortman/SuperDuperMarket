package logic;

import logic.order.CustomerOrder.ClosedCustomerOrder;

import java.util.HashMap;
import java.util.Map;

public class Customer extends SDMObjectWithUniqueLocationAndUniqueSerialID {

    Map<Integer, ClosedCustomerOrder> mapOfClosedCustomerOrders;
    public Customer(Integer serialNumber, String name, SDMLocation location)
    {
        super(serialNumber, name, location);
        mapOfClosedCustomerOrders = new HashMap<Integer, ClosedCustomerOrder>();
    }

    public Customer(jaxb.schema.generated.SDMCustomer user)
    {
        super(user.getId(), user.getName(), new SDMLocation(user.getLocation()));
        mapOfClosedCustomerOrders = new HashMap<Integer, ClosedCustomerOrder>();
    }

    public void addClosedCustomerOrderToMap(ClosedCustomerOrder closedCustomerOrder)
    {
        mapOfClosedCustomerOrders.put(closedCustomerOrder.getSerialNumber(), closedCustomerOrder);
    }

    public Map<Integer, ClosedCustomerOrder> getMapOfClosedCustomerOrders() {
        return mapOfClosedCustomerOrders;
    }

    //TODO
    public Integer getAmountOfOrdersCol()
    {
        return mapOfClosedCustomerOrders.size();
    }
    //TODO
    public Double getAverageOrderPriceCol()
    {
        return (mapOfClosedCustomerOrders.values().stream().mapToDouble(x->x.getTotalOrderPrice()).sum()) / getAmountOfOrdersCol();
    }
    //TODO
    public Double getAverageDeliveryPrice()
    {
        return (mapOfClosedCustomerOrders.values().stream().mapToDouble(x->x.getTotalDeliveryPriceInOrder()).sum()) / getAmountOfOrdersCol();

    }

    /*
    public User(jaxb.schema.generated.SDMItem item)
    {
        this.serialNumber = new SimpleIntegerProperty(item.getId());
        this.name = new SimpleStringProperty(item.getName());
        String itemPurchaseCategoryStr = item.getPurchaseCategory();
        Item.TypeOfMeasure itemPurchaseCategory = Item.TypeOfMeasure.convertStringToEnum(itemPurchaseCategoryStr);
        this.typeToMeasureBy = itemPurchaseCategory;
    }*/

}
