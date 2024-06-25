public class ButtonForward extends Button {
    public ButtonForward() {
        super("Forward");
    }

    @Override
    String press() {
        return "forward button clicked";
    }

    @Override
    public String doubleClick() {
        return null;
    }
}
