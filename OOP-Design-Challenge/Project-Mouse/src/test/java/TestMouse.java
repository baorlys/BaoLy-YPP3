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
        mouse.buttons = new ArrayList<Button>();
        mouse.buttons.add(new ButtonLeft());
        mouse.buttons.add(new ButtonRight());
        mouse.buttons.add(new ButtonForward());
        mouse.buttons.add(new ButtonBack());
        mouse.wheel = new Wheel();
        mouse.sensor = new Sensor(new Point(0,0));
        mouse.position = new Position(new Point(0,0));
        mouse.body = new Body();
        mouse.sensitivity = 1;
        mouse.targetObject = new TargetObject();
    }
    @Test
    public void TestConnectToComputer() {
        assert(mouse.connectToComputer().equals("connected to computer"));
    }

    @Test
    public void TestPressButton() {
        assert(mouse.pressButton("Left").equals("left button clicked"));
        assert(mouse.pressButton("Right").equals("right button clicked"));
        assert(mouse.pressButton("Forward").equals("forward button clicked"));
        assert(mouse.pressButton("Back").equals("back button clicked"));
        assertThrows(IllegalArgumentException.class, () -> {
            mouse.pressButton("Up");
        });
    }

    @Test
    public void TestWheel() {
        assert(mouse.scrollUp().equals("scrolling up"));
        assert(mouse.ScrollDown().equals("scrolling down"));
    }


    @Test
    public void TestMove() {
        Point newPoint = new Point(1,1);
        Position position = mouse.Move(newPoint);
        assert(position.point.equals(newPoint));
    }





}
