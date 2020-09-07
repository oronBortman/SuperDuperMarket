package exceptions;

public class DuplicateCustomerSerialIDException extends Exception{
    String name=null;
    int serialId;
    public DuplicateCustomerSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    public DuplicateCustomerSerialIDException(int serialId, String name)
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
