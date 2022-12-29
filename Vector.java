
/**
 * Write a description of class Vector here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Vector
{
    private double[] x;
    private int dim;
    /**
     * Constructor for objects of class Vector
     */
    public Vector(double[] input)
    {
        x = input;
        dim = x.length;
    }

    public double[] getVector() {return x;}

    public int getDim() {return dim;}

    public double getMag() 
    {
        double mag = 0;
        for(int i = 0; i < dim; i++)
        {
            mag += x[i] * x[i];
        }
        return Math.sqrt(mag);
    }

    public static Vector scale(Vector v, double k)
    {
        double[] vx   = v.getVector();
        double[] newV = new double[v.getDim()];
        for(int i = 0; i < v.getDim(); i++)
        {
            newV[i] = vx[i] * k;
        }
        return new Vector(newV);
    }

    public static Vector add(Vector v1, Vector v2)
    {
        if (v1.getDim() != v2.getDim()) throw new IllegalArgumentException();
        double[] v1x  = v1.getVector();
        double[] v2x  = v2.getVector();
        double[] newV = new double[v1.getDim()];
        for(int i = 0; i < v1.getDim(); i++)
        {
            newV[i] = v1x[i] + v2x[i];
        }
        return new Vector(newV);
    }

    public static double dotProduct(Vector v1, Vector v2)
    {
        if (v1.getDim() != v2.getDim()) throw new IllegalArgumentException();
        double sum = 0;
        double[] v1x = v1.getVector();
        double[] v2x = v2.getVector();
        for(int i = 0; i < v1.getDim(); i++)
        {
            sum += v1x[i] * v2x[i];
        }
        return sum;
    }

    public static Vector crossProduct(Vector v1, Vector v2)
    {
        if(v1.getDim() != 3 || v2.getDim() != 3) throw new IllegalArgumentException();
        double[] v1x  = v1.getVector();
        double[] v2x  = v2.getVector();
        double a      = v1x[0];
        double b      = v1x[1];
        double c      = v1x[2];
        double d      = v2x[0];
        double e      = v2x[1];
        double f      = v2x[2];
        double[] newV = {b * f - c * e,
                c * d - a * f,
                a * e - b * d};
        return new Vector(newV);
    }
    
    public void output(String prefix)
    {
        prefix += "(";
        for(double d : x)
        {
            prefix += d + ", ";
        }
        prefix += ")";
        System.out.println(prefix);
    }
}