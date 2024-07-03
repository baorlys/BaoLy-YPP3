import java.util.ArrayList;
import java.util.List;

public class Node {
    private String text;

    private Node parent;
    private List<Node> children = new ArrayList<>();

    private NodeType type;

    public Node(String text, NodeType type) {
        this.text = text;
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setChildren(List<Node> nodes) {
        this.children = nodes;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }


    public void addChild(Node topic) {
        children.add(topic);
    }

    public void removeChild(Node topic) {
        children.remove(topic);
    }
}
