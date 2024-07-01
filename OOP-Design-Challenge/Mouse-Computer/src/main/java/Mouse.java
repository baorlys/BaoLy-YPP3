import java.util.List;

public class Mouse implements ISwitchable, IConnectable, IMouseActions {
    private TargetObject targetObject;
    private String name;
    private List<Button> buttons;
    private int dpi;
    private Sensor sensor;
    private ConnectionType connectionType;
    private Wheel wheel;
    private Body body;

    // Default value for connectionStatus is DISCONNECTED
    private ConnectionStatus connectionStatus = ConnectionStatus.DISCONNECTED;
    // Default value for powerStatus is OFF
    private PowerStatus powerStatus = PowerStatus.OFF;

    // Default value for currentAction is NONE
    private MouseAction currentAction = MouseAction.NONE;

    // Default point for mouseAction is (0,0)
    private final Point currentPositionSensor = new Point(0, 0);
    private int timeResponse;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setButtons(List<Button> newButtons) {
        this.buttons = newButtons;
    }


    public List<Button> getButtons() {
        return buttons;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public PowerStatus getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(PowerStatus powerStatus) {
        this.powerStatus = powerStatus;
    }

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public MouseAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(MouseAction currentAction) {
        this.currentAction = currentAction;
    }

    public void setTimeResponse(int timeResponse) {
        this.timeResponse = timeResponse;
    }

    public int getTimeResponse() {
        return timeResponse;
    }

    @Override
    public ConnectionStatus connect() {
        return this.connectionStatus = ConnectionStatus.CONNECTED;
    }

    @Override
    public ConnectionStatus disconnect() {
        return this.connectionStatus = ConnectionStatus.DISCONNECTED;
    }

    @Override
    public PowerStatus powerOn() {
        sensor.detecting();
        return this.powerStatus = PowerStatus.ON;
    }

    @Override
    public PowerStatus powerOff() {

        return this.powerStatus = PowerStatus.OFF;
    }


    @Override
    public Point moveMouse(Point point) {
        currentAction = MouseAction.MOVE;
        return null;
    }

    @Override
    public Point clickMouse(String buttonName, Point point) {
        currentAction = MouseAction.CLICK;
        return null;
    }

    @Override
    public Point doubleClickMouse(String buttonName, Point point) {
        currentAction = MouseAction.DOUBLE_CLICK;
        return null;
    }

    @Override
    public Point scrollUpMouse(int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_UP;
        return null;
    }

    @Override
    public Point scrollDownMouse(int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_DOWN;
        return null;
    }


    @Override
    public Point dragMouse(Point startPoint, Point endPoint) {
        currentAction = MouseAction.DRAG;
        return null;
    }

    @Override
    public Point moveMouseToElement(String elementName, Point point) {
        currentAction = MouseAction.MOVE_TO_ELEMENT;
        return null;
    }

    @Override
    public Point clickMouseOnElement(String elementName, String buttonName, Point point) {
        currentAction = MouseAction.CLICK_ON_ELEMENT;
        return null;
    }

    @Override
    public Point doubleClickMouseOnElement(String elementName, String buttonName, Point point) {
        currentAction = MouseAction.DOUBLE_CLICK_ON_ELEMENT;
        return null;
    }

    @Override
    public Point scrollUpMouseOnElement(String elementName, int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_UP_ON_ELEMENT;
        return null;
    }

    @Override
    public Point scrollDownMouseOnElement(String elementName, int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_DOWN_ON_ELEMENT;
        return null;
    }

    @Override
    public Point dragMouseOnElement(String elementName, Point startPoint, Point endPoint) {
        currentAction = MouseAction.DRAG_ON_ELEMENT;
        return null;
    }

    @Override
    public Point hoverMouseOverElement(String elementName, Point point) {
        currentAction = MouseAction.HOVER_OVER_ELEMENT;
        return null;
    }

    @Override
    public Point rightClickMouseOnElement(String elementName, Point point) {
        currentAction = MouseAction.RIGHT_CLICK_ON_ELEMENT;
        return null;
    }


}
