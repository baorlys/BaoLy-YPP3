public class ButtonLeft extends Button {

    public ButtonLeft() {
        super("Left");
    }

    @Override
    String press() {
        return "left button clicked";
    }
}
