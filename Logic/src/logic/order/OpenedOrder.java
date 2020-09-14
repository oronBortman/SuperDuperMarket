package logic.order;

import logic.SDMLocation;

public interface OpenedOrder {

    //TODO
    public abstract Double calcTotalDeliveryPrice(SDMLocation inputLocation);

    public Double calcTotalPriceOfOrder(SDMLocation inputLocation);

    public abstract Double calcTotalPriceOfItemsNotFromSale();

    public abstract Double calcTotalAmountOfItemsNotFromSaleByUnit();
    /*public logic.Orders.orderItems.OrderedItem getItemInOrder(int serialIDOfItem)
    {
        return getOrderedItems().get(serialIDOfItem);
    }*/

    public abstract boolean checkIfItemAlreadyExistsInOrder(int serialIDOfItem);

    public abstract Integer calcTotalAmountOfItemsTypeNotFromSale();
}
