package initialization.annealing;

import java.util.ArrayList;

/**
 * Created by Ксю on 09.03.2017.
 */
public class CalculateEnergy
{
    //как сумма Евклидовых расстояний
    private static double energy;
    public static double calEner(ArrayList<Double> states, Cities cities)
    {
        int n = states.size();
        energy = 0;
        for (int i=0; i<n-1; i++ ) {
            energy += Metric.calculate(cities.getStateX((int)Math.floor(states.get(i))), cities.getStateX((int)Math.floor(states.get(i+1))),
                    cities.getStateY((int)Math.floor(states.get(i))), cities.getStateY((int)Math.floor(states.get(i+1))));

        }

        energy += Metric.calculate(cities.getStateX((int) Math.floor(states.get(n-1))), cities.getStateX((int) Math.floor(states.get(0))),
                cities.getStateY((int) Math.floor(states.get(n-1))), cities.getStateY((int) Math.floor(states.get(0))));

        return energy;

    }

}
