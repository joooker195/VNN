package mainfunction;

import model.ModelNeuron;

import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class TrainingTestingFunction {
    public static double m = 0.3;
    public static final double MO = 5;
    public static final double D = 10;

    public static void setM(double m) {
        TrainingTestingFunction.m = m;
    }

    public static double receiveSignal(ArrayList neurons, double[] x) {

        double y = 0;
        int count = x.length;
        ModelNeuron[] firstLayer = (ModelNeuron[]) neurons.get(1);
        ModelNeuron[][] secondLayer = (ModelNeuron[][]) neurons.get(2);
        ModelNeuron[][][] thirdLayer = (ModelNeuron[][][]) neurons.get(3);

        for(int i=0; i<count; i++)
        {
            y+=x[count-i-1]*firstLayer[i].getWeight();
            for(int j=0; j<count; j++)
            {
                y+=x[count-j-1]*secondLayer[i][j].getWeight();
                for(int k=0; k<count; k++)
                {
                    y+=x[count-k-1]*thirdLayer[i][j][k].getWeight();
                }
            }
        }

        return y;
    }


    //корректировака весов
    public static ArrayList returnSignal(ArrayList network, double[] x,  double y, double d)
    {
        int count = x.length;
        ModelNeuron[] firstLayer = (ModelNeuron[]) network.get(1);
        ModelNeuron[][] secondLayer = (ModelNeuron[][]) network.get(2);
        ModelNeuron[][][] thirdLayer = (ModelNeuron[][][]) network.get(3);

        double y1;
        double y2;
        double y3;

        for(int i=0; i<count; i++)
        {
            y1 = x[count-i-1]*(y-d);
            firstLayer[i].setWeight(firstLayer[i].getWeight()-m*y1);
            for(int j=0; j<count; j++)
            {
                y2 = x[count-j-1]*y1;
                secondLayer[i][j].setWeight(secondLayer[i][j].getWeight()-m*y2);
                for(int k=0; k<count; k++)
                {
                    y3 = x[count-k-1]*y2;
                    thirdLayer[i][j][k].setWeight(thirdLayer[i][j][k].getWeight()-m*y3);
                }
            }
        }

        network.set(1,firstLayer);
        network.set(2,secondLayer);
        network.set(3,thirdLayer);

        return network;
    }


    static double e = 1; // ошибка прогнозирования
    //условие схождения и вычисление ошибки прогнозирования
    public static boolean condition(double y, double d)
    {
        double epsilon = 0.0001; //размер ошибки
        double err = 0.5 * Math.pow((y-d), 2); //целевая функция
        e += Math.pow((y-d), 2);
        if (err <= epsilon)
        {
            e = 0.05 * e;
            return true;
        }
        return false;
    }
}
