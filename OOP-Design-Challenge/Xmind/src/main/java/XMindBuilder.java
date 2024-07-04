
public class XMindBuilder {
    Board xmind;
    private static final String DEFAULT_BOARD_NAME = "Board";
    private static final String DEFAULT_SHEET_NAME = "Mind Map";
    private static final int DEFAULT_NUMBER_OF_MAIN_TOPICS = 4;

    private static final String DEFAULT_ROOT_NAME = "Central Topic";

    public XMindBuilder() {
        xmind = new Board();
    }

    public XMindBuilder initBoard() {
        xmind.setName(DEFAULT_BOARD_NAME);
        return this;
    }

    public XMindBuilder initBoard(String boardName) {
        xmind.setName(boardName);
        return this;
    }

    public XMindBuilder initDefaultSheet() {
        xmind.addSheet(DEFAULT_SHEET_NAME);
        return this;
    }

    public XMindBuilder initDefaultSheet(String defaultSheetName) {
        xmind.addSheet(defaultSheetName);
        return this;
    }

    public XMindBuilder initDefaultTopics() {
        Sheet sheet = xmind.getFirstSheet();
        sheet.setRootTopic(new Node(DEFAULT_ROOT_NAME, NodeType.ROOT));
        Node rootTopic = sheet.getRootTopic();
        for (int i = 0; i < DEFAULT_NUMBER_OF_MAIN_TOPICS; i++) {
            sheet.addNodeToCurrentTopic(rootTopic);
        }
        return this;
    }

    public Board build() {
        return xmind;
    }



}
