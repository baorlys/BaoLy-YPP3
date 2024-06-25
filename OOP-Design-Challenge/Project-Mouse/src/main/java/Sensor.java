public class Sensor {
    Point prev;

    public Sensor(Point point) {
        this.prev = point;
    }

    public DetectInfo detect(Point curr) {
        double deg = prev.calDegree(curr);
        double distance = prev.calDistance(curr);
        this.prev = curr;
        return new DetectInfo(deg, distance);
    }


}


