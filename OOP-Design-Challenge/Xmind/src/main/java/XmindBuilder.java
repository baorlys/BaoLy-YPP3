import java.util.List;

public class XmindBuilder {
    Xmind xmind;

    public XmindBuilder() {
        xmind = new Xmind();
    }
    public XmindBuilder initBoard(String boardName) {
        xmind.setBoard(new Board(boardName));
        return this;
    }

    public XmindBuilder initDefaultSheet(String defaultSheetName) {
        xmind.getBoard().addSheet(defaultSheetName);
        return this;
    }

    public XmindBuilder initDefaultTopics(int numberOfMainTopics) {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        sheet.setRootTopic(new Node("Central Topic", NodeType.ROOT));
        for (int i = 1; i <= numberOfMainTopics; i++) {
            sheet.addNodeToCurrentTopic(sheet.getRootTopic());
        }
        return this;
    }

    public XmindBuilder initDefaultTopics(List<String> topics) {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        sheet.setRootTopic(new Node("Central Topic", NodeType.ROOT));
        for (String topic : topics) {
            sheet.getRootTopic().addChild(new Node(topic, NodeType.MAIN_TOPIC));
        }
        return this;
    }

    public Xmind build() {
        return xmind;
    }


}
