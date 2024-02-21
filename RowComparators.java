import java.util.Comparator;

public class RowComparators {
    public static class EqualComparator implements Comparator<TableEntry> {

        @Override
        public int compare(TableEntry o1, TableEntry o2) {
            return o1.compareTo(o2);
        }
    }

    public static class LSComparator implements Comparator<TableEntry> {

        @Override
        public int compare(TableEntry o1, TableEntry o2) {
            if (o1.compareTo(o2) < 0) {
                return 0;
            }
            return 1;
        }
    }

    public static class GSComparator implements Comparator<TableEntry> {
        public int compare(TableEntry o1, TableEntry o2) {
            if (o1.compareTo(o2) >0) {
                return 0;
            }
            return 1;
        }


    }
}
