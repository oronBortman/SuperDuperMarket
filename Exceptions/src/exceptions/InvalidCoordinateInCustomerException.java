package exceptions;

public class InvalidCoordinateInCustomerException extends Exception{
    String name=null;
    int coord;
    Integer serialID;

    public InvalidCoordinateInCustomerException(int coord, String name, Integer serialID)
    {
        this.coord = coord;
        this.name = name;
        this.serialID = serialID;
    }
    public InvalidCoordinateInCustomerException(int coord)
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
