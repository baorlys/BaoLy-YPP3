public class Light {
    Shape shape;
    String color;
    Boolean fill;
    Border border;
    Material material;
    double brightness;

    public Light(Shape shape, String color, Boolean fill, Border border, Material material, double brightness) {
        this.shape = shape;
        this.color = color;
        this.fill = fill;
        this.border = border;
        this.material = material;
        this.brightness = brightness;
    }

    public String turnOn() {
        return "light is on";
    }

    public String turnOff() {
        return "light is off";
    }
}
