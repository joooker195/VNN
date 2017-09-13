package initialization.annelign;

import math.Calculate;
import initialization.IInitWeight;
import initialization.InitRandom;
import logs.Log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ксю on 09.03.2017.
 */
public class InitWeightAnnealing implements IInitWeight
{

    private Random r = new Random();
    private Cities cities;
    private ArrayList<Double> states;
    private int count;
    private double initialTemperature;
    private double endTemperature;
    private double T;

    public InitWeightAnnealing(double tmax, double tmin)
    {
        this.initialTemperature= tmax;
        this.endTemperature = tmin;
    }


    private ArrayList<Double> getRand()
    {
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
        return ran;
    }

    @Override
    public ArrayList<Double> initWeight(int count) {

        ArrayList<Double> x = new ArrayList<Double>();
        ArrayList<Double> y = new ArrayList<Double>();
        ArrayList<Double> rand = getRand();
        for(int i=0; i<count; i++)
        {
            /*x.add(InitRandom.initGaussianRandomWeight());
            y.add(InitRandom.initGaussianRandomWeight());*/
            x.add(rand.get(r.nextInt(150)));
            y.add(rand.get(r.nextInt(150)));
        }
        cities = new Cities(x, y);
        this.count = count;
        states = generateStateCandidate();

        for (int i=0; i< states.size(); i++)
        {
            double data = states.get(i);
            if(states.get(i)>0) {
                data = Calculate.normalize(states.get(i)*10);
            }
          //  double data = cities.getStateX((int)Math.floor(states.get(i)));
            states.set(i, data);
        }

        if(Log.isDebugEnable)
        {
            String s ="";
            for(Double state: states)
            {
                s=s+state+"\n";
            }
            Log.debug("InitWeightAnneling#generateStateCandidate()", s, false);
        }

        return states;

    }

    private ArrayList<Double> generateStateCandidate()
    {
        ArrayList<Double> stateCondidates;
        double energyCondidates;
        double energyCurrent;

        T = initialTemperature;
        states = new ArrayList<Double>();

        double n = count - 10;
        for(int i=0; i< count; i++)
        {
          //  states.add(r.nextDouble()*10+n);
            states.add((double)r.nextInt(15));
        }

        energyCurrent = CalculateEnergy.calEner(states, cities);

        String a = "";
        for(int i=1; ; i++)
        {
            stateCondidates = GenerateStateCandidate.generate(states);
            energyCondidates = CalculateEnergy.calEner(stateCondidates, cities);

            if(energyCondidates < energyCurrent)
            {
                energyCurrent = energyCondidates;
                states = stateCondidates;
            }
            else
            {
                if(MakeTransitProbility.calculate(energyCurrent, energyCondidates, T))
                {
                    energyCurrent = energyCondidates;
                    states = stateCondidates;
                }

            }

            T = initialTemperature * 0.1 / i;
            if(Log.isDebugEnable)
                System.out.println("T = "+ T);

            if(T < endTemperature)
            {
                break;
            }
        }
        return states;
    }
}
