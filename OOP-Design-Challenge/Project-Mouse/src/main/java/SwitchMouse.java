public class SwitchMouse {
    Shape shape;
    String color;
    Material material;

    public SwitchMouse(Shape shape, String color, Material material) {
        this.shape = shape;
        this.color = color;
        this.material = material;
    }

    public String on() {
        return "mouse is on";
    }

    public String off() {
        return "mouse is off";
    }
}
