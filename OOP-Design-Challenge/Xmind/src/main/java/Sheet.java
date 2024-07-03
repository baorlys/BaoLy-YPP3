import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Sheet {
    private String name;
    private Node rootTopic;

    private List<Node> nodes = new ArrayList<>();

    public Sheet(String name) {
        this.name = name;
    }
    public Sheet(String name, Node rootTopic) {
        this.name = name;
        this.rootTopic = rootTopic;
        nodes.add(rootTopic);
    }

    public List<Node> getNodes() {
        return nodes;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getRootTopic() {
        return rootTopic;
    }

    public void setRootTopic(Node rootTopic) {
        this.rootTopic = rootTopic;
    }


    public void addNodeToCurrentTopic(Node currentTopic) {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        Node newNode = new Node(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType);
        newNode.setParent(currentTopic);
        currentTopic.addChild(newNode);
        nodes.add(newNode);
    }



    public void addFloatTopic() {
        Node floatTopic = new Node(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
              , NodeType.FLOATING_TOPIC);
        nodes.add(floatTopic);
    }

    public void removeNode(Node topic) {
        Node parent = topic.getParent();
        parent.removeChild(topic);
        nodes.remove(topic);
    }

    public void moveNode(Node nodeIsMoved, Node parentNode) {
        Node parent = nodeIsMoved.getParent();
        parent.removeChild(nodeIsMoved);
        nodeIsMoved.setParent(parentNode);
        parentNode.addChild(nodeIsMoved);
    }
}
