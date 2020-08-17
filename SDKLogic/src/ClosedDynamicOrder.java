import java.util.Date;
import java.util.Map;

public class ClosedDynamicOrder extends ClosedOrder {
   public ClosedDynamicOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Map<Integer, OrderedItem> orderedItems, Date date )
    {
        super( deliveryPrice,  totalPriceOfOrder,  totalAmountOfItemsByUnit,  totalAmountOfItemTypes,  totalPriceOfItems, orderedItems,date );
    }
}
