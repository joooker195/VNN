package mainfunction;

import model.ModelNeuron;

import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class TrainingTestingFunction {
    public static double m = 0.01;
    public static final double MO = 5;
    public static final double D = 10;

    public static void setM(double m) {
        TrainingTestingFunction.m = m;
    }

    public static double receiveSignal(ArrayList neurons, double[] x) {
     /*   int countRoot = 3;
        //первый промежуточный слой
        int layerOne = 1;
        int layerTwo = layerOne * countRoot + 1;
        int layerThree = layerTwo * countRoot + 1;

     //   double y = 0;

        for (int one = 0; one < countRoot; one++) {
            y = y + x[2] * neurons[layerThree].getWeight();
            neurons[layerOne].setStatus(Integer.toString(one));
            layerOne++;
            for (int two = 0; two < countRoot; two++) {
                y = y + x[1] * neurons[layerThree].getWeight();
                neurons[layerTwo].setValueNode(y);
                neurons[layerTwo].setSignal(x[1]);

                layerTwo++;
                for (int three = 0; three < countRoot; three++) {
                    y = y + x[0] * neurons[layerThree].getWeight();
                    neurons[layerThree].setValueNode(y);
                    neurons[layerThree].setSignal(x[0]);
                    layerThree++;
                }
            }
        }*/

        double y = 0;
        int count = x.length;
        ModelNeuron[] firstLayer = (ModelNeuron[]) neurons.get(1);
        ModelNeuron[][] secondLayer = (ModelNeuron[][]) neurons.get(2);
        ModelNeuron[][][] thirdLayer = (ModelNeuron[][][]) neurons.get(3);

        for(int i=0; i<count; i++)
        {
            y+=x[count-i]*firstLayer[i].getWeight();
            for(int j=0; j<count; j++)
            {
                y+=x[count-j]*secondLayer[i][j].getWeight();
                for(int k=0; k<count; k++)
                {
                    y+=x[count-k]*thirdLayer[i][j][k].getWeight();
                }
            }
        }

        return y;
    }


    //корректировака весов
    public static ArrayList returnSignal(ArrayList network, double[] x,  double y, double d)
    {
       /* int countRoot = 3;
        //первый промежуточный слой
        int layerOne = 1;
        int layerTwo = layerOne * countRoot + 1;
        int layerThree = layerTwo * countRoot + 1;

        double deltaFirhs = x[0]*(y-d);
        double deltaSecond = x[1]*deltaFirhs;
        double deltaThird = x[2]*deltaSecond;

        network[0].setWeight(network[0].getWeight()-m*deltaFirhs);
        int i = 1;
        for(; i<layerTwo; i++)
        {
            network[i].setWeight(network[i].getWeight()-m*deltaSecond);
        }
        for(; i<layerTwo; i++)
        {
            network[i].setWeight(network[i].getWeight()-m*deltaThird);
        }*/


        int count = x.length;
        ModelNeuron[] firstLayer = (ModelNeuron[]) network.get(1);
        ModelNeuron[][] secondLayer = (ModelNeuron[][]) network.get(2);
        ModelNeuron[][][] thirdLayer = (ModelNeuron[][][]) network.get(3);

        double y1;
        double y2;
        double y3;

        for(int i=0; i<count; i++)
        {
            y1 = x[count-i]*(y-d);
            firstLayer[i].setWeight(firstLayer[i].getWeight()-m*y1);
            for(int j=0; j<count; j++)
            {
                y2 = x[count-j]*y1;
                secondLayer[i][j].setWeight(secondLayer[i][j].getWeight()-m*y2);
                for(int k=0; k<count; k++)
                {
                    y3 = x[count-k]*y2;
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
            e = 0.5 * e;
            return true;
        }
        return false;
    }
}
