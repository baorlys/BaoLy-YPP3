import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationShip {
    private Node node;

    private List<HashMap<Node,String>> relation;

    private static final String DEFAULT_RELATIONSHIP_NAME = "relationship";

    public RelationShip(Node node) {
        this.node = node;
        this.relation = new ArrayList<>();
    }


    public Node getNode() {
        return node;
    }


    public void setNode(Node node) {
        this.node = node;
    }

    public List<HashMap<Node,String>> getRelation() {
        return relation;
    }

    public void setRelation(List<HashMap<Node,String>> relation) {
        this.relation = relation;
    }


    public void addRelation(Node node) {
        HashMap<Node, String> relationMap = new HashMap<>();
        relationMap.put(node, DEFAULT_RELATIONSHIP_NAME);
        relation.add(relationMap);
    }

    public void changeRelationName(Node node, String relationName) {
        relation.stream().filter(relationMap -> relationMap.containsKey(node))
                .findFirst().ifPresent(relationMap -> relationMap.put(node, relationName));
    }

    public void removeRelation(Node node) {
        relation.stream().filter(relationMap -> relationMap.containsKey(node))
                .findFirst().ifPresent(relation::remove);
    }

    public boolean isRelated(Node node) {
        return relation.stream().anyMatch(relationMap -> relationMap.containsKey(node));
    }
}
