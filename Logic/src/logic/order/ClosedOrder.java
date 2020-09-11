package logic.order;

import logic.BusinessLogic;
import logic.order.itemInOrder.OrderedItem;

import java.util.Date;
import java.util.Map;

public interface ClosedOrder{

    public Integer getSerialNumber();

   /* public double getTotalAmountOfAnItemByTypeOfMeasure(int itemSerialID)
    {
        return getItemInOrder(itemSerialID).getTotalAmountOfItemOrderedByTypeOfMeasure();
    }*/

}

