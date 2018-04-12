package initialization.genetic;

import initialization.IInitWeight;
import initialization.annealing.Metric;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitWeightGenetic implements IInitWeight
{

    private final int GA_POPSIZE = 2000;

    private Random r = new Random();
    private double fitness = 0;
    private double bestFitness = 0;
    private ArrayList<Double> popWeight;
    private ArrayList<Double> popFit;
    private ArrayList<Double> bestWeight;
    private List<ArrayList<Double>> populationWeight = new ArrayList<ArrayList<Double>>();
    private List<Double> populationFitness;
    private List<ArrayList<Double>> bufferWeight = new ArrayList<ArrayList<Double>>();
    int countWeight = 0;

    private void initPopulation()
    {
        for(int i=0; i<GA_POPSIZE; i++)
        {
            popWeight = new ArrayList<Double>();
            for(int j=0; j<countWeight; j++)
            {
                popWeight.add(r.nextGaussian());
            }
            populationWeight.add(popWeight);
        }
    }
    private void calcFitnessFunction()
    {
        int tsize = countWeight;
        populationFitness = new ArrayList<Double>();
        popFit = new ArrayList<Double>();
        for(int i=0; i<GA_POPSIZE; i++)
        {
            fitness = 0;
            popFit = populationWeight.get(i);
            for(int j=0; j<tsize; j++)
            {
                fitness += Metric.calculate( Math.floor(popFit.get(r.nextInt(tsize-1))), Math.floor(popFit.get(r.nextInt(tsize-1))),
                                                Math.floor(popFit.get(r.nextInt(tsize-1))), Math.floor(popFit.get(r.nextInt(tsize-1))));
            }
            populationFitness.add(fitness);
        }

    }

    private void sortByFitness()
    {
        for (int i = populationFitness.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (populationFitness.get(j) > populationFitness.get(j+1)) {
                    double t = populationFitness.get(j);
                    populationFitness.set(j, populationFitness.get(j+1));
                    populationFitness.set(j+1, t);

                    ArrayList<Double> s = populationWeight.get(j);
                    populationWeight.set(j, populationWeight.get(j+1));
                    populationWeight.set(j+1, s);
                }
            }
        }
    }

    private void mutate(ArrayList<Double> mutateW, int i)
    {
        int tsize = countWeight;
        int ipos = Math.abs(r.nextInt(tsize));
        mutateW.set(ipos, r.nextGaussian());
        populationWeight.set(i, mutateW);
    }


    private void selection()
    {
        int tsize = countWeight;
        int spos;
        int i1;
        int i2;
        bufferWeight = new ArrayList<ArrayList<Double>>();
        bufferWeight.addAll(populationWeight);
        populationWeight = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> sel;

        for(int i=0; i<GA_POPSIZE; i++)
        {
            sel = new ArrayList<Double>();
            i1 = Math.abs(r.nextInt(GA_POPSIZE)%GA_POPSIZE/2);
            i2 = Math.abs(r.nextInt(GA_POPSIZE)% GA_POPSIZE/2);
            spos = Math.abs(r.nextInt(tsize)% tsize);
            for(int j=0; j<spos; j++)
            {
                sel.add(bufferWeight.get(i1).get(j));
            }
            for(int j=spos; j<tsize; j++)
            {
                sel.add(bufferWeight.get(i2).get(j));
            }
            populationWeight.add(sel);

            if(bestFitness!=0 && r.nextInt(100)>bestFitness/2)
                mutate(sel, i);
        }

    }


    public ArrayList<Double> initWeight(int countWeight)
    {
        this.countWeight = countWeight;
        initPopulation();

        for(int i=0; i<1000; i++) {
            bestWeight = new ArrayList<Double>();
            calcFitnessFunction();
            sortByFitness();
            bestFitness = populationFitness.get(0);
            bestWeight = populationWeight.get(0);
            if (bestFitness == 0)
            {
                break;
            }
            selection();
        }
        return bestWeight;
    }




}
