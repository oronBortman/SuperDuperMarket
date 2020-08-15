public class InvalidCoordinateException extends Exception{
    String name=null;
    int coord;
    InvalidCoordinateException(int coord, String name)
    {
        this.coord = coord;
        this.name = name;
    }
    InvalidCoordinateException(int coord)
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
