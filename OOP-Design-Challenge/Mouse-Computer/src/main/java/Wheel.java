import java.util.List;

public class Wheel implements ISwitchPowerLights {
    String material;
    List<Light> lights;
    public Wheel(String material) {
        this.material = material;
    }

    public Wheel(String material, List<Light> lights) {
        this.material = material;
        this.lights = lights;
    }

    @Override
    public void turnOnLights() {
        for (Light light : lights) {
            light.powerOn();
        }
    }

    @Override
    public void turnOffLights() {
        for (Light light : lights) {
            light.powerOff();
        }
    }
}
