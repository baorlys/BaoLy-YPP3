import java.awt.*;

public class Light implements ISwitchable {
    Color color;
    String name;
    PowerStatus powerStatus = PowerStatus.OFF;
    public Light(Color color, String name, PowerStatus powerStatus) {
        this.color = color;
        this.name = name;
        this.powerStatus = powerStatus;
    }

    public Light(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public PowerStatus powerOn() {
        powerStatus = PowerStatus.ON;
        return powerStatus;
    }

    @Override
    public PowerStatus powerOff() {
        powerStatus = PowerStatus.OFF;
        return powerStatus;
    }
}
