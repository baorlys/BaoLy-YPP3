public class MonitorComputer extends TargetObject{
    int width;
    int height;

    // Default pointer
    Point pointer = new Point(0, 0, 0);

    public MonitorComputer(String name, int width, int height) {
        super(name);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getPointer() {
        return pointer;
    }

    public void setPointer(Point pointer) {
        this.pointer = pointer;
    }


}
