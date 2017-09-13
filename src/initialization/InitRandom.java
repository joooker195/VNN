package initialization;

import logs.Log;
import math.Calculate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class InitRandom implements IInitWeight
{
    ArrayList<Double> weights = new ArrayList<Double>();
    @Override
    public ArrayList<Double> initWeight(int count) {

        String s = "Weights \n";
        for(int i=0; i<count; i++) {
            double a = initGaussianRandomWeight();
            s = s+a+"\n";
            weights.add(a);
        }
        /*if(Log.isDebugEnable)
        {
            Log.debug("InitWeightRandom#initWeight(int count)",s, false);
        }*/
        return weights;
    }


    // просто пусть будет тут
    public static double initGaussianRandomWeight()
    {
        double m = 0.1;
        double sqrS = 1.5;
        double powE = -(Math.pow(Calculate.getRandom() - m, 2)) / (2 * sqrS);
        double coff = 1 / (Math.sqrt(2 * sqrS * Math.PI));
        return Math.pow(Math.E, powE) * coff;
    }
}
