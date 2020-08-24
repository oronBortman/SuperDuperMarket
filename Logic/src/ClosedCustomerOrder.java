import java.util.Date;
import java.util.Map;

public class ClosedCustomerOrder extends ClosedOrder {
    int totalAmountOfStores;
   public ClosedCustomerOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Map<Integer, OrderedItem> orderedItems, Date date, int totalAmountOfStores ) {
       super(deliveryPrice, totalPriceOfOrder, totalAmountOfItemsByUnit, totalAmountOfItemTypes, totalPriceOfItems, orderedItems, date);
       this.totalAmountOfStores = totalAmountOfStores;
   }

    public int getTotalAmountOfStores() {
        return totalAmountOfStores;
    }
}
