import javax.swing.plaf.TableUI;
import java.util.Objects;

public abstract class TableEntry implements Comparable<TableEntry> {

    public String tostring() {
        switch (getType()) {
            case Bool:
                return String.valueOf(((BoolEntry)this).data);
            case Int:
                return String.valueOf(((IntEntry)this).data);
            case Double:
                return String.valueOf(((DoubleEntry)this).data);
            case String:
                return ((StringEntry)this).data;
            default:
                return "";
        }
    }


    public static TableEntry stringtote(String next, Type coltype) {
        switch (coltype) {
            case Bool:
                return new BoolEntry(Boolean.parseBoolean(next));
            case Int:
                return new IntEntry(Integer.parseInt(next));
            case Double:
                return new DoubleEntry(Double.parseDouble(next));
            case String:
                return new StringEntry(next);
            default:
                throw new IllegalArgumentException("Invalid column type");
        }
    }


    public enum Type{
        Bool,
        Int,
        Double,
        String

    }
    public abstract Type getType();


    public static class BoolEntry extends TableEntry{
        public boolean data;

        public BoolEntry(boolean b){
            data = b;
        }




        @Override
        public Type getType() {
            return Type.Bool;
        }



        @Override
        public int compareTo(TableEntry o) {

            if (o.getType()!= Type.Bool){
                throw new ClassCastException();
            }
            BoolEntry ob = (BoolEntry) o;

            if (data){
                if(ob.data){
                    return 0;
                } else{
                    return 1;
                }
            } else{
                if(!ob.data){
                    return 0;
                }else{
                    return -1;
                }
            }
        }
    }
    public static class DoubleEntry extends TableEntry{

        public double data;
        public String name;


        public DoubleEntry(double d) {
            data = d;
        }



        @Override
        public Type getType() {
            return Type.Double;
        }

        @Override
        public int compareTo(TableEntry o) {
            if (o.getType() != Type.Double) {
                throw new ClassCastException();
            }
            DoubleEntry od = (DoubleEntry) o;

            return Double.compare(data, od.data);

        }

    }
    public static class IntEntry extends TableEntry{
        public int data;


        public IntEntry(int i){
            data = i;
        }


        @Override
        public Type getType() {
            return Type.Int;
        }

        @Override
        public int compareTo(TableEntry o) {
            if (o.getType() != Type.Int) {
                throw new ClassCastException();
            } else {
                IntEntry e = (IntEntry) o;
                int oi = e.data;


                return Integer.compare(data, oi);
            }
        }
    }
    public static class StringEntry extends TableEntry {
        public String data;

        public StringEntry(String s) {
            data = s;
        }

        @Override
        public Type getType() {
            return Type.String;
        }


        @Override
        public int compareTo(TableEntry o) {
            if (o.getType() != Type.String) {
                throw new ClassCastException();
            } else {
                StringEntry e = (StringEntry) o;
                String os = e.data;
                return data.compareTo(os);

            }
        }

    }
}
