public class Xmind {
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public ExportStatus export(Sheet sheet, FileType type, String filename) {
        ITypeExport export = ExportFactory.getExport(type);
        return export.export(sheet, filename);
    }
}
