package exceptions;

public class DuplicateItemSerialIDException extends Exception{
    String name=null;
    int serialId;
    public DuplicateItemSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    public DuplicateItemSerialIDException(int serialId, String name)
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
