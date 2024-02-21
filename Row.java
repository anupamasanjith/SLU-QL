import java.util.ArrayList;
import java.util.Arrays;

public class Row {
    private ArrayList<TableEntry> row;
    private int size;


    public Row() {
        row = new ArrayList<>();
        size = 0;

    }
    public void remove(TableEntry t){
        row.remove(t);

    }


    public void setEntry(TableEntry[] entry) {
        row.addAll(Arrays.asList(entry));
    }


    public int getSize() {
        return size;
    }


    public TableEntry getEntry(int callidxtocompare) {
        return row.get(callidxtocompare);
    }
}