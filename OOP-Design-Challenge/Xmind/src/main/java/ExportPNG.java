public class ExportPNG implements ITypeExport {
    @Override
    public ExportStatus export(Sheet sheet, String filename) {
        // Export board to PNG file
        return ExportStatus.SUCCESS;
    }
}
