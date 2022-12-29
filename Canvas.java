import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Canvas extends JPanel implements ActionListener, MouseMotionListener, KeyListener
{
    private boolean setCam = false;
    private Camera cam;
    private Timer timer;
    private Driver d;
    private Robot robot;

    private boolean pannable = true;
    private boolean wHeld = false;
    private boolean sHeld = false;
    private boolean aHeld = false;
    private boolean dHeld = false;
    private boolean shiftHeld = false;
    private boolean spaceHeld = false;

    public int width, height, fps;
    public int delay;
    public Canvas(Driver driver, int w, int h, int maxfps)
    {
        d = driver;
        width = w;
        height = h;
        fps = maxfps;
        delay = (int) (1000 / fps);
        try{
            robot = new Robot();
            robot.mouseMove(width / 2, height / 2);
        }
        catch(AWTException e)
        {
            System.out.println("You will not be able to pan the camera");
            pannable = false;
        }
    }

    public void addCam(Camera c)
    {
        cam    = c;
        setCam = true;
    }

    public void start()
    {
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Surface[] env = d.getEnvironment();
        Polygon[] polys = cam.renderAll(env);
        for(Polygon p: polys)
        {
            g.drawPolygon(p);
        }
        //System.out.println();
    }

    public void mouseMoved(MouseEvent mv)
    {
        if(pannable)
        {
            cam.pan(mv.getX() - width / 2, mv.getY() - height / 2);
            robot.mouseMove(width / 2, height / 2);
        }
    }

    public void mouseDragged(MouseEvent mv)
    {

    }

    public void keyPressed(KeyEvent kv)
    {
        switch(kv.getKeyChar())
        {
            case 'w':
            wHeld = true;
            break;
            case 's':
            sHeld = true;
            break;
            case 'a':
            aHeld = true;
            break;
            case 'd':
            dHeld = true;
            break;
            default:
            break;
        }
        
        switch(kv.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
            spaceHeld = true;
            break;
            case KeyEvent.VK_SHIFT:
            shiftHeld = true;
            break;
            case KeyEvent.VK_ESCAPE:
            pannable = !pannable;
            break;
            default:
            break;
        }
    }

    public void keyReleased(KeyEvent kv)
    {
        switch(kv.getKeyChar())
        {
            case 'w':
            case ',':
            wHeld = false;
            break;
            case 's':
            case 'o':
            sHeld = false;
            break;
            case 'a':
            aHeld = false;
            break;
            case 'd':
            case 'e':
            dHeld = false;
            break;
            default:
            break;
        }
        
        switch(kv.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
            spaceHeld = false;
            break;
            case KeyEvent.VK_SHIFT:
            shiftHeld = false;
            break;
            default:
            break;
        }
    }

    public void keyTyped(KeyEvent kv)
    {

    }

    public void actionPerformed(ActionEvent ev)
    {
        if(wHeld)
        {
            cam.moveForward();
        }

        if(sHeld)
        {
            cam.moveBackward();
        }

        if(aHeld)
        {
            cam.moveAngleAdjusted(Math.PI / -2.0);
        }

        if(dHeld)
        {
            cam.moveAngleAdjusted(Math.PI / 2.0);
        }

        if(spaceHeld)
        {
            cam.moveUp();
        }

        if(shiftHeld)
        {
            cam.moveDown();
        }
        repaint();
    }
}