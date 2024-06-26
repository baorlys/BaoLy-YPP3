import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMouse {
    Mouse mouse;
    @BeforeEach
    // This method is executed before each test
    // It is used to prepare the test environment (initialize the class).
    public void setUp() {
        mouse = new Mouse();

        mouse.name = "Logitech G PRO";
        mouse.brand = new Brand("Logitech", "Switzerland");

        mouse.connectedDevices = new ArrayList<Device>();
        mouse.connectedDevices.add(new Device(new ScreenInfo()));

        mouse.lights = new ArrayList<Light>();
        mouse.lights.add(new Light(
                new Shape(),
                "red",
                true,
                new Border("red",1),
                new Material("Aluminum"),
                0.3));
        mouse.lights.add(new Light(
                new Shape(),
                "yellow",
                true,
                new Border("yellow",1),
                new Material("Aluminum"),
                0.3));

        // Add buttons to the mouse
        mouse.buttons = new ArrayList<Button>();
        mouse.buttons.add(new ButtonLeft());
        mouse.buttons.add(new ButtonRight());
        mouse.buttons.add(new ButtonForward());
        mouse.buttons.add(new ButtonBack());

        //
        mouse.switchMouse = new SwitchMouse(new Shape(), "red", new Material("Aluminum"));

        // Add wheel to the mouse
        mouse.wheel = new Wheel();

        // Add sensor to the mouse
        mouse.sensor = new Sensor();

        // Add default position of pointer to the mouse
        mouse.position = new Position(new Point(0,0));

        // Add body to the mouse
        mouse.body = new Body(new Shape(), new Material("Aluminum"), "black");

        // Add default sensitivity to the mouse
        mouse.sensitivity = 1;

        // Add target object to the mouse
        mouse.targetObject = new TargetObject(new Position(new Point(1,1)));
    }

    // This test checks if the mouse is connected to a device
    @Test
    public void testConnectToDevice() {
        assert(mouse.connectToDevice(new Device(new ScreenInfo())).equals("connected to device"));
    }

    // This test checks if the mouse is disconnected from a device
    @Test
    public void testDisconnectFromDevice() {
        assert(mouse.disconnectFromDevice(new Device(new ScreenInfo())).equals("disconnected from device"));
    }

    // This test checks if the mouse's light is on
    @Test
    public void testLightOn() {
        assert(mouse.turnLightOn(0).equals("light is on"));
    }

    // This test checks if the mouse's light is off
    @Test
    public void testLightOff() {
        assert(mouse.turnLightOff(0).equals("light is off"));
    }


    @Test
    public void testTurnAllLightsOn() {
        assert(mouse.turnAllLightsOn().split("\n").length == 2);
    }

    // This test check button left press
    @Test
    public void testPressButtonLeft() {
        assert(mouse.pressButton("Left").equals("left button clicked at position: 0 0"));
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.pressButton("Up");
        });
    }

    // This test check button right press
    @Test
    public void testPressButtonRight() {
        assert(mouse.pressButton("Right").equals("right button clicked at position: 0 0"));
    }

    // This test check button forward press
    @Test
    public void testPressButtonForward() {
        assert(mouse.pressButton("Forward").equals("forward button clicked at position: 0 0"));
    }

    // This test check button back press
    @Test
    public void testPressButtonBack() {
        assert(mouse.pressButton("Back").equals("back button clicked at position: 0 0"));
    }


    // This test check button up press and throw exception
    @Test
    public void testPressButtonThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.pressButton("Up");
        });
    }


    // This test checks if the left button is double clicked
    @Test
    public void testDoubleClickButtonLeft() {
        assert(mouse.doubleClickButtonLeft().equals("left button double clicked at position: 0 0"));
    }


    // This test checks if the mouse wheel is scrolled up
    @Test
    public void testWheelScrollUp() {
        assert(mouse.scrollUp(3).equals("scrolling up 3 units"));
    }

    // This test checks if the mouse wheel is scrolled down
    @Test
    public void testWheelScrollDown() {
        assert(mouse.scrollDown(3).equals("scrolling down 3 units"));
    }


    // This test checks if the mouse is moved
    @Test
    public void testDetect() {
        Point newPointSensor = new Point(1,1);
        DetectInfo detectInfo = mouse.detectMove(newPointSensor);
        assert(detectInfo.deg == 45.0);
        assert(detectInfo.distance == 1.4142135623730951);
    }


    // This test checks if the mouse is moved and the position is updated
    @Test
    public void testMove() {
        Point newPoint = new Point(1,1);
        Position position = mouse.move(newPoint);
        assert(position.point.equals(new Point(1,1)));
    }


    // This test checks if the mouse is moved and the position is updated with multiple moves
    @Test
    public void testMove2() {
        Point newPointSensor = new Point(1,1);
        Position position = mouse.move(newPointSensor);
        assert(position.point.equals(new Point(1,1)));
        Position position2 = mouse.move(newPointSensor);
        assert(position2.point.equals(new Point(2,2)));
    }


    // This test checks if the increase sensitivity method works
    @Test
    public void testIncreaseSensitivity() {
        mouse.increaseSensitivity();
        assert(mouse.sensitivity == 2);
    }

    // This test checks if the increase sensitivity method works with a value
    @Test
    public void testIncreaseSensitivity2() {
        mouse.sensitivity = 1;
        mouse.increaseSensitivity(3);
        assert(mouse.sensitivity == 4);
    }

    // This test checks if the sensitivity is increased with a negative value
    @Test
    public void testIncreaseSensitivityWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.increaseSensitivity(-3);
        });
    }

    // This test checks if the increase sensitivity method throws an exception
    @Test
    public void testIncreaseSensitivityThrowException() {
        mouse.sensitivity = 10;
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.increaseSensitivity();
        });
    }

    // This test checks if the decrease sensitivity method works
    @Test
    public void testDecreaseSensitivity() {
        mouse.sensitivity = 2;
        mouse.decreaseSensitivity();
        assert(mouse.sensitivity == 1);
    }

    // This test checks if the decrease sensitivity method works with a value
    @Test
    public void testDecreaseSensitivity2() {
        mouse.sensitivity = 4;
        mouse.decreaseSensitivity(3);
        assert(mouse.sensitivity == 1);
    }


    // This test checks if the sensitivity is decreased with a negative value
    @Test
    public void testDecreaseSensitivityWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.decreaseSensitivity(-3);
        });
    }




    // This test checks if the decrease sensitivity method throws an exception
    @Test
    public void testDecreaseSensitivityThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.decreaseSensitivity();
        });
    }

    // This test checks if the mouse is hovering over the target object
    @Test
    public void testHoverTargetObject() {
        assert(mouse.hoverTargetObject().equals("hovering over target object"));
    }

    // This test checks if the mouse is clicking the target object
    @Test
    public void testClickTargetObject() {
        assert(mouse.clickTargetObject().equals("clicking target object"));
    }

    // This test checks if the mouse is turned on
    @Test
    public void turnOnMouse() {
        assert(mouse.turnOn().equals("mouse is on"));
    }


    // This test checks if the mouse is turned off
    @Test
    public void turnOffMouse() {
        assert(mouse.turnOff().equals("mouse is off"));
    }




}
