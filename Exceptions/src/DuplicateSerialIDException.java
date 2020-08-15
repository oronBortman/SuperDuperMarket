public class DuplicateSerialIDException extends Exception{
    String name=null;
    int serialId;
    DuplicateSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    DuplicateSerialIDException(int serialId, String name)
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
