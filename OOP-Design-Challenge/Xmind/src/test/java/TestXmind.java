import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestXmind {
    Xmind xmind;
    @BeforeEach
    public void setUp() {
        xmind = new XmindBuilder()
                .initBoard("Test Board")
                .initDefaultSheet("Mind Map")
                .initDefaultTopics(4)
                .build();
    }

    @Test
    // Test if the board has a sheet
    public void testXmindSheet() {
        assertEquals(1, xmind.getBoard().getSheets().size());
    }

    @Test
    // Test sheet have default topics
    public void testXmindSheetRootTopic() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        assertEquals(4, sheet.getRootTopic().getChildren().size());
    }

    @Test
    // Test add sheet to board
    public void testAddSheet() {
        xmind.getBoard().addSheet();
        assertEquals(2, xmind.getBoard().getSheets().size());
        assertEquals("Map 2", xmind.getBoard().getSheets().get(1).getName());
    }

    @Test
    // Test export xmind to png file
    public void testExportPNG() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        ExportStatus exportStatus = xmind.export(sheet, FileType.PNG, "test.png");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test export xmind to pdf file
    public void testExportPDF() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        ExportStatus exportStatus = xmind.export(sheet, FileType.PDF, "test.pdf");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test add node to root topic
    public void testAddNode() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        Node rootTopic = sheet.getRootTopic();
        sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(5, sheet.getRootTopic().getChildren().size());
        assertEquals("main topic 5", sheet.getRootTopic().getChildren().get(4).getText());
        Node childTopic = sheet.getRootTopic().getChildren().get(4);

        sheet.addNodeToCurrentTopic(childTopic);
        assertEquals(1, childTopic.getChildren().size());
        assertEquals("sub topic 1", childTopic.getChildren().get(0).getText());
    }

    @Test
    // Test add float topic
    public void testAddFloatTopic() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        sheet.addFloatTopic();
        assertEquals(5, sheet.getNodes().size());
        assertEquals("floating topic", sheet.getNodes().get(4).getText());
    }

    @Test
    // Test add node to float topic
    public void testAddNodeToFloatTopic() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        sheet.addFloatTopic();
        Node floatTopic = sheet.getNodes().get(4);
        sheet.addNodeToCurrentTopic(floatTopic);
        assertEquals(1, floatTopic.getChildren().size());
        assertEquals("sub topic 1", floatTopic.getChildren().get(0).getText());
    }

    @Test
    // Test remove node
    public void testRemoveNode() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        Node rootTopic = sheet.getRootTopic();
        sheet.addNodeToCurrentTopic(rootTopic);
        Node childTopic = sheet.getRootTopic().getChildren().get(4);
        sheet.addNodeToCurrentTopic(childTopic);
        sheet.removeNode(childTopic);
        assertEquals(4, sheet.getRootTopic().getChildren().size());
    }

    @Test
    // Test move node to another node
    public void testMoveNode() {
        Sheet sheet = xmind.getBoard().getSheets().get(0);
        Node rootTopic = sheet.getRootTopic();
        sheet.addNodeToCurrentTopic(rootTopic);
        Node childTopic = sheet.getRootTopic().getChildren().get(4);
        assertEquals(5, rootTopic.getChildren().size());
        sheet.addNodeToCurrentTopic(childTopic);
        Node subTopic = childTopic.getChildren().get(0);
        sheet.moveNode(subTopic, rootTopic);
        assertEquals(6, rootTopic.getChildren().size());
        assertEquals(0, childTopic.getChildren().size());
    }


}
