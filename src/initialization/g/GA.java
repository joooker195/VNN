package initialization.g;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//https://habrahabr.ru/post/100990/
public class GA
{
    private Random r = new Random();
    private final int GA_POPSIZE = 2048;
   // private final double GA_ELITRATE = 0.10f;
    private final int GA_ELITRATE = 1;
    private final double GA_MUTATIONRATE = 0.25f;
    private final double GA_MUTATION = r.nextInt() * GA_MUTATIONRATE;
    private final String GA_TARGET = "Hello, World";


    private int fitness = 0;
   // private String str = "";
    private String strNum ="";
    List<Integer> populationFitness = new ArrayList<Integer>();
    List<Integer> bufferFitness = new ArrayList<Integer>();
    List<String> populationStr = new ArrayList<String>();
    List<String> bufferStr = new ArrayList<String>();

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
            populationStr.add(strNum);
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
                fitness += Math.abs((int)(populationStr.get(i).charAt(j) - target.charAt(j)));
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

    /*void elitism(ga_vector &population,
                 ga_vector &buffer, int esize )
    {
        for (int i=0; i<esize; i++) {
            buffer[i].str = population[i].str;
            buffer[i].fitness = population[i].fitness;
        }
    }*/

    public void elitims(int esize)
    {
        for(int i=0; i<esize; i++)
        {
            bufferStr.set(i, populationStr.get(i));
            bufferFitness.set(i, populationFitness.get(i));
        }
    }

    /*void mutate(ga_struct &member)
    {
        int tsize = GA_TARGET.size();
        int ipos = rand() % tsize;
        int delta = (rand() % 90) + 32;

        member.str[ipos] = ((member.str[ipos] + delta) % 122);
    }*/
    public void mutate(String str, int i)
    {
        int tsize = GA_TARGET.length();
        int ipos = r.nextInt() % tsize;
        int delta = (r.nextInt() % 90) + 32;

        bufferFitness.set(i, ((str.charAt(ipos) + delta) % 122));
    }

    private void sortByFitness() {
        for (int i = populationFitness.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (populationFitness.get(j) > populationFitness.get(j+1)) {
                    int t = populationFitness.get(j);
                    populationFitness.set(j, populationFitness.get(j+1));
                    populationFitness.set(j+1, t);
                }
            }
        }
    }

    /*void mate(ga_vector &population, ga_vector &buffer)
    {
        int esize = GA_POPSIZE * GA_ELITRATE;
        int tsize = GA_TARGET.size(), spos, i1, i2;

        elitism(population, buffer, esize);

        // Mate the rest
        for (int i=esize; i<GA_POPSIZE; i++) {
            i1 = rand() % (GA_POPSIZE / 2);
            i2 = rand() % (GA_POPSIZE / 2);
            spos = rand() % tsize;

            buffer[i].str = population[i1].str.substr(0, spos) +
                    population[i2].str.substr(spos, esize - spos);

            if (rand() < GA_MUTATION) mutate(buffer[i]);
        }
    }*/

    public void mate()
    {
        int esize = GA_POPSIZE * GA_ELITRATE;
        int tsize = GA_TARGET.length(), spos, i1, i2;

        elitims(esize);
        for(int i=esize; i<GA_POPSIZE; i++)
        {
            i1 = r.nextInt() % (GA_POPSIZE/2);
            i2 = r.nextInt() % (GA_POPSIZE/2);
            spos = r.nextInt() % tsize;
            String str = bufferStr.get(i);
            str = populationStr.get(i1).substring(0, spos)+populationStr.get(i2).substring(spos, esize-spos);
            bufferStr.set(i, str);

            if(r.nextDouble()<GA_MUTATION)
            {
                mutate(bufferStr.get(i), i);
            }

        }

    }

    /*inline void print_best(ga_vector &gav)
    { cout << "Best: " << gav[0].str << " (" << gav[0].fitness << ")" << endl; */

    public void printBestPop()
    {
        System.out.println("Best: "+populationStr.get(0)+" ("+ populationFitness.get(0)+")");
    }


    /*int main()
    {
        srand(unsigned(time(NULL)));

        ga_vector pop_alpha, pop_beta;
        ga_vector *population, *buffer;

        init_population(pop_alpha, pop_beta);
        population = &pop_alpha;
        buffer = &pop_beta;

        for (int i=0; i<GA_MAXITER; i++) {
            calc_fitness(*population);		// вычисляем пригодность
            sort_by_fitness(*population);		// сортируем популяцию
            print_best(*population);		// выводим лучшую популяцию

            if ((*population)[0].fitness == 0) break;

            mate(*population, *buffer);		// спариваем популяции
            swap(population, buffer);		// очищаем буферы
        }

        return 0;
    }*/

    public void run()
    {
        initPopulation();

        for(int i=0; i<GA_MUTATION; i++)
        {
            sortByFitness();
            printBestPop();
            if(populationFitness.get(0) ==0) break;
            mate();
        }
    }


}
