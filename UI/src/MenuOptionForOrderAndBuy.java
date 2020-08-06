import java.util.Set;

public class MenuOptionForOrderAndBuy {

    private SDKBase base;

    public MenuOptionForOrderAndBuy(SDKBase base)
    {
        this.base = base;
    }
    public void orderAndBuy() {
        Set<Integer> setOfItemsSerialID = base.getSetOfItemsSerialID();
        SDKItem item;

        for (Integer itemSerialID : setOfItemsSerialID) {
            item = base.getItemySerialID(itemSerialID);
            System.out.println("1.Serial IDL" + itemSerialID);
            System.out.println("2.Name:" + item.getName());
            System.out.println("3.Type of buying:" + item.getTypeOfMeasureStr());
            System.out.println("4.How many shops selles the item: " + base.getHowManyShopsSellesAnItem(itemSerialID));
            System.out.println("5.Average price of item in Super Duper Market: " + base.getAvgPriceOfItemInSDK(itemSerialID));
            System.out.println("6.How many times the the item has been soled in Super Duper Market: " + base.getHowManyTimesTheItemSoled(itemSerialID));
        }
    }
}
