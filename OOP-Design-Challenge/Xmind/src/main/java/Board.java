import java.util.ArrayList;
import java.util.List;

public class Board {
    private String name;

    private List<Sheet> sheets = new ArrayList<>();

    public Board(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public void addSheet() {
        int sheetNumber = sheets.size() + 1;
        sheets.add(new Sheet("Map " + sheetNumber));
    }

    public void addSheet(String sheetName) {
        sheets.add(new Sheet(sheetName));
    }
}
