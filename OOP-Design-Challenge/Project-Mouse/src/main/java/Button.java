public abstract class Button {
    String name;
    Shape shape;
    public Button(String name) {
        this.name = name;
    }
    abstract String press();
}
