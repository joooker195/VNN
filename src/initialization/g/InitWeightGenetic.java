package initialization.g;

import initialization.InitRandom;
import math.Calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitWeightGenetic
{

    private final int GA_POPSIZE = 10;

    private Random r = new Random();
    private int fitness = 0;
    private int bestFitness = 0;
    private String bestPop = "";
    private ArrayList<Double> popNum = new ArrayList<Double>();
    private List<ArrayList<Double>> populationWeight = new ArrayList<ArrayList<Double>>();
    private List<List<Integer>> populationFitness = new ArrayList<List<Integer>>();
    private List<Double> bufferWeight = new ArrayList<Double>();
    int countWeight = 0;

    private void initPopulation()
    {
        for(int i=0; i<GA_POPSIZE; i++)
        {
            for(int j=0; j<2; j++)
            {
                popNum.add(r.nextGaussian());
            }
            populationWeight.add(popNum);
            popNum.clear();
        }
    }

    /*private void calcFitnessFunction()
    {
        int tsize = target.length();
        populationFitness.clear();
        for(int i=0; i<GA_POPSIZE; i++)
        {
            fitness = 0;
            for(int j=0; j<tsize; j++)
            {
                int a = target.charAt(j);
                fitness += Math.abs(populationStr.get(i).charAt(j) - target.charAt(j));
            }
            populationFitness.add(fitness);
        }
    }*/


    public void run(int countWeight)
    {
        this.countWeight = countWeight;
        initPopulation();
        int a = 0;
    }




}
