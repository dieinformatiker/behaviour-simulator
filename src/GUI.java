import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;

final class GUI extends JFrame{
    //Variable declarations.
    protected final String title;
    protected final int width, height;

    //UI object declarations.
    protected Canvas cnvs_sim;

    public GUI (String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        //set LookAndFeel, using default look and feel, only GTK and Windows UI supported
        try {
            UIManager.setLookAndFeel ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");                // LINUX UI
        } catch (Exception ex){
            try {
                UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");    // Windows UI
            } catch (Exception e) {
                Global.printerr ("error: could not load Windows look and feel");
            }
            Global.printerr ("error: could not load GTK look and feel");
        }
        
        createUI ();
    }

    //Creates the UI with window and components
    protected void createUI (){

        //UI object definations.
        cnvs_sim = new Canvas ();

        //JFrame frame properties.
        setTitle (title);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setSize (width, height);
        setResizable (false);
        setLocationRelativeTo (null);
        setLayout (null);

        //Canvas cnvs_sim properties.
        cnvs_sim.setBounds (0, 0, width, height);
        cnvs_sim.setBackground (Color.DARK_GRAY);

        //JFrame frame adding components.
        add (cnvs_sim);
        setVisible (true);
    }

    //Getter for Canvas cnvs_sim.
    protected Canvas getCanvas (){
        return cnvs_sim;
    }
}
