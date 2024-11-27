import java.util.Objects;

public class PointColor {
    private final double x;
    private final double y;
    private final ColorP color;

    public PointColor(double x, double y, ColorP color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ColorP getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointColor that = (PointColor) o;
        return x == that.x && y == that.y && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }
}
