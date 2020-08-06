public class duplicateSerialIDException extends Exception{
    String name=null;
    int serialId;
    duplicateSerialIDException(int serialId)
    {
        this.serialId = serialId;
    }
    duplicateSerialIDException(int serialId, String name)
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
