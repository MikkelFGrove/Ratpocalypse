package dk.lima.common.data;

public class Coordinate implements Cloneable {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate other = (Coordinate) obj;
            if ((int)this.x == (int)other.getX() && (int)this.y == (int)other.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean approxEquals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate other = (Coordinate) obj;

            double dx = Math.abs(this.x - other.getX());
            double dy = Math.abs(this.y - other.getY());

            return dx <= 5 && dy <= 5;
        }
        return false;
    }

    @Override
    public Coordinate clone() {
        return new Coordinate(this.x, this.y);
    }
}
