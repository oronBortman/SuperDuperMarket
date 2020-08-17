public class ItemNotExistInStoresException extends Exception{
    Item item;
    ItemNotExistInStoresException(Item item)
    {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
