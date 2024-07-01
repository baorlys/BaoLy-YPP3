public class Point {
    int x;
    int y;
    int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public double calDegree(Point nextPoint) {
        double x = nextPoint.x - this.x;
        double y = nextPoint.y - this.y;
        double deg = Math.toDegrees(Math.atan2(y, x));
        if (deg < 0) {
            deg += 360;
        }
        return deg;
    }
}
