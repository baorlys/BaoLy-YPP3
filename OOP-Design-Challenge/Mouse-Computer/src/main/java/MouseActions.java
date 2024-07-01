import java.util.HashMap;

public interface MouseActions {
    Point moveMouse(Point point);
    Point clickMouse(String buttonName, Point point);
    Point doubleClickMouse(String buttonName, Point point);
    Point scrollUpMouse(int scrollAmount, Point point);
    Point scrollDownMouse(int scrollAmount, Point point);
    Point dragMouse(Point startPoint, Point endPoint);
    Point moveMouseToElement(String elementName, Point point);
    Point clickMouseOnElement(String elementName, String buttonName, Point point);
    Point doubleClickMouseOnElement(String elementName, String buttonName, Point point);
    Point scrollUpMouseOnElement(String elementName, int scrollAmount, Point point);
    Point scrollDownMouseOnElement(String elementName, int scrollAmount, Point point);
    Point dragMouseOnElement(String elementName, Point startPoint, Point endPoint);
    Point hoverMouseOverElement(String elementName, Point point);
    Point rightClickMouseOnElement(String elementName, Point point);
}
