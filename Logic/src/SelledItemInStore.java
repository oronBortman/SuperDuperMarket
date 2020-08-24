public class SelledItemInStore extends Item {
    private int pricePerUnit;

    SelledItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int pricePerUnit) {
        super(serialNumber, name, purchaseCategory);
        this.pricePerUnit = pricePerUnit;
    }

    SelledItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }

    SelledItemInStore(Item item, int price) {
        super(item);
        this.pricePerUnit = price;
    }


    public int getPricePerUnit()
    {
        return pricePerUnit;
    }
    public void setPricePerUnit(int pricePerUnit) {this.pricePerUnit = pricePerUnit;}
}
