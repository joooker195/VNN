package model;

import initialization.IInitWeight;
import initialization.InitRandom;
import initialization.annelign.InitWeightAnnealing;
import initialization.genetic.InitWeightGenetic;
import logs.Log;
import view.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ксю on 13.09.2017.
 */


public class ModelNetwork
{
    //статично 3 нейрона
    int count = 3;
    private ArrayList network = new ArrayList();
    private ModelNeuron out = new ModelNeuron();
    private ModelNeuron[] firstLayer = new ModelNeuron[count];
    private ModelNeuron[][] secondLayer = new ModelNeuron[count][count];
    private ModelNeuron[][][] thirdLayer = new ModelNeuron[count][count][count];

    public ArrayList createNetwork()
    {
        String s = "";
        try {
            out.setStatus("out");
            for (int i = 0; i < count; i++) {
                firstLayer[i] = new ModelNeuron();
                firstLayer[i].setStatus(Integer.toString(i));
                firstLayer[i].setCountLayer(1);
                for (int j = 0; j < count; j++) {
                    secondLayer[i][j] = new ModelNeuron();
                    secondLayer[i][j].setStatus(Integer.toString(i) + Integer.toString(j));
                    secondLayer[i][j].setCountLayer(2);
                    for (int k = 0; k < count; k++) {
                        thirdLayer[i][j][k] = new ModelNeuron();
                        thirdLayer[i][j][k].setStatus(Integer.toString(i) + Integer.toString(j) + Integer.toString(k));
                        thirdLayer[i][j][k].setCountLayer(3);
                    }
                }
            }
            init();
            network.add(out);
            network.add(firstLayer);
            network.add(secondLayer);
            network.add(thirdLayer);

        }
        catch (Exception e) {
            System.out.println("Error");
            Log.debug("ModelNetwork#createNetwork(int countRoot, int countLayer)", "Exeption: " + e, e.getStackTrace());
        }

        return network;
    }

    private void init() {
        int countRoot = 3;
        double[] first = new double[countRoot];
        double[][] second = new double[countRoot][countRoot];
        double[][][] third = new double[countRoot][countRoot][countRoot];

        ArrayList<Double> coeff = coeff(40);
        int weightNum = 0;

        for (int i = 0; i < countRoot; i++) {
            first[i] = coeff.get(weightNum);
            weightNum++;
            for (int j = 0; j < countRoot; j++) {
                second[i][j] = second[j][i] = coeff.get(weightNum);
                weightNum++;
                for (int k = 0; k < countRoot; k++) {
                    third[i][j][k] = third[j][i][k] = third[i][k][j] = third[j][k][i]
                            = third[k][i][j] = third[k][j][i] = coeff.get(weightNum);
                    weightNum++;
                }

            }
        }

        String s = "";
        s = s+ out.toString();

        //1 веса
        for (int i = 0; i < countRoot; i++) {
            firstLayer[i].setWeight(first[i]);
            s = s+firstLayer[i].toString();
        }
        //2
        for (int i = 0; i < countRoot; i++) {
            for (int j = 0; j < countRoot; j++) {
                secondLayer[i][j].setWeight(second[i][j]);
                s =s+secondLayer[i][j].toString();
            }
        }
        //3

        for (int i = 0; i < countRoot; i++) {
            for (int j = 0; j < countRoot; j++) {
                for (int k = 0; k < countRoot; k++) {
                    thirdLayer[i][j][k].setWeight(third[i][j][k]);
                    s = s+thirdLayer[i][j][k].toString();
                }
            }
        }

        if (Log.isDebugEnable)
        {
            Log.debug("ModelNetwork#connection(ModelNeuron[] neurons)", s, false);
        }

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


        return neurons;
    }

    private ArrayList<Double> coeff(int count) {

        ArrayList<Double> coefficients = new ArrayList<Double>();
        try {

            IInitWeight init = new InitRandom();
            if (Log.isDebugEnable) {
                Log.debug("ModelNetwork#coeff(int count)", "count = " + count, true);
            }

            switch (Integer.parseInt(View.chooseInit()))
            {
                case 1:
                {
                    init = new InitRandom();
                    break;
                }
                case 2:
                {
                    init = new InitWeightAnnealing(1, 1E-6);
                    break;
                }
                case 3:
                {
                    init = new InitWeightGenetic();
                }
            }


            coefficients = init.initWeight(count);
        }
        catch (IOException e)
        {
            Log.debug("ModelNetwork2#coeff(int count)", e.toString(), e.getStackTrace());
        }

        return coefficients;


    }









}
