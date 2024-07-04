import java.util.*;
import java.util.stream.Collectors;

public class Sheet {
    private boolean isBalanced;
    private String name;
    private Node rootTopic;

    HashSet<RelationShip> nodes = new HashSet<>();



    public Sheet(String name) {
        this.name = name;
    }
    public Sheet(String name, Node rootTopic) {
        this.name = name;
        this.rootTopic = rootTopic;
        this.addNewNode(rootTopic);
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
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


    public void addNewNode(Node node) {
        nodes.add(new RelationShip(node));
    }

    public List<Node> getAllNodes() {
        return nodes.stream().map(RelationShip::getNode).collect(Collectors.toList());
    }

    public Node addNodeToCurrentTopic(Node currentTopic) {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        Node newNode = new Node(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType);
        newNode.setParent(currentTopic);
        currentTopic.addChild(newNode);
        this.addNewNode(newNode);
        return newNode;
    }



    public Node addFloatTopic() {
        Node floatTopic = new Node(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
              , NodeType.FLOATING_TOPIC);
        this.addNewNode(floatTopic);
        return floatTopic;
    }

    public void removeNode(Node topic) {
        Node parent = topic.getParent();
        parent.removeChild(topic);
        nodes.removeIf(relationShip -> relationShip.getNode().equals(topic));
    }

    public void moveNode(Node nodeIsMoved, Node parentNode) {
        nodeIsMoved.moveToParent(parentNode);
    }
    public void moveNode(Node node) {
        node.removeParent();
    }

    public void createRelationship(Node fromNode, Node toNode) {
        nodes.stream().filter(relationShip -> relationShip.getNode().equals(fromNode))
                .forEach(relationShip -> relationShip.addRelation(toNode));
    }


    public Node getNode(Node node) {
        return nodes.stream().map(RelationShip::getNode)
                .filter(relationShipNode -> relationShipNode.equals(node))
                .findFirst().orElse(null);
    }

    public RelationShip getRelationships(Node node) {
        return nodes.stream().filter(relationShip -> relationShip.getNode().equals(node))
                .findFirst().orElse(null);
    }

    public void removeRelationship(Node node1, Node node2) {
        nodes.stream().filter(relationShip -> relationShip.getNode().equals(node1))
                .forEach(relationShip -> relationShip.removeRelation(node2));
    }

    public void changeRelationshipName(Node node1, Node node2, String relationshipName) {
        nodes.stream().filter(relationShip -> relationShip.getNode().equals(node1))
                .forEach(relationShip -> relationShip.changeRelationName(node2, relationshipName));

    }


    public void balanceMap() {
        isBalanced = true;
    }
}
