import java.util.HashMap;
import java.util.Map;

public class ExportFactory {
    private static final Map<FileType, ITypeExport> exports = new HashMap<>();

    static {
        exports.put(FileType.PNG, new ExportPNG());
        exports.put(FileType.PDF, new ExportPDF());
    }

    public static ITypeExport getExport(FileType type) {
        return exports.get(type);
    }
}
