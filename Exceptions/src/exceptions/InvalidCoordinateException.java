package exceptions;

public class InvalidCoordinateException extends Exception{
    String name=null;
    int coord;
    public InvalidCoordinateException(int coord, String name)
    {
        this.coord = coord;
        this.name = name;
    }
    public InvalidCoordinateException(int coord)
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
