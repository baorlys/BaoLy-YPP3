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

    public ScreenInfo screenInfo;



    public String pressButton(String btn) {
        for (Button button : buttons) {
            if (button.name.equals(btn)) {
                return button.press();
            }
        }
        throw new IllegalArgumentException("Button not found");
    }

    public String scrollUp() {
        return wheel.scrollUp();
    }

    public String ScrollDown() {
        return wheel.scrollDown();
    }


    public Position Move(Point newPoint) {
        DetectInfo detectInfo = sensor.detect(newPoint);
        double deg = detectInfo.deg;
        double distance = detectInfo.distance * sensitivity;
        position.move(deg, distance);
        return position;
    }


    public String connectToComputer() {
        return "connected to computer";
    }

}
