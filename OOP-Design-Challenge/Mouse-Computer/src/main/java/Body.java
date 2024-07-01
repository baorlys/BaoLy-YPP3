import java.util.List;

public class Body implements ISwitchPowerLights {
    String typeBody;
    ShapeBody shapeBody;
    List<Light> lights;
    public Body(String typeBody, ShapeBody shapeBody, List<Light> lights) {
        this.typeBody = typeBody;
        this.shapeBody = shapeBody;
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

    public String getTypeBody() {
        return typeBody;
    }

    public ShapeBody getShapeBody() {
        return shapeBody;
    }
}
