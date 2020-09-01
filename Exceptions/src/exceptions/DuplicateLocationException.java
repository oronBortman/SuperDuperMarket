package exceptions;

public class DuplicateLocationException extends Exception{
    int coordinateX;
    int coordinateY;
    String name = null;
    public DuplicateLocationException(int x, int y)
    {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public DuplicateLocationException(int x, int y, String name)
    {
        this.coordinateX = x;
        this.coordinateY = y;
        this.name = name;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public String getName() {
        return name;
    }
}
