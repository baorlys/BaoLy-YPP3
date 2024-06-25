import java.util.ArrayList;

public class Mouse {
    static final int MAX_SENSITIVITY = 10;
    static final int MIN_SENSITIVITY = 1;
    public ArrayList<Light> lights;
    public ArrayList<Button> buttons;
    public Wheel wheel;
    public Sensor sensor;
    public Position position;
    public Body body;

    public TargetObject targetObject;
    public int sensitivity;

    public ArrayList<Device> connectedDevices;
    public String name;
    public Brand brand;


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

    public String scrollDown(int length) {

        return wheel.scrollDown(length);
    }


    public Position move(Point currPointSensor) {
        DetectInfo detectInfo = this.detectMove(currPointSensor);
        double deg = detectInfo.deg;
        double distance = detectInfo.distance * sensitivity;
        this.position.move(deg, distance);
        return this.position;
    }

    public DetectInfo detectMove(Point newPointSensor) {
        return sensor.detectMove(newPointSensor);
    }


    public String connectToDevice(Device device) {
        this.connectedDevices.add(device);
        return "connected to device";
    }



    public String turnLightOn(int indexLight) {
        return lights.get(indexLight).turnOn();
    }

    public String turnLightOff(int indexLight) {
        return lights.get(indexLight).turnOff();
    }

    public String doubleClickButtonLeft() {
        for (Button button : buttons) {
            if (button.name.equals("Left")) {
                return button.doubleClick() + " at position: " + position.point.x + " " + position.point.y;
            }
        }
        throw new IllegalArgumentException("Button not found");
    }

    public void increaseSensitivity() {
        if(this.sensitivity < MAX_SENSITIVITY)
            this.sensitivity++;
        else
            throw new IllegalArgumentException("Sensitivity cannot be more than 10");
    }

    public void decreaseSensitivity() {
        if(this.sensitivity > MIN_SENSITIVITY)
            this.sensitivity--;
        else
            throw new IllegalArgumentException("Sensitivity cannot be less than 1");
    }

    public String disconnectFromDevice(Device device) {
        this.connectedDevices.remove(device);
        return "disconnected from device";
    }
}
