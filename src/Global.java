public class Global {
    protected static String command;

    // list of interacting entities: MUST be left EMPTY
    protected static String[] interacting = {
        "",
        ""
    };
    // list of attribute names
    protected static final String[] attributes = {
        "",
        "",
        "",
        ""
    };

    // wrapper funtions
    protected static void print (String str) {
        System.out.print (str);
    }
    protected static void println (String str) {
        System.out.println (str);
    }
    protected static void printerr (String str) {
        System.err.println (str);
    }
}
