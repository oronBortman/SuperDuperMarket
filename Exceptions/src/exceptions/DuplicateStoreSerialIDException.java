package exceptions;

public class DuplicateStoreSerialIDException extends Exception{
    String name=null;
    int serialId;
    public DuplicateStoreSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    public DuplicateStoreSerialIDException(int serialId, String name)
    {
        this.serialId = serialId;
        this.name = name;
    }

    public int getSerialId() {
        return serialId;
    }

    public String getName() {
        return name;
    }
}
