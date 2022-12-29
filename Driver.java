import java.awt.*;
import javax.swing.*;
/**
 * Write a description of class Driver here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Driver
{ 
    private JFrame window;
    private Canvas canv;
    private Camera cam;
    public Surface[] environment;

    private static final int WIDTH  = 1080;
    private static final int HEIGHT = 1080;
    private static final int FPS    = 120;
    /**
     * Constructor for objects of class Driver
     */
    public Driver()
    {
        window = new JFrame();
        canv   = new Canvas(this, WIDTH, HEIGHT, FPS);
        window.setFocusable(true);
        window.add(canv);
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.addKeyListener(canv);
        window.addMouseMotionListener(canv);
        window.setCursor(Cursor.CROSSHAIR_CURSOR);
        environment = setupEnv();
        cam = new Camera(WIDTH, HEIGHT, FPS);
        canv.addCam(cam);
        window.setVisible(true);
        canv.start();
    }

    public Surface[] getEnvironment() {return environment;}

    /**
     * Totally arbitrary setup of environment
     */
    private Surface[] setupEnv()
    {
        double[][] points1 = {{1, -1, -1}, {1, -1, 1}, {1, 1, 1},  {1, 1, -1}};
        double[][] points2 = {{1, -1, -1}, {1, -1, 1}, {2, -1, 1}, {2, -1, -1}};
        double[][] points3 = {{1, 1, -1},  {1, 1, 1},  {2, 1, 1},  {2, 1, -1}};
        double[][] points4 = {{2, -1, -1}, {2, -1, 1}, {2, 1, 1},  {2, 1, -1}};
        Vector[] s1 = new Vector[4];
        Vector[] s2 = new Vector[4];
        Vector[] s3 = new Vector[4];
        Vector[] s4 = new Vector[4];
        for(int i = 0; i < 4; i++)
        {
            s1[i] = new Vector(points1[i]);
            s2[i] = new Vector(points2[i]);
            s3[i] = new Vector(points3[i]);
            s4[i] = new Vector(points4[i]);
        }
        Surface[] env  = {new Surface(s1), new Surface(s2), new Surface(s3), new Surface(s4)};
        return env;
    }
}