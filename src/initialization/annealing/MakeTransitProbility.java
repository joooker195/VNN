package initialization.annealing;

import java.util.Random;

/**
 * Created by Ксю on 09.03.2017.
 */
public class MakeTransitProbility
{
    private static Random r = new Random();
    public static boolean calculate(double current, double condidate, double T)
    {
        double probility = condidate - current;
        double p = Math.exp(-probility/T);

        if(p >1 || p <0)
        {
            System.err.println("Exp");
        }

        double value = r.nextGaussian();

        if(value <= p)
        {
            return true;
        }

        return false;

    }
}
