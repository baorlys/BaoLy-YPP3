public class ButtonRight extends Button {
    public ButtonRight() {
        super("Right");
    }

    @Override
    String press() {
        return "right button clicked";
    }

    @Override
    public String doubleClick() {
        return null;
    }
}
