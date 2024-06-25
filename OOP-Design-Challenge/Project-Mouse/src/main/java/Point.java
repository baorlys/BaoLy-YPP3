public class Point {
    int x;
    int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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

    public double calDistance(Point nextPoint) {
        double x = nextPoint.x - this.x;
        double y = nextPoint.y - this.y;
        return Math.sqrt(x*x + y*y);
    }

    public Point getNextPoint(double deg, double distance) {
        double rad = Math.toRadians(deg);
        int x = (int) (this.x + distance * Math.cos(rad));
        int y = (int) (this.y + distance * Math.sin(rad));
        return new Point(x, y);
    }

    public boolean equals(Point point) {
        return this.x == point.x && this.y == point.y;
    }
}