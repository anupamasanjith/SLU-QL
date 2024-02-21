import java.sql.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashMap<String,Table> db = new HashMap<>();
        Scanner in = new Scanner(System.in);
        System.out.print("% ");

        while (in.hasNext()){
            String cmd = in.next();

            switch(cmd.charAt(0)){

                case'C':
                    String tableName = in.next();
                    int n = in.nextInt();
                    ArrayList<TableEntry.Type> Types = new ArrayList<>(n);
                    ArrayList<String> names = new ArrayList<>(n);
                    for (int i = 0; i < n; i++) {
                        String a  = in.next();
                        String capitalized = a.substring(0, 1).toUpperCase() + a.substring(1);
                        Types.add(i, TableEntry.Type.valueOf(capitalized));
                    }
                    for (int i = 0; i < n; i++) {
                        names.add(i,in.next());

                    }
                    if (db.containsKey(tableName)) {
                        System.out.printf("Error: %s does not name a table in the database\n", tableName);
                        break;
                    }
                    Table t = new Table(tableName,names,Types);
                    db.put(tableName,t);
                    System.out.printf("New table %s with column(s) ",tableName);
                    for (String s : names) {
                        System.out.print(s + " ");
                    }
                    System.out.print("created\n");
                    break;

                case 'I':
                    in.next(); // consume "N"
                    tableName = in.next();
                    int numValues = in.nextInt();
                    in.next();

                    // Find the table
                    Table table;
                        if (db.containsKey(tableName)) {
                            table = db.get(tableName);
                        }else{
                            System.out.printf("Error: %s does not name a table in the database\n", tableName);
                            break;
                        }


                    // Check number of values matches number of columns
                    int numColumns = table.numcols();
                    int size = table.size;

                    // Parse values and create new Row
                    TableEntry[] values = new TableEntry[numColumns];
                    for (int j = 0; j < numValues; j++) {

                        for (int i = 0; i < numColumns; i++) {
                            TableEntry e = TableEntry.stringtote(in.next(),table.Coltype2(i));
                            values[i] = e;
                        }
                        Row newRow = new Row();
                        newRow.setEntry(values);
                        // Add new Row to table
                        table.addRow(newRow);


                    }
                    int start;
                    if(size == 0 ) {
                        start = 0;
                    } else {
                        start = (table.size ) - size;
                    }
                    //Added <N> rows to <tablename> from position <startN> to <endN>


                    System.out.printf("Added %d rows to %s from position %d to %d\n", numValues,tableName,start,table.size-1);
                    break;

                case 'R':
                    tableName = in.next();
                    if (db.containsKey(tableName)) {
                        db.remove(tableName);
                        System.out.printf("Table %s deleted\n", tableName);
                    }else{
                        System.out.printf("Error: %s does not name a table in the database\n", tableName);
                    }
                    break;
                case'P' :
                    in.next();
                    tableName = in.next();
                    Table tAble = db.get(tableName);
                    int Numcols = in.nextInt();
                    ArrayList<String> colsToPrint = new ArrayList<>(Numcols);
                    for (int i = 0; i < Numcols; i++) {
                        colsToPrint.add(in.next());
                    }
                    if(in.next().equals("ALL")){
                        tAble.printall(colsToPrint);

                    }else{
                        String calltocompare = in.next();
                        String op = in.next();
                        TableEntry val = TableEntry.stringtote(in.next(),tAble.Coltype(calltocompare));
                        Comparator<TableEntry> comp;

                        switch(op){
                            case"=":
                                comp = new RowComparators.EqualComparator();
                                break;
                            case"<":
                                comp = new RowComparators.LSComparator();
                                break;
                            default:
                                comp = new RowComparators.GSComparator();
                                break;
                        }
                        tAble.printwithCondition(calltocompare,comp,val, colsToPrint);
                        System.out.printf("Printed %d matching rows from %s\n",tAble.comparison,tableName);

                    }
                    break;
                case'D':
                    in.next();
                    tableName = in.next();
                    tAble = db.get(tableName);
                    in.next();
                    String calltocompare = in.next();
                    String op = in.next();
                    TableEntry val = TableEntry.stringtote(in.next(),tAble.Coltype(calltocompare));
                    Comparator<TableEntry> comp;

                    switch(op){
                        case"=":
                            comp = new RowComparators.EqualComparator();
                            break;
                        case"<":
                            comp = new RowComparators.LSComparator();
                            break;
                        default:
                            comp = new RowComparators.GSComparator();
                            break;
                    }
                    int number = tAble.Delete(calltocompare,comp,val);
                    System.out.printf("Deleted %d rows from %s\n", number,tableName);
                    break;



                case'G':
                    in.next();
                    tableName = in.next();
                    String indexType = in.next();
                    in.next();
                    in.next();
                    String columnName = in.next();
                    if (!db.containsKey(tableName)) {
                        System.out.println("Error: " + tableName + " is not the name of a table in the database");
                        return;
                    }
                     table = db.get(tableName);
                    if (table.hasIndex()) {
                        table.deleteIndex();
                    }

                    // Create new index
                    switch (indexType) {
                        case "hash":
                            table.createHashIndex(columnName);
                            break;
                        case "bst":
                            table.createBSTIndex(columnName);
                            break;
                        default:
                            System.out.println("Error: Invalid index type");
                            break;
                    }
                    System.out.printf("Created %s index for table %s on column %s\n",indexType,tableName,columnName);
                    break;
                case 'J':
                    String T1n = in.next();
                    in.next();
                    String T2n = in.next();
                    in.next();
                    String T1col = in.next();
                    in.next();
                    String T2col = in.next();
                    in.next();
                    in.next();
                    int numcols = in.nextInt();
                    ArrayList<String> colsToprint = new ArrayList<>(numcols);
                    ArrayList<Boolean> colsT1 = new ArrayList<>(numcols);
                    for (int i = 0; i < numcols; i++) {
                        colsToprint.add(in.next());
                        if(in.next().equals("1")){
                            colsT1.add(true);
                        } else{
                            colsT1.add(false);
                        }

                    }
                    Table t1 = db.get(T1n);
                    Table t2 = db.get(T2n);
                    Comparator<TableEntry> join =  new RowComparators.EqualComparator();
                    t1.join(t2,join,T1col,T2col,colsToprint,colsT1);
                    break;






                case'#':
                    in.nextLine();
                    break;

                case 'Q' :
                    System.out.println("Goodbye, SLU!");
                    System.exit(0);
                    break;
                default:
                    System.out.printf("Unknown Command %s\n",cmd);

            }
            System.out.print("% ");
        }

    }
}
