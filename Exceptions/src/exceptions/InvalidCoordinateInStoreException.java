package exceptions;

public class InvalidCoordinateInStoreException extends Exception{
    String name=null;
    Integer serialID;
    int coord;

    public InvalidCoordinateInStoreException(int coord, String name, Integer serialID)
    {
        this.coord = coord;
        this.name = name;
        this.serialID = serialID;
    }
    public InvalidCoordinateInStoreException(int coord)
    {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public int getCoord() {
        return coord;
    }
}
