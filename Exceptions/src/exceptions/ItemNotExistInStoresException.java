package exceptions;

import logic.Item;

public class ItemNotExistInStoresException extends Exception{
    Item item;
    Integer serialID;
    public ItemNotExistInStoresException(Item item)
    {
        this.item = item;
    }
    public ItemNotExistInStoresException(Integer serialID)
    {
        this.serialID = serialID;
    }

    public Item getItem() {
        return item;
    }
}
