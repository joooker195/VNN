package initialization.g;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//https://habrahabr.ru/post/100990/
public class GA
{
    private Random r = new Random();
    private final int GA_POPSIZE = 2048;
    private final double GA_ELITRATE = 0.10f;
    private final double GA_MUTATIONRATE = 0.25f;
    private final double GA_MUTATION = r.nextInt() * GA_MUTATIONRATE;
    private final String GA_TARGET = "Hello, World";


    private int fitness = 0;
   // private String str = "";
    private String strNum ="";
    List<Integer> populationFitness = new ArrayList<Integer>();
    List<String> pupulationStrNum = new ArrayList<String>();

    /*void init_population(ga_vector &population,
                         ga_vector &buffer )
    {
        int tsize = GA_TARGET.size();

        for (int i=0; i<GA_POPSIZE; i++) {
            ga_struct citizen;

            citizen.fitness = 0;
            citizen.str.erase();

            for (int j=0; j<tsize; j++)
                citizen.str += (rand() % 90) + 32;

            population.push_back(citizen);
        }

        buffer.resize(GA_POPSIZE);
    }*/


    private void initPopulation()
    {
        int tsize = GA_TARGET.length();
        for(int i=0; i<GA_POPSIZE; i++)
        {
            fitness = 0;
            strNum = "";
            for(int j=0; j<tsize; i++)
            {
                strNum += (r.nextInt() % 90) + 32;
            }
            pupulationStrNum.add(strNum);
        }
    }



    /*void calc_fitness(ga_vector &population)
    {
        string target = GA_TARGET;
        int tsize = target.size();
        unsigned int fitness;

        for (int i=0; i<GA_POPSIZE; i++) {
            fitness = 0;
            for (int j=0; j<tsize; j++) {
                fitness += abs(int(population[i].str[j] - target[j]));
            }

            population[i].fitness = fitness;
        }
    }*/

    public void fitnessFunction()
    {
        String target = GA_TARGET;
        int tsize = target.length();
        for(int i=0; i<GA_POPSIZE; i++)
        {
            fitness = 0;
            for(int j=0; j<tsize; j++)
            {
                fitness += Math.abs((int)(pupulationStrNum.get(i).charAt(j) - target.charAt(j)));
            }
            populationFitness.add(fitness);
        }
    }

    /*bool fitness_sort(ga_struct x, ga_struct y)
    { return (x.fitness < y.fitness); }*/

    public boolean fitnessSort(int i, int j)
    {
        return (populationFitness.get(i) < populationFitness.get(j));
    }


}
