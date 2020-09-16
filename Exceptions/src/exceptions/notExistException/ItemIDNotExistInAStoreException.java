package exceptions.notExistException;

public class ItemIDNotExistInAStoreException extends Exception{
    String storeName=null;
    int serialId;
    public ItemIDNotExistInAStoreException(int serialId, String storeName)
    {
        this.serialId = serialId;
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }
    public int getSerialId() {
        return serialId;
    }
}
