public class DetectInfo {
    double deg;
    double distance;

    public DetectInfo(double deg, double distance) {
        this.deg = deg;
        this.distance = distance;
    }

    public boolean equals(DetectInfo detectInfo) {
        return this.deg == detectInfo.deg && this.distance == detectInfo.distance;
    }
}
