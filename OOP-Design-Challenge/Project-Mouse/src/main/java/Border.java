public class Border {
    int width;
    String style;
    String color;
    public Border(String style, String color,int width ) {
        this.width = width;
        this.style = style;
        this.color = color;
    }

    public Border(String color,int width) {
        this.width = width;
        this.color = color;
    }
}
