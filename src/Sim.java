import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

class Sim implements Runnable {

    //Global variable declaration.
    protected final String title;
    protected final int width, height;
    protected int CNVS_WIDTH;
    protected int CNVS_HEIGHT;
    protected boolean running = false;
    
    //Global object declarations.
    protected GUI ui;
    protected BufferStrategy bs;
    protected Graphics gfx;
    protected Thread th;

    public Sim (String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    //Method responsible for updating values.
    protected void update () {
        
    }

    //Method is responsible for drawing on Canvas cnvs_space.
    protected void render () {
        //Buffer strategy of Canvas cnvs_space is obtained.
        bs = ui.getCanvas ().getBufferStrategy ();
        /*Sets new buffer strategy for Canvas cnvs_space
         *if obtained value of bs = null.
         */
        if (bs == null) {
            /*Method getCanvas () uses the already
             *created Canvas in class UIForm.
             */
            ui.getCanvas ().createBufferStrategy (3);
            return;
        }
        //Creates a graphics context for the drawing buffer.
        gfx = bs.getDrawGraphics ();
        gfx.clearRect (0, 0, CNVS_WIDTH, CNVS_HEIGHT);

        // Draw below
        
        //Makes the next available buffer visible.
        bs.show ();
        /*Smoothens graphics through optimizations
         */
        Toolkit.getDefaultToolkit ().sync ();
        /*Disposes off this graphics context and
         *releases any system resources that it is using.
         */
        gfx.dispose ();
    }

    protected void initiate () {
        /*Calling instance of UIForm to create a window to
         *display the GUI components.
         */
        ui = new GUI (title, width, height);
        //Get the canvas width and height.
        CNVS_WIDTH = ui.getCanvas ().getWidth ();
        CNVS_HEIGHT = ui.getCanvas ().getHeight ();
    }

    /**
     * Abstract method: Runs the code required to be run on thread
     */
    @Override
    public void run () {
        initiate ();
        final double FPS = 30;                               //30 Hz display
        double deltaUpdate = 0, deltaFrames = 0;
        long now;
        long lastTime = System.nanoTime ();
        double timePerFrame = 1000000000 / FPS;              //frames
        double timePerUpdate = 1000000000 / 60;              //60 Hz updater
        while (running) {
            now = System.nanoTime ();
            deltaUpdate += (now - lastTime) / timePerUpdate;
            deltaFrames += (now - lastTime) / timePerFrame;
            lastTime = now;
            //Value updater
            if (deltaUpdate >= 1) {
                //Calls update () to update values.
                update ();
                deltaUpdate--;
            }
            //Frames per second, higher frames makes display smooth
            if (deltaFrames >= 1) {
                //Calls render () to draw to screen.
                render ();
                deltaFrames--;
            }
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
