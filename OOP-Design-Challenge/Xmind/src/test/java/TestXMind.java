import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestXMind {
    Board xMind;
    @BeforeEach
    void setUp() {
        xMind = new XMindBuilder()
                .initBoard()
                .initDefaultSheet()
                .initDefaultTopics()
                .build();
    }

    @Test
    // Test if the board has a sheet
    void testXMindSheet() {
        assertEquals(1, xMind.getSheets().size());
    }

    @Test
    // Test sheet have default topics
    void testXMindSheetRootTopic() {
        Sheet sheet = xMind.getFirstSheet();
        assertEquals(4, sheet.getRootTopic().getChildren().size());
    }

    @Test
    // Test add sheet to board
    void testAddSheet() {
        xMind.addSheet();
        assertEquals(2, xMind.getSheets().size());
    }

    @Test
    // Test export xMind to png file
    void testExportPNG() {
        Sheet sheet = xMind.getFirstSheet();
        ExportStatus exportStatus = xMind.export(sheet, FileType.PNG, "filename");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test export xMind to pdf file
    void testExportPDF() {
        Sheet sheet = xMind.getFirstSheet();
        ExportStatus exportStatus = xMind.export(sheet, FileType.PDF, "filename");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test add node to root topic
    void testAddNode() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(NodeType.MAIN_TOPIC, sheet.getNode(newMainTopic).getType());
        Node newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
    }

    @Test
    // Test add float topic
    void testAddFloatTopic() {
        Sheet sheet = xMind.getFirstSheet();
        Node floatTopic = sheet.addFloatTopic();
        assertEquals(NodeType.FLOATING_TOPIC, sheet.getNode(floatTopic).getType());
    }

    @Test
    // Test add node to float topic
    void testAddNodeToFloatTopic() {
        Sheet sheet = xMind.getFirstSheet();
        Node newFloatTopic = sheet.addFloatTopic();
        Node newSubTopic = sheet.addNodeToCurrentTopic(newFloatTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
    }

    @Test
    // Test remove node
    void testRemoveNode() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic =sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(5, sheet.getAllNodes().size());
        sheet.removeNode(newMainTopic);
        assertEquals(4, sheet.getAllNodes().size());
    }

    @Test
    // Test move node to another node
    void testMoveNode() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(5, rootTopic.getChildren().size());
        Node newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
        sheet.moveNode(newSubTopic, rootTopic);
        assertEquals(6, rootTopic.getChildren().size());
        assertEquals(0, newMainTopic.getChildren().size());
    }

    @Test
    // Test move node to floating topic
    void testMoveNodeToFloatTopic() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(5, rootTopic.getChildren().size());
        sheet.moveNode(newMainTopic);
        assertEquals(4, rootTopic.getChildren().size());
        assertEquals(NodeType.FLOATING_TOPIC, newMainTopic.getType());

    }
    @Test
    // Test create relationship between nodes
    void testCreateRelationship() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        Node newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
        sheet.createRelationship(newSubTopic, rootTopic);
        assertTrue(sheet.getRelationships(newSubTopic).isRelated(rootTopic));
    }


    @Test
    // Test remove relationship between nodes
    void testRemoveRelationship() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.removeRelationship(newMainTopic, rootTopic);
        assertFalse(sheet.getRelationships(newMainTopic).isRelated(rootTopic));
    }


    @Test
    // Test change relationship name
    void testChangeRelationshipName() {
        Sheet sheet = xMind.getFirstSheet();
        Node rootTopic = sheet.getRootTopic();
        Node newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.changeRelationshipName(newMainTopic, rootTopic, "new relationship name");
        assertTrue(sheet.getRelationships(newMainTopic)
                .getRelation()
                .stream()
                .anyMatch(relationMap -> relationMap.containsValue("new relationship name")));
    }


    @Test
    // Test auto balance map
    void testAutoBalanceMap() {
        Sheet sheet = xMind.getFirstSheet();
        sheet.addNodeToCurrentTopic(sheet.getRootTopic());
        assertFalse(sheet.isBalanced());
        sheet.balanceMap();
        assertTrue(sheet.isBalanced());
    }

//    @Test
//    // Test change view mode
//    void testChangeViewMode() {
//        Sheet sheet = xMind.getFirstSheet();
//        sheet.changeViewMode(ViewMode.OUTLINER);
//        assertEquals(ViewMode.RIGHT, sheet.getViewMode());
//    }


}
