/**
 * @name behavior-simulator
 * @version May 7, 2021
 * @lisence GNU GPL v3.0+
 */
class Main {

    //main method that begins software execution.
    public static void main (String[] args){
        /*Create new Sim object and start thread,
         *Thread is used to allow running multiple codes together.
         *This will aid in reducing code execution wait time
         *and also aids in programming logic.
         */
        Sim sim = new Sim ("Behaviour Simulator", 1024, 710);
        Console console = new Console ();
        
        /*Method start essentially begins the Simulator
         *by calling a new thread.
         *Look in class Sim for more details.
         */
        System.out.println ("Behaviour Simulator v2021.5.7");
        System.out.println ("GNU General Public Lisence (GPL) v3.0+\n");
        console.start ();
        sim.start ();
    }
}
