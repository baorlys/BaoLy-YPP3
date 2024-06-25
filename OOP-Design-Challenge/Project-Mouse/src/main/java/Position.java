public class Position {
    Point point;

    public Position(Point point) {
        this.point = point;
    }


    public void move(double deg, double distance) {
        this.point = point.getNextPoint(deg, distance);
    }

    public boolean equals(Position position) {
        return this.point.equals(position.point);
    }

}
