package logic.order;

import logic.order.itemInOrder.OrderedItem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Order {

    private Date date;
    private boolean isOrderStatic;
    private boolean isOrderDynamic;


    public Order(Date date, boolean isOrderStatic)
    {
        this.date = date;
        this.isOrderStatic = isOrderStatic;
        this.isOrderDynamic = !isOrderStatic;

    }
    public Date getDate()
    {
        return date;
    }

    public boolean isOrderDynamic() {
        return isOrderDynamic;
    }

    public boolean isOrderStatic() {
        return isOrderStatic;
    }

    public abstract boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem);

}
