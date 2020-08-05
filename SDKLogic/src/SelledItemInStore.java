public class SelledItemInStore extends SDKItem{
    private int pricePerUnit;

    SelledItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }

    public int getPricePerUnit()
    {
        return pricePerUnit;
    }
}
