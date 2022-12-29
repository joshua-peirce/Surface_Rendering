import java.awt.Polygon;
/**
 * Dummy Wrapper to a polygon, used to mark polygons as "bad",
 * iff an error is thrown in generating the polygon.
 */
public class BadPolygon extends Polygon
{
    public BadPolygon()
    {
        //System.out.println("This is a bad polygon!");
    }
}
