import java.util.*;

public class Table {
    private ArrayList<Row> rows;
    private Map<String, ArrayList<Integer>> hashIndex;
    private TreeMap<String, ArrayList<Integer>> bstIndex;
    private String name;
    int size;
    int comparison;
    private ArrayList<String> names;
    private ArrayList<TableEntry.Type> types;


    public Table(String name, ArrayList<String> names, ArrayList<TableEntry.Type> types) {
        this.types = types;
        this.names = names;
        rows = new ArrayList<>();
        this.name = name;
        this.hashIndex = new HashMap<>();
        this.bstIndex = new TreeMap<>();
        size = 0;
        comparison = 0;

    }

    public TableEntry.Type Coltype(String s) {
        return types.get(names.indexOf(s));
    }

    public String getName() {
        return name;
    }

    public TableEntry.Type Coltype2(int i) {
        return types.get(i);
    }

    public void addRow(Row row) {
        rows.add(row);
        size++;
    }

    public int numcols() {
        return names.size();
    }


    public Row getRow(int index) {
        return rows.get(index);
    }

    public int numRows() {
        return rows.size();
    }


    public void printwithCondition(String calltocompare, Comparator<TableEntry> comp, TableEntry val, ArrayList<String> print) {
        // column to compare
        int callidxtocompare = names.indexOf(calltocompare);
        ArrayList<Integer> callidx = namestoids(print);
        for (String s : print) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (Row r : rows) {
            if(isHash()|| !hasIndex()) {
                if (comp.compare(r.getEntry(callidxtocompare), val) == 0) {
                    comparison++;

                    for (int i = 0; i < callidx.size(); i++) {
                        System.out.print(r.getEntry(callidx.get(i)).tostring() + " ");
                    }
                    System.out.println();
                }
            }else {
                    // bst index, print in order of bst traversal
                    ArrayList<Integer> bstIndices = bstIndex.get(r.getEntry(callidxtocompare).tostring());
                    for (int i : bstIndices) {
                        if (comp.compare(r.getEntry(callidxtocompare), val) == 0) {
                            comparison++;
                        Row ra = rows.get(i);
                            for (int j = 0; j < callidx.size(); j++) {
                                System.out.print(ra.getEntry(callidx.get(j)).tostring() + " ");
                            }

                            System.out.println();
                        }
                    }
                }
            }
        }


    public ArrayList<Integer> namestoids(ArrayList<String> print) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (String t : print) {
            int index = names.indexOf(t);
            if (index >= 0) {
                indices.add(index);
            }
        }

        return indices;
    }


    public void printall(ArrayList<String> print) {
        ArrayList<Integer> callidx = namestoids(print);
        for (String s : print) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (Row r : rows) {
            for (int i = 0; i < callidx.size(); i++) {
                System.out.print(r.getEntry(callidx.get(i)).tostring() + " ");
            }
            System.out.println();
        }
    }

    public int Delete(String calltocompare, Comparator<TableEntry> comp, TableEntry val) {
        int number = 0;
        int callidxtocompare = names.indexOf(calltocompare);
        Iterator<Row> iter = rows.iterator();
        while (iter.hasNext()) {
            Row r = iter.next();
            if (comp.compare(r.getEntry(callidxtocompare), val) == 0) {
                number++;
                iter.remove(); // Use the iterator to remove the row
            }
        }
        return number;
    }

    public void createHashIndex(String columnName) {
        int columnIndex = names.indexOf(columnName);
        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            TableEntry entry = r.getEntry(columnIndex);
            String key = entry.tostring();
            if (hashIndex.containsKey(key)) {
                ArrayList<Integer> rowsWithSameKey = hashIndex.get(key);
                rowsWithSameKey.add(i);
            } else {
                ArrayList<Integer> rowsWithSameKey = new ArrayList<>();
                rowsWithSameKey.add(i);
                hashIndex.put(key, rowsWithSameKey);
            }
        }
    }


    public void createBSTIndex(String columnName) {
        int index = names.indexOf(columnName);
        for (int i = 0; i < rows.size(); i++) {
            String key = rows.get(i).getEntry(index).tostring();
            if (!bstIndex.containsKey(key)) {
                bstIndex.put(key, new ArrayList<>());
            }
            bstIndex.get(key).add(i);
        }
    }
    public boolean isHash(){
        if(hashIndex.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean hasIndex() {
        return !hashIndex.isEmpty() || !bstIndex.isEmpty();
    }


    public void deleteIndex() {
        hashIndex.clear();
        bstIndex.clear();
    }

    public void join(Table t2, Comparator<TableEntry> join, String t1col, String t2col, ArrayList<String> colsToprint, ArrayList<Boolean> colsT1) {
        ArrayList<Integer> colidx = new ArrayList<>(colsToprint.size());
        for (int i = 0; i < colsToprint.size(); i++) {
            ArrayList<String> targetnames = colsT1.get(i) ? names:t2.names;
            colidx.add(targetnames.indexOf(colsToprint.get(i)));

        }
        int t1idx = names.indexOf(t1col);
        int t2idx = t2.names.indexOf(t2col);

        for(Row r1: rows){
            for(Row r2: t2.rows){
                if(join.compare(r1.getEntry(t1idx),r2.getEntry(t2idx))==0){
                    printfrom2rows(r1,r2,colidx,colsT1);
                }

            }
        }
    }

    private void printfrom2rows(Row r1, Row r2, ArrayList<Integer> colidx, ArrayList<Boolean> colsT1) {
        ArrayList<String> sb = new ArrayList<>();
        for (int i = 0; i < colidx.size(); i++) {
            int idx = colidx.get(i);
            boolean fromT1 = colsT1.get(i);
            String value = (fromT1 ? r1.getEntry(idx).tostring() : r2.getEntry(idx).tostring());
            sb.add(value);
            if (i < colidx.size() - 1) {
                sb.add(" ");
            }
        }
        System.out.println(String.join("", sb));
    }
    }

