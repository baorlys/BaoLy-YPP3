import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MouseBuilder {
    private Mouse mouse;
    public MouseBuilder() {
        this.mouse = new Mouse();

    }

    public MouseBuilder name(String name) {
        this.mouse.setName(name);
        return this;
    }

    public MouseBuilder addButtons(List<String> buttons) {
        List<Button> newButtons = new ArrayList<>();
        for(String button : buttons) {
            Button newBtn = new Button(button, Color.BLACK, ShapeButton.valueOf(button.toUpperCase()));
            newButtons.add(newBtn);
        }
        this.mouse.setButtons(newButtons);
        return this;
    }

    public MouseBuilder body(String typeBody, Color red, List<Light> lights) {
        this.mouse.setBody(new Body(typeBody, ShapeBody.valueOf(typeBody.toUpperCase()), lights));
        return this;
    }


    public MouseBuilder dpi(int dpi) {
        this.mouse.setDpi(dpi);
        return this;
    }

    public MouseBuilder sensor(String sensorName, int timeResponse) {
        this.mouse.setSensor(new Sensor(sensorName, timeResponse));
        this.mouse.setTimeResponse(timeResponse);
        return this;
    }

    public MouseBuilder connectionType(String connectionType) {
        this.mouse.setConnectionType(ConnectionType.valueOf(connectionType.toUpperCase()));
        return this;
    }


    public MouseBuilder wheel(String material, List<Light> lights) {
        this.mouse.setWheel(new Wheel(material, lights));
        return this;
    }


    public MouseBuilder targetObject(TargetObject targetObject) {
        this.mouse.setTargetObject(targetObject);
        return this;
    }


    public MouseBuilder currentAction(MouseAction action) {
        this.mouse.setCurrentAction(action);
        return this;
    }

    public Mouse build() {
        return this.mouse;
    }

}
