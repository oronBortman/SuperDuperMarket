public class SerialIDNotExistException extends Exception{
    String name=null;
    int serialId;
    SerialIDNotExistException(int serialId)
    {
        this.serialId = serialId;
    }
    SerialIDNotExistException(int serialId, String name)
    {
        this.serialId = serialId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSerialId() {
        return serialId;
    }
}
