import java.util.EnumMap;
import java.util.Map;

public class AddNodeFactory {
    private static final Map<NodeType, NodeType> childNodeType = new EnumMap<>(NodeType.class);
    private AddNodeFactory() {

    }

    static {
        childNodeType.put(NodeType.ROOT, NodeType.MAIN_TOPIC);
        childNodeType.put(NodeType.MAIN_TOPIC, NodeType.SUB_TOPIC);
        childNodeType.put(NodeType.FLOATING_TOPIC, NodeType.SUB_TOPIC);
        childNodeType.put(NodeType.SUB_TOPIC, NodeType.SUB_TOPIC);
    }

    public static NodeType getChildNodeType(NodeType type) {
        return childNodeType.get(type);
    }
}

