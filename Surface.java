
/**
 * A surface to be rendered in space
 *
 * @author me
 * @version now
 */
public class Surface
{
    private Vector[] pts;
    
    public Surface(Vector[] vertecies)
    {
        pts = vertecies;
    }
    
    public int getNumPts() {return pts.length;}
    
    public Vector[] getPts() {return pts;}
}
