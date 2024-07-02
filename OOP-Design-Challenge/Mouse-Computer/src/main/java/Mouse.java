import java.util.List;

public class Mouse implements ISwitchable, IMouseActions {
    private TargetObject targetObject;
    private String name;
    private List<Button> buttons;
    private int dpi;
    private Sensor sensor;
    private IConnectionType connectionType;
    private Wheel wheel;
    private Body body;

    // Default value for powerStatus is OFF
    private PowerStatus powerStatus = PowerStatus.OFF;

    // Default value for currentAction is NONE
    private MouseAction currentAction = MouseAction.NONE;

    // Default point for mouseAction is (0,0)
    private final Point currentPositionSensor = new Point(0, 0);

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

    public IConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(IConnectionType connectionType) {
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

    @Override
    public Pair<ConnectionType, ConnectionStatus> connect() {
        return this.connectionType.connect();
    }

    @Override
    public ConnectionStatus disconnect() {
        return this.connectionType.disconnect();
    }

    @Override
    public void powerOn() {
        sensor.detecting();
        this.powerStatus = PowerStatus.ON;
    }

    @Override
    public void powerOff() {

        this.powerStatus = PowerStatus.OFF;
    }


    @Override
    public void moveMouse(Point point) {
        currentAction = MouseAction.MOVE;
    }

    @Override
    public void clickMouse(String buttonName, Point point) {
        currentAction = MouseAction.CLICK;
    }

    @Override
    public void doubleClickMouse(String buttonName, Point point) {
        currentAction = MouseAction.DOUBLE_CLICK;
    }

    @Override
    public void scrollUpMouse(int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_UP;
    }

    @Override
    public void scrollDownMouse(int scrollAmount, Point point) {
        currentAction = MouseAction.SCROLL_DOWN;
    }


    @Override
    public void dragMouse(Point startPoint, Point endPoint) {
        currentAction = MouseAction.DRAG;
    }

    @Override
    public void moveMouseToElement(String elementName, Point point) {
        currentAction = MouseAction.MOVE_TO_ELEMENT;
    }

    @Override
    public void clickMouseOnElement(String elementName, String buttonName, Point point) {
        currentAction = MouseAction.CLICK_ON_ELEMENT;
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
