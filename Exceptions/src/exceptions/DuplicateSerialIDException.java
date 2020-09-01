package exceptions;

public class DuplicateSerialIDException extends Exception{
    String name=null;
    int serialId;
    public DuplicateSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    public DuplicateSerialIDException(int serialId, String name)
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
