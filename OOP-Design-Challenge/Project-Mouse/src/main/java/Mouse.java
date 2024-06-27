import java.security.DigestInputStream;
import java.util.ArrayList;

public class Mouse {

    // Config max and min sensitivity
    static final int MAX_SENSITIVITY = 10;
    static final int MIN_SENSITIVITY = 1;
    public ArrayList<Light> lights;
    public ArrayList<Button> buttons;
    public Wheel wheel;
    public Sensor sensor;
    public Position pointer;
    public Body body;

    public TargetObject targetObject;
    public int sensitivity;

    public ArrayList<Device> connectedDevices;
    public String name;
    public Brand brand;
    public SwitchMouse switchMouse;

    public Boolean isOn;


    // This method is used to press a button
    public String pressButton(String btn) {
        for (Button button : buttons) {
            if (button.name.equals(btn)) {
                return button.press() + " at position: " + pointer.point.x + " " + pointer.point.y;
            }
        }
        throw new IllegalArgumentException("Button not found");
    }

    // This method is used to scroll up the wheel
    public String scrollUp(int length) {
        return wheel.scrollUp(length);
    }

    // This method is used to scroll down the wheel
    public String scrollDown(int length) {
        return wheel.scrollDown(length);
    }


    // This method is used to move the pointer in screen to a new position by sensor
    public Position move(Point currPointSensor) {
        DetectInfo detectInfo = this.detectMove(currPointSensor);
        double deg = detectInfo.deg;
        double distance = detectInfo.distance * sensitivity;
        this.pointer.move(deg, distance);
        return this.pointer;
    }


    // This method is used to detect the move of the mouse
    public DetectInfo detectMove(Point newPointSensor) {
        return sensor.detectMove(newPointSensor);
    }


    // This method is used to connect to a device
    public String connectToDevice(Device device) {
        this.connectedDevices.add(device);
        return "connected to device";
    }

    // This method is used to disconnect from a device
    public String disconnectFromDevice(Device device) {
        this.connectedDevices.remove(device);
        return "disconnected from device";
    }


    // This method is used to turn on the light of the mouse
    public String turnLightOn(int indexLight) {
        return lights.get(indexLight).turnOn();
    }

    // This method is used to turn off the light of the mouse
    public String turnLightOff(int indexLight) {
        return lights.get(indexLight).turnOff();
    }

    // This method is used to turn on all the lights of the mouse
    public String turnAllLightsOn() {
        StringBuilder result = new StringBuilder();
        for (Light light : lights) {
            result.append(light.turnOn()).append("\n");
        }
        return result.toString();
    }

    // This method is used to double-click the left button
    public String doubleClickButtonLeft() {
        for (Button button : buttons) {
            if (button.name.equals("Left")) {
                return button.doubleClick() + " at position: " + pointer.point.x + " " + pointer.point.y;
            }
        }
        throw new IllegalArgumentException("Button not found");
    }

    // This method is used to increase the sensitivity of the mouse
    public void increaseSensitivity() {
        if(this.sensitivity < MAX_SENSITIVITY)
            this.sensitivity++;
        else
            throw new IllegalArgumentException("Sensitivity cannot be more than 10");
    }

    // This method is used to increase the sensitivity of the mouse with a specific value
    public void increaseSensitivity(int i) {
        if(i < 0)
            throw new IllegalArgumentException("Sensitivity cannot be negative");
        if(this.sensitivity + i < MAX_SENSITIVITY)
            this.sensitivity += i;
        else
            throw new IllegalArgumentException("Sensitivity cannot be more than 10");
    }

    // This method is used to decrease the sensitivity of the mouse
    public void decreaseSensitivity() {
        if(this.sensitivity > MIN_SENSITIVITY)
            this.sensitivity--;
        else
            throw new IllegalArgumentException("Sensitivity cannot be less than 1");
    }

    // This method is used to decrease the sensitivity of the mouse with a specific value
    public void decreaseSensitivity(int i) {
        if(i < 0)
            throw new IllegalArgumentException("Sensitivity cannot be negative");
        if(this.sensitivity + i > MIN_SENSITIVITY)
            this.sensitivity -= i;
        else
            throw new IllegalArgumentException("Sensitivity cannot be less than 10");
    }


    // This method is used to hover the target object
    public String hoverTargetObject() {
        return targetObject.hover();
    }


    // This method is used to click the target object
    public String clickTargetObject() {
        return targetObject.click();
    }

    // This method is used to turn on the mouse
    public String turnOn() {
        this.isOn = true;
        return switchMouse.on();
    }


    // This method is used to turn off the mouse
    public String turnOff() {
        this.isOn = false;
        return switchMouse.off();
    }


    public int getMaxScreenSize() {
        int maxScreenWidth = 0;
        for (Device device : connectedDevices) {
            maxScreenWidth += device.screenInfo.SCREEN_WIDTH;
        }
        return maxScreenWidth;
    }
}
