import java.util.ArrayList;

public class Mouse {
    public ArrayList<Light> lights;
    public ArrayList<Button> buttons;
    public Wheel wheel;
    public Sensor sensor;
    public Position position;
    public Body body;

    public TargetObject targetObject;
    public int sensitivity;

    public ArrayList<Device> connectedDevices;



    public String pressButton(String btn) {
        for (Button button : buttons) {
            if (button.name.equals(btn)) {
                return button.press() + " at position: " + position.point.x + " " + position.point.y;
            }
        }
        throw new IllegalArgumentException("Button not found");
    }

    public String scrollUp(int length) {

        return wheel.scrollUp(length);
    }

    public String ScrollDown(int length) {

        return wheel.scrollDown(length);
    }


    public Position move(Point currPointSensor) {
        DetectInfo detectInfo = this.detect(currPointSensor);
        double deg = detectInfo.deg;
        double distance = detectInfo.distance * sensitivity;
        this.position.move(deg, distance);
        return this.position;
    }


    public String connectToDevice(Device device) {
        this.connectedDevices.add(device);
        return "connected to device";
    }

    public DetectInfo detect(Point newPoint) {
        return sensor.detect(newPoint);
    }
}
