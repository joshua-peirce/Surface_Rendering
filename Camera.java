import java.awt.Polygon;
import java.util.ArrayList;
/**
 * Write a description of class Camera here.
 *
 * @author Josh
 * @version now
 */
public class Camera
{
    public  double x, y, z, theta, phi;
    public  int screenWidth, screenHeight, FPS;
    private double fov;
    private double zoom;
    private double speed;
    private double sensetivity;

    private Vector cAngle;
    private Vector down;  //Unit vector in the "down"  direction
    private Vector right; //Unit vector in the "right" direction
    private double downScale;  //How many pixels 1 unit "down"  is on the screen
    private double rightScale; //How many pixels 1 unit "right" is on the screen

    /**
     * Constructor for objects of class Camera
     */
    public Camera(int sw, int sh, int FPS)
    {
        // initialise Camera at origin
        x = 0;
        y = 0;
        z = 0;
        theta = 0;//Math.PI / 6;
        phi = Math.PI / 2;
        fov = Math.PI / 2;
        zoom = 0.2;
        speed = 8;
        sensetivity = 0.8; 

        screenWidth  = sw;
        screenHeight = sh;
        this.FPS = FPS;
        updateBasis();
    }

    public Vector getCameraAngle()
    {
        double[] newV = {
                Math.cos(theta) * Math.sin(phi),
                Math.sin(theta) * Math.sin(phi),
                Math.cos(phi)
            };
        return new Vector(newV); 
    }

    public Vector getCameraPos()
    {
        double[] newV = {x, y, z};
        return new Vector(newV);
    }

    public void moveForward()
    {
        x += speed / FPS * Math.cos(theta) * Math.sin(phi);
        y += speed / FPS * Math.sin(theta) * Math.sin(phi);
        z += speed / FPS * Math.cos(phi);
    }

    public void moveBackward()
    {
        x -= speed / FPS * Math.cos(theta) * Math.sin(phi);
        y -= speed / FPS * Math.sin(theta) * Math.sin(phi);
        z -= speed / FPS * Math.cos(phi);
    }

    public void moveAngleAdjusted(double deltatheta)
    {
        double newtheta = theta + deltatheta;
        x += speed / FPS * Math.cos(newtheta) * Math.sin(phi);
        y += speed / FPS * Math.sin(newtheta) * Math.sin(phi);
        //z += speed / FPS * Math.cos(phi);
    }

    public void moveUp()
    {
        z += speed / FPS;
    }
    
    public void moveDown()
    {
        z -= speed / FPS;
    }
    
    public void pan(int x, int y)
    {
        theta += x * fov / 2 / rightScale * sensetivity;
        phi   += y * fov / 2 / downScale  * sensetivity;
    }

    private void updateBasis()
    {
        cAngle    = getCameraAngle();
        double newphi    = phi   + fov / 2; //Subtract bcuz increasing is down in computer graphics for some reason
        double newtheta  = theta + fov / 2;
        double[] newVphi = {
                Math.cos(theta) * Math.sin(newphi),
                Math.sin(theta) * Math.sin(newphi),
                Math.cos(newphi)
            }; 
        double[] newVtheta = {
                Math.cos(newtheta) * Math.sin(phi),
                Math.sin(newtheta) * Math.sin(phi),
                Math.cos(phi)
            };
        Vector phiTest    = new Vector(newVphi);
        Vector thetaTest  = new Vector(newVtheta);
        double phiDP      = Vector.dotProduct(phiTest, cAngle);
        double thetaDP    = Vector.dotProduct(thetaTest, cAngle);
        Vector phiTest2   = Vector.scale(phiTest, 1 / phiDP);
        Vector thetaTest2 = Vector.scale(thetaTest, 1 / thetaDP);
        Vector phiTest3   = Vector.add(phiTest2, Vector.scale(cAngle, -1.0));
        Vector thetaTest3 = Vector.add(thetaTest2, Vector.scale(cAngle, -1.0));
        downScale         = screenHeight / phiTest3.getMag() * zoom;
        rightScale        = screenWidth  / thetaTest3.getMag() * zoom;
        down              = Vector.scale(phiTest3, 1 / phiTest3.getMag());
        right             = Vector.scale(thetaTest3, 1 / thetaTest3.getMag());
    }

    private int[] renderPt(Vector absv)
    {
        Vector v  = Vector.add(absv, Vector.scale(getCameraPos(), -1.0));
        double dp = Vector.dotProduct(v, cAngle);
        if(dp <= 0)//Point is behind camera
        {
            throw new IllegalArgumentException();
        }
        Vector onscreen = Vector.scale(v, 1 / dp);
        Vector pt1      = Vector.add(onscreen, Vector.scale(cAngle, -1.0));
        double downcomp = Vector.dotProduct(pt1, down);
        double rightcomp = Vector.dotProduct(pt1, right);
        int[] coords = {
                (int) (rightcomp * rightScale + screenWidth  / 2),
                (int) (downcomp  * downScale  + screenHeight / 2)
            };
        return coords;
    }

    public Polygon renderSurface(Surface s)
    {
        int numPts = s.getNumPts();
        int[] xpts = new int[numPts];
        int[] ypts = new int[numPts];
        for(int i = 0; i < numPts; i++)
        {
            try{
                Vector v     = s.getPts()[i];
                int[] coords = renderPt(v);
                xpts[i]      = coords[0];
                ypts[i]      = coords[1];
            }
            catch (IllegalArgumentException e)
            {
                return new BadPolygon();
            }
        }
        return new Polygon(xpts, ypts, numPts);
    }

    public Polygon[] renderAll(Surface[] surfaces)
    {
        updateBasis();
        ArrayList<Polygon> output = new ArrayList<Polygon>();
        for(Surface s : surfaces)
        {
            Polygon renderedSurface = renderSurface(s);
            if(!(renderedSurface instanceof BadPolygon)) output.add(renderedSurface);
        }
        Polygon[] polys = new Polygon[output.size()];
        polys = output.toArray(polys);
        return polys;
    }
}
