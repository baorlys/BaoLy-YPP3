public class TargetObject {
    Position position;

    public TargetObject(Position position) {
        this.position = position;
    }

    public String hover() {
        return "hovering over target object";
    }


    public String click() {
        return "clicking target object";
    }
}
