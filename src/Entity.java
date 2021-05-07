import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Entity {

    private static boolean validAttribute (String atr) {                      // scans the Global.attributes array for validity check
        for (String attribute: Global.attributes) {
            if (attribute.equalsIgnoreCase (atr)) {
                return true;
            }
        }
        return false;
    }

    protected static void setAttrValue (String name, String atr, int val) {
        PrintWriter pr;
        name = name.toLowerCase ();
        atr = atr.toLowerCase ();
        if (validAttribute (atr)) {                                           // if attribute is valid
            try {
                pr = new PrintWriter (
                new FileWriter ("Data/Entities/" + name + "/" + atr, false)); // overwrites attribute file at name directory
                pr.write (Integer.toString(val));                             // actual writing
            } catch (Exception e) {
                Global.printerr ("error: no such person: " + name);
            }
        }
        else {
            Global.printerr ("error: no such attribute: " + atr);
        }
    }

    protected static int getAttrValue (String name, String atr) {
        Scanner sc;
        name = name.toLowerCase ();
        atr = atr.toLowerCase ();
        if (validAttribute (atr)) {
            try {
                sc = new Scanner (
                new File ("Data/Entities/" + name + "/" + atr));              // reads the attribute file at name directory
                return Integer.parseInt (sc.nextLine());                      // returns value as int
            } catch (Exception e) {
                Global.printerr ("error: no such person: " + name);
            }
        }
        else {
            Global.printerr ("error: no such attribute: " + atr);
        }
        return 0;                                                             // default return is 0
    }
}
