package model;

import logs.Log;

import java.util.ArrayList;

/**
 * Created by Ксю on 13.09.2017.
 */


public class ModelNetwork2
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
        try {
            out.setStatus(Integer.toString(0));

            for (int i = 0; i < count; i++) {
                firstLayer[i].setStatus(Integer.toString(i));
                for (int j = 0; j < count; j++) {
                    secondLayer[i][j].setStatus(Integer.toString(i) + "/" + Integer.toString(j));
                    for (int k = 0; k < count; k++) {
                        thirdLayer[i][j][k].setStatus(Integer.toString(i) + "/" + Integer.toString(j) + "/" + Integer.toString(k));
                    }
                }
            }
            network.add(out);
            network.add(firstLayer);
            network.add(secondLayer);
            network.add(thirdLayer);
            if (Log.isDebugEnable) {
                String s = "";
                for (int i = 0; i < network.size(); i++) {
                    s = s + network.get(i).toString();
                }
                Log.debug("ModelNetwork#connection(ModelNeuron[] neurons)", s, false);
            }
        }
        catch (Exception e) {
            System.out.println("Error");
            Log.debug("ModelNeuron#createNetwork(int countRoot, int countLayer)", "Exeption: " + e, e.getStackTrace());
        }

        return network;
    }









}
