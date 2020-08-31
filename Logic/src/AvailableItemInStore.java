public class AvailableItemInStore extends Item {
    private int pricePerUnit;

    AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int pricePerUnit) {
        super(serialNumber, name, purchaseCategory);
        this.pricePerUnit = pricePerUnit;
    }

    AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }

    AvailableItemInStore(Item item, int price) {
        super(item);
        this.pricePerUnit = price;
    }


    public int getPricePerUnit()
    {
        return pricePerUnit;
    }
    public void setPricePerUnit(int pricePerUnit) {this.pricePerUnit = pricePerUnit;}
}
