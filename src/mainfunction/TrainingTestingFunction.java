package mainfunction;

import model.ModelNeuron;

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

    public static double receiveSignal(ModelNeuron[] neurons, double[] x) {
        int countRoot = 3;
        //первый промежуточный слой
        int layerOne = 1;
        int layerTwo = layerOne * countRoot + 1;
        int layerThree = layerTwo * countRoot + 1;

        double y = 0;

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
        }
        return y;
    }


    //корректировака весов
    public static ModelNeuron[] returnSignal(ModelNeuron[] network, double[] x,  double y, double d)
    {
        int countRoot = 3;
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
            network[i].setWeight(network[i].getWeight()-m*deltaFirhs);
        }
        for(; i<layerTwo; i++)
        {
            network[i].setWeight(network[i].getWeight()-m*deltaThird);
        }

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
