package math;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ксю on 12.09.2017.
 */
public class Calculate
{
    private static Random r = new Random();
    public static double getRandom()
    {
        double rnd = r.nextGaussian();
        return rnd;
    }

    //нормализация входных данных
    public static double normalize(double x, double m, double d)
    {
        double x_max = 95;//25
        double x_min = 0;//0
        double y_max = 0.5;
        double y_min = 0;
        double a= x-x_min;
        double b = x_max-x_min;
        double c = y_max - y_min;
        return (a/b)*c + y_min;
    }
    //нормализация входных данных
    public static double normalize(double x)
    {
        return normalize(x, 0, 0);
    }


    public static double denotnormalize(double x)
    {
        double x_max = 150;
        double x_min = 8;
        double y_max = 0.9;
        double y_min = 0;
        double result = ((x_min - x_max) * x - y_max
                * x_min + x_max * y_min)
                / (y_min - y_max);
        return result;
    }

    //Гаусовское распределение для случайной инициализации весов
    public static double distrGaussian()
    {
        double m = 0;
        double sqrS = 1;
        double powE = -(Math.pow(getRandom()-m, 2))/(2*sqrS);
        double coff = 1/(Math.sqrt(2*sqrS*Math.PI));
        return Math.pow(Math.E, powE)*coff;
    }

    // производная от сигмоидальной функции активации (гиперболический тангенс)
    public static double funActivator(double x)
    {
        double a = Math.pow(Math.E, x);
        double b = Math.pow(Math.E, -x);
        double x1 = a - b;
        double x2 = a + b;
        double y = x1/x2;
        return  y;
    }

    public static double average(ArrayList<Double> data)
    {
        double y = 0;
        for(int i=0; i<data.size(); i++)
        {
            y+=data.get(i);
        }
        return  y/data.size();
    }

    public static double sd(ArrayList<Double> data, double average)
    {
        double y = 0;
        for(int i=0; i< data.size(); i++) {
            y += Math.pow(data.get(i) - average, 2);
        }
        return Math.sqrt(y/(data.size()));
    }

    public static double err = 1;

    public static double error(ArrayList<Double> datain, ArrayList<Double> dataout)
    {
        err = 0;
        double y =0;
        for(int i=0; i<datain.size(); i++)
        {
            y+=Math.abs((datain.get(i) - dataout.get(i))/dataout.get(i));
        }
        err = y/(datain.size());
        return err;
    }
}
