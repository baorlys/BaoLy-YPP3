import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMouse {
    Mouse mouse;
    @BeforeEach
    public void setUp() {
        mouse = new Mouse();
        mouse.lights = new ArrayList<Light>();
        mouse.lights.add(new Light());
        mouse.buttons = new ArrayList<Button>();
        mouse.connectedDevices = new ArrayList<Device>();
        mouse.connectedDevices.add(new Device(new ScreenInfo()));
        mouse.buttons.add(new ButtonLeft());
        mouse.buttons.add(new ButtonRight());
        mouse.buttons.add(new ButtonForward());
        mouse.buttons.add(new ButtonBack());
        mouse.wheel = new Wheel();
        mouse.sensor = new Sensor();
        mouse.position = new Position(new Point(0,0));
        mouse.body = new Body();
        mouse.sensitivity = 1;
        mouse.targetObject = new TargetObject();
    }
    @Test
    public void testConnectToDevice() {
        assert(mouse.connectToDevice(new Device(new ScreenInfo())).equals("connected to device"));
    }

    @Test
    public void testPressButton() {
        assert(mouse.pressButton("Left").equals("left button clicked at position: 0 0"));
        assert(mouse.pressButton("Right").equals("right button clicked at position: 0 0"));
        assert(mouse.pressButton("Forward").equals("forward button clicked at position: 0 0"));
        assert(mouse.pressButton("Back").equals("back button clicked at position: 0 0"));
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.pressButton("Up");
        });
    }

    @Test
    public void testWheel() {
        assert(mouse.scrollUp(3).equals("scrolling up 3 units"));
        assert(mouse.ScrollDown(3).equals("scrolling down 3 units"));
    }

    @Test
    public void testDetect() {
        Point newPoint = new Point(1,1);
        DetectInfo detectInfo = mouse.detect(newPoint);
        assert(detectInfo.deg == 45.0);
        assert(detectInfo.distance == 1.4142135623730951);
    }


    @Test
    public void testMove() {
        Point newPoint = new Point(1,1);
        Position position = mouse.move(newPoint);
        assert(position.point.equals(new Point(1,1)));
    }

    @Test
    public void testMove2() {
        Point newPoint = new Point(1,1);
        Position position = mouse.move(newPoint);
        assert(position.point.equals(new Point(1,1)));
        Position position2 = mouse.move(newPoint);
        assert(position2.point.equals(new Point(2,2)));
    }











}
