public class ButtonBack extends Button {
    public ButtonBack() {
        super("Back");
    }

    @Override
    String press() {
        return "back button clicked";
    }

    @Override
    public String doubleClick() {
        return null;
    }
}
