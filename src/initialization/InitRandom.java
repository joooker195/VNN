package initialization;

import logs.Log;
import math.Calculate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ксю on 12.09.2017.
 */
public class InitRandom implements IInitWeight
{
    ArrayList<Double> weights = new ArrayList<Double>();
    Random r = new Random();
    @Override
    public ArrayList<Double> initWeight(int count) {

        for(int i=0; i<count; i++) {
            double a = initGaussianRandomWeight();

            weights.add(a);
        }
        Scanner in = null;
        try {
            in = new Scanner(new FileReader("ffout.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Double> ran = new ArrayList<Double>();
        int num = in.nextInt();
        for(int i=0; i<num; i++)
        {
            ran.add(Double.valueOf(in.next()));
        }
        for(int i=0; i<count; i++)
        {
            weights.add(ran.get(r.nextInt(150)));
        }
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
