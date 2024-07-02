public interface IMouseActions extends IConnectionType {
    void moveMouse(Point point);
    void clickMouse(String buttonName, Point point);
    void doubleClickMouse(String buttonName, Point point);
    void scrollUpMouse(int scrollAmount, Point point);
    void scrollDownMouse(int scrollAmount, Point point);
    void dragMouse(Point startPoint, Point endPoint);
    void moveMouseToElement(String elementName, Point point);
    void clickMouseOnElement(String elementName, String buttonName, Point point);
    Point doubleClickMouseOnElement(String elementName, String buttonName, Point point);
    Point scrollUpMouseOnElement(String elementName, int scrollAmount, Point point);
    Point scrollDownMouseOnElement(String elementName, int scrollAmount, Point point);
    Point dragMouseOnElement(String elementName, Point startPoint, Point endPoint);
    Point hoverMouseOverElement(String elementName, Point point);
    Point rightClickMouseOnElement(String elementName, Point point);
}
