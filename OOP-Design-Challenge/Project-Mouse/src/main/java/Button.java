public abstract class Button {
    String name;
    Shape shape;
    public Button(String name) {
        this.name = name;
    }
    abstract String press();


    public String doubleClick() {
        return null;
    };
}
