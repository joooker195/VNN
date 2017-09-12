package model;

import initialization.IInitWeight;
import initialization.InitRandom;
import logs.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class ModelNetwork {
    int count;
    private
    ModelNeuron[] neurons;

    public ModelNeuron[] createNetwork() {
        try {
            int countRoot = 3;
            //первый промежуточный слой
            int layerOne = 1;
            int layerTwo;
            int layerThree;

            //count layer 3
            //третий слой
            layerTwo = layerOne * countRoot + 1;
            layerThree = layerTwo * countRoot + 1;
            this.count = layerThree * countRoot + 1;

            neurons = new ModelNeuron[count];

            neurons[0] = new ModelNeuron();
            neurons[0].setStatus(Integer.toString(0));
            neurons[0].setSignal(0);
            neurons[0].setCountLayer(3);

            for (int one = 0; one < countRoot; one++) {
                neurons[layerOne] = new ModelNeuron();
                neurons[layerOne].setStatus(Integer.toString(one));
                layerOne++;
                for (int two = 0; two < countRoot; two++) {
                    neurons[layerTwo] = new ModelNeuron();
                    neurons[layerTwo].setStatus(Integer.toString(one) + Integer.toString(two));
                    layerTwo++;
                    for (int three = 0; three < countRoot; three++) {
                        neurons[layerThree] = new ModelNeuron();
                        neurons[layerThree].setStatus(Integer.toString(one) + Integer.toString(two) + Integer.toString(three));
                        layerThree++;
                    }
                }
            }

            neurons = init(neurons);
            neurons = connection(neurons);
        } catch (Exception e) {
            System.out.println("Error");
            Log.debug("ModelNeuron#createNetwork(int countRoot, int countLayer)", "Exeption: " + e, e.getStackTrace());
        }
        return neurons;
    }

    private ModelNeuron[] init(ModelNeuron[] neurons) {
        int countRoot = 3;
        double[] firstLayer = new double[countRoot];
        double[][] secondLayer = new double[countRoot][countRoot];
        double[][][] thirdLayer = new double[countRoot][countRoot][countRoot];

        ArrayList<Double> coeff = coeff(count);
        int weightNum = 0;

        for (int i = 0; i < countRoot; i++) {
            firstLayer[i] = coeff.get(weightNum);
            weightNum++;
            for (int j = 0; j < countRoot; j++) {
                secondLayer[i][j] = secondLayer[j][i] = coeff.get(weightNum);
                weightNum++;
                for (int k = 0; k < countRoot; k++) {
                    thirdLayer[i][j][k] = thirdLayer[j][i][k] = thirdLayer[i][k][j] = thirdLayer[j][k][i]
                            = thirdLayer[k][i][j] = thirdLayer[k][j][i] = coeff.get(weightNum);
                    weightNum++;
                }

            }
        }
        //1 веса
        int consNum = 1;
        for (int i = 0; i < countRoot; i++) {
            neurons[consNum].setWeight(firstLayer[i]);
            consNum++;
        }
        //2
        for (int i = 0; i < countRoot; i++) {
            for (int j = 0; j < countRoot; j++) {
                neurons[consNum].setWeight(secondLayer[i][j]);
                consNum++;
            }
        }
        //3
        for (int i = 0; i < countRoot; i++) {
            for (int j = 0; j < countRoot; j++) {
                for (int k = 0; k < countRoot; k++) {
                    neurons[consNum].setWeight(thirdLayer[i][j][k]);
                    consNum++;
                }
            }
        }

        return neurons;
    }

    private ModelNeuron[] connection(ModelNeuron[] neurons)//countRoot количество нейронов на входе; countNeurons количество нейронов в в первом промежуточном слое
    {
        int countNeurons = 3;
        int j = 0;
        for (int i = 0; i < countNeurons; i++) {
            j++;
            neurons[i].setLeft(neurons[j]);
            neurons[j].setParent(neurons[i]);
            j++;
            neurons[i].setHiddenLeft(neurons[j]);
            neurons[j].setParent(neurons[i]);
            j++;
            neurons[i].setHiddenRight(neurons[j]);
            neurons[j].setParent(neurons[i]);
        }

        /*if (Log.isDebugEnable) {
            String s = "";
            for (int i = 0; i < neurons.length; i++) {
                s = s + neurons[i].toString();
            }
            Log.debug("ModelNetwork#connection(ModelNeuron[] neurons)", s, false);
        }*/

        return neurons;
    }

    private ArrayList<Double> coeff(int count) {

        IInitWeight init;
        if (Log.isDebugEnable) {
            Log.debug("ModelNetwork#coeff(int count)", "count = " + count, true);
        }
        // init = new InitWeightAnnealing(1, 1E-7);
         init = new InitRandom();
      //  init = new InitWeightGenetic();

        ArrayList<Double> coefficients = init.initWeight(count);

        return coefficients;

    }
}
