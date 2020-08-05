import java.util.Objects;

public class SDKLocation {
    private int x;
    private int y;

    public SDKLocation(int coordinateX, int coordinateY) {
        this.x = coordinateX;
        this.y = coordinateY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDKLocation that = (SDKLocation) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private boolean checkCoordinateIsInRange(int coordinate)
    {
        return(coordinate >= 1 && coordinate <= 50);
    }
    
    public int getX(){ 
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
