import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * Entire console will be CASE-INSENSITIVE
 * Each person will be known by a name
 *
 * Commands
 * 1.  create [NAME]                     create balla        If name already present, error is given
 * 2.  del [NAME]                        del hullu           If name already present, error is given
 * 3.  interact [NAME] [NAME]
 * 4.  set [NAME] [ATTRIBUTE] [NEW VALUE]
 * 5.  get [NAME] [ATTRIBUTE]
 */

public class Console implements Runnable {
    
    protected boolean running = false;
    protected Thread th;
    
    protected String readCommand () {
        BufferedReader stdin = new BufferedReader (
                new java.io.InputStreamReader (System.in));
        try {
            System.out.print ("BS> ");                                        // prompt
            Global.command = stdin.readLine ();                               // store command in variable, kinda uselss
            evaluate (Global.command);                                        // evaluates the command
            return Global.command;                                            // return that command, also useless
        } catch (java.io.IOException ex) {
            Global.printerr (ex.toString());
        }
        return null;
    }

    private void evaluate(String command) {
        StringTokenizer sT = new StringTokenizer (command, " \n");            // delimiters
        String cmd = sT.nextToken();
        if (cmd.equalsIgnoreCase ("CREATE")) {                                // if command is to create entity
            File dir = new File ("Data/Entities/" + sT.nextToken());          // pass th directory path
            dir.mkdir ();                                                     // creates a directory for that named entity
        }
        else if (cmd.equalsIgnoreCase ("DEL")) {
            String name;
            File dir = new File ("Data/Entities/" + (name = sT.nextToken()));
            for (File file: dir.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
                else {
                    try {
                        Global.printerr ("error: failed to delete: " + 
                                file.getCanonicalPath());                     // canonical path is the path from root
                    } catch (IOException ex) {
                        Global.printerr ("error: could not find name: " + name);
                        return;
                    }
                }
            }
        }
        else if (cmd.equalsIgnoreCase ("SET")) {
            Entity.setAttrValue(sT.nextToken(),
                    sT.nextToken(), Integer.parseInt(sT.nextToken()));
        }
        else if (cmd.equalsIgnoreCase ("GET")) {
            Global.println (Integer.toString (
                    Entity.getAttrValue(sT.nextToken(), sT.nextToken())));
        }
        else if (cmd.equalsIgnoreCase ("INTERACT")) {
            Global.interacting[0] = sT.nextToken();
            Global.interacting[1] = sT.nextToken();
        }
    }
    
    /**
     * Abstract method: Runs the code required to be run on thread
     */
    @Override
    public void run () {
        while (running) {
           readCommand ();
        }
        //Calls new Sim.stop () to close thread.
        stop ();
    }

    //Method responsible for starting thread.
    public synchronized void start () {
        /*Prevents errors by not starting thread if
         *it's already running.
         */
        if (running) {
            return;
        }
        //Sets a flag variable true to denote thread is running.
        running = true;
        //Defines new Thread object
        th = new Thread (this);
        /*Start Thread th, following method exists in a
         *library class, NOT to be confused with mthod
         *public synchronized void start ().
         */
        th.start ();
    }

    //Method responsible for stopping thread
    public synchronized void stop () {
        /*Prevents errors by not closing thread if
         *it's already not running
         */
        if (!running) {
            return;
        }
        //Sets a flag variable false to denote thread is not running
        running = false;
        /*Safely closes the thread
         *NOTE that stop method is deprecated
         *NOTE join () method always throws InterruptedException
         */
        try {
            th.join ();
        } catch (InterruptedException e) {
            System.err.print (e.toString ());
            System.exit (0);
        }
    }
}
