import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MouseTest {
    Mouse mouse;

    // Create a new mouse object before each test
    @BeforeEach
    void setUp() {
        mouse = new MouseBuilder()
                .name("Logitech G PRO")
                .body("ambidextrous",
                        Color.black,
                        List.of(new Light(Color.red, "logo"),
                                new Light(Color.yellow, "side")))
                .addButtons(List.of("left", "right","forward","backward"))
                .dpi(1600)
                .sensor("hero", 1000)
                .wheel("rubber",
                        List.of(new Light(Color.red, "wheel")))
                .connectionType("wireless")
                .targetObject(new MonitorComputer("monitor",1920,1080))
                .currentAction(MouseAction.NONE)
                .build();
    }


    @Test
    // Test the switch on of the lights of the mouse
    void testMouseLightsSwitchOn() {
        mouse.getBody().lights.get(0).powerOn();
        mouse.getWheel().lights.get(0).powerOn();
        assertEquals(PowerStatus.ON, mouse.getBody().lights.get(0).powerStatus);
        assertEquals(PowerStatus.ON, mouse.getWheel().lights.get(0).powerStatus);
    }

    @Test
    // Test the switch off of the lights of the mouse
    void testMouseLightsSwitchOff() {
        mouse.getBody().lights.get(0).powerOff();
        mouse.getWheel().lights.get(0).powerOff();
        assertEquals(PowerStatus.OFF, mouse.getBody().lights.get(0).powerStatus);
        assertEquals(PowerStatus.OFF, mouse.getWheel().lights.get(0).powerStatus);
    }

    @Test
    // Test turning on the lights of the mouse
    void testMouseLightsTurnOn() {
        mouse.getBody().turnOnLights();
        mouse.getWheel().turnOnLights();
        assertEquals(PowerStatus.ON, mouse.getBody().lights.get(0).powerStatus);
        assertEquals(PowerStatus.ON, mouse.getBody().lights.get(1).powerStatus);
        assertEquals(PowerStatus.ON, mouse.getWheel().lights.get(0).powerStatus);
    }

    @Test
    // Test turning off the lights of the mouse
    void testMouseLightsTurnOff() {
        mouse.getBody().turnOffLights();
        mouse.getWheel().turnOffLights();
        assertEquals(PowerStatus.OFF, mouse.getBody().lights.get(0).powerStatus);
        assertEquals(PowerStatus.OFF, mouse.getBody().lights.get(1).powerStatus);
        assertEquals(PowerStatus.OFF, mouse.getWheel().lights.get(0).powerStatus);
    }


    @Test
    // Test the switch on of the mouse
    void testMouseSwitchOn() throws InterruptedException{
        // Redirect System.out to a custom ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        mouse.powerOn();
        Thread.sleep(1000*5);
        String output = outContent.toString();
        long count = output.lines().count();
        assertEquals(4, count);
        assertEquals(PowerStatus.ON, mouse.getPowerStatus());

    }

    @Test
    // Test the switch off of the mouse
    void testMouseSwitchOff() {
        mouse.powerOff();
        assertEquals(PowerStatus.OFF, mouse.getPowerStatus());
    }

    @Test
    // Test the connection of the mouse
    void testMouseConnect() {
        mouse.connect();
        assertEquals(ConnectionStatus.CONNECTED, mouse.getConnectionStatus());
    }

    @Test
    // Test the disconnection of the mouse
    void testMouseDisconnect() {
        mouse.disconnect();
        assertEquals(ConnectionStatus.DISCONNECTED, mouse.getConnectionStatus());
    }

    @Test
    // Test the connection type of the mouse
    void testMouseConnectionTypeSet() {
        mouse.setConnectionType(ConnectionType.WIRED);
        assertEquals(ConnectionType.WIRED, mouse.getConnectionType());
        mouse.setConnectionType(ConnectionType.WIRELESS);
        assertEquals(ConnectionType.WIRELESS, mouse.getConnectionType());
    }

    @Test
    // Test the target object of the mouse
    void testMouseTargetObject() {
        assertEquals("monitor", mouse.getTargetObject().getName());
        assertEquals(1920, ((MonitorComputer) mouse.getTargetObject()).getWidth());
        assertEquals(1080, ((MonitorComputer) mouse.getTargetObject()).getHeight());
    }


    @Test
    // Test the move action of the mouse
    void testMouseMove() {
        Point point = new Point(100, 20);
        mouse.moveMouse(point);
        assertEquals(MouseAction.MOVE, mouse.getCurrentAction());
    }

    @Test
    // Test the click action of the mouse
    void testMouseClick() {
        Point point = new Point(100, 20);
        mouse.clickMouse("left", point);
        assertEquals(MouseAction.CLICK, mouse.getCurrentAction());
    }

    @Test
    // Test the double click action of the mouse
    void testDoubleClick() {
        Point point = new Point(100, 20);
        mouse.doubleClickMouse("left", point);
        assertEquals(MouseAction.DOUBLE_CLICK, mouse.getCurrentAction());
    }








    //region Test initial mouse

    @Test
        // Test the name of the mouse
    void testMouseName() {
        assertEquals("Logitech G PRO", mouse.getName());
    }

    @Test
        // Test the body of the mouse
    void testMouseBody() {
        assertEquals(ShapeBody.AMBIDEXTROUS, mouse.getBody().getShapeBody());
    }

    @Test
        // Test the buttons of the mouse
    void testMouseButtons() {
        assertEquals(4, mouse.getButtons().size());
    }

    @Test
        // Test the DPI of the mouse
    void testMouseDpi() {
        assertEquals(1600, mouse.getDpi());
    }

    @Test
        // Test the sensor of the mouse
    void testMouseSensor() {
        assertEquals("hero", mouse.getSensor().getName());
    }

    @Test
        // Test the wheel of the mouse
    void testMouseWheel() {
        assertEquals("rubber", mouse.getWheel().material);
    }

    @Test
        // Test the connection type of the mouse
    void testMouseConnectionType() {
        assertEquals(ConnectionType.WIRELESS, mouse.getConnectionType());
    }

    @Test
        // Test the connection status of the mouse
    void testMouseConnectionStatus() {
        assertEquals(ConnectionStatus.DISCONNECTED, mouse.getConnectionStatus());
    }

    @Test
        // Test the lights of the mouse
    void testMouseLights() {
        assertEquals(2, mouse.getBody().lights.size());
        assertEquals(1, mouse.getWheel().lights.size());
    }

    @Test
        // Test the color of the lights of the mouse
    void testMouseLightsColor() {
        assertEquals(Color.red, mouse.getBody().lights.get(0).color);
        assertEquals(Color.red, mouse.getWheel().lights.get(0).color);
    }

    @Test
        // Test the name of the lights of the mouse
    void testMouseLightsName() {
        assertEquals("logo", mouse.getBody().lights.get(0).name);
        assertEquals("wheel", mouse.getWheel().lights.get(0).name);
    }

    @Test
        // Test the power status of the lights of the mouse
    void testMouseLightsPowerStatus() {
        assertEquals(PowerStatus.OFF, mouse.getBody().lights.get(0).powerStatus);
        assertEquals(PowerStatus.OFF, mouse.getWheel().lights.get(0).powerStatus);
    }

    @Test
        // Test the current action of the mouse
    void testMouseCurrentAction() {
        assertEquals(MouseAction.NONE, mouse.getCurrentAction());
    }
    //endregion

}
