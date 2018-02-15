package initialization.g;
import java.util.*;

//https://habrahabr.ru/post/100990/
public class GA
{
    private Random r = new Random();
    private final int GA_POPSIZE = 2048;
    private final String GA_TARGET = "Hey!!!+)";

    private int fitness = 0;
    private int bestFitness = 0;
    private String bestStr = "";
    private String strNum ="";
    private List<Integer> populationFitness = new ArrayList<Integer>();
    private List<String> populationStr = new ArrayList<String>();
    private List<String> bufferStr = new ArrayList<String>();


    private void initPopulation()
    {
        int tsize = GA_TARGET.length();
        for(int i=0; i<GA_POPSIZE; i++)
        {
            fitness = 0;
            strNum = "";
            char c = ' ';
            for(int j=0; j<tsize; j++)
            {
                c += (char)(r.nextInt() % 90) + 32;
                strNum += c;
            }
            populationStr.add(strNum);
        }
    }

    private void calcFitnessFunction()
    {
        String target = GA_TARGET;
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
    }

    private void sortByFitness() {
        HashMap<Integer, String> fitnessAll2 = new HashMap<Integer, String>();

        for (int i = populationFitness.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (populationFitness.get(j) > populationFitness.get(j+1)) {
                    int t = populationFitness.get(j);
                    populationFitness.set(j, populationFitness.get(j+1));
                    populationFitness.set(j+1, t);

                    String s = populationStr.get(j);
                    populationStr.set(j, populationStr.get(j+1));
                    populationStr.set(j+1, s);
                }
            }
        }
    }

    private void mutate(String str, int i)
    {
        int tsize = GA_TARGET.length();
        int ipos = Math.abs(r.nextInt(tsize));
        char delta;
        delta = (char) (((r.nextInt() % 90) + 32));
        String s = str.replace(str.charAt(ipos), delta);
        populationStr.set(i, s);

    }

    private void mate()
    {
        int tsize = GA_TARGET.length(), spos, i1, i2;
        this.bufferStr.addAll(populationStr);
        populationStr.clear();

        for(int i=0; i<GA_POPSIZE/2; i++)
        {
            i1 = Math.abs(r.nextInt(GA_POPSIZE)%GA_POPSIZE/2);
            i2 = Math.abs(r.nextInt(GA_POPSIZE)% GA_POPSIZE/2);
            spos = Math.abs(r.nextInt(tsize)% tsize);

            String s1 = bufferStr.get(i1).substring(0, spos);
            String s2 = bestStr.substring(spos, tsize);
            String str = s1+s2;
            populationStr.add(str);

            if(bestFitness!=0 && r.nextInt(bestFitness)>bestFitness/2)
            mutate(str, i);

            s1 = bufferStr.get(i2).substring(spos, tsize);
            s2 = bestStr.substring(0, spos);
            str = s1+s2;
            populationStr.add(str);

            if(bestFitness<tsize+2)
            mutate(bestStr, i);

        }

    }

    private void printBestPop()
    {
        System.out.println("Best: "+bestStr+" ("+ bestFitness+")");
    }


    public void run()
    {
        initPopulation();

        for(int i=0; i<10000;i++) {
            calcFitnessFunction();
            sortByFitness();
            if (bestFitness == 0 && !bestStr.equals(""))
            {
                break;
            }
            if (bestFitness==0 || bestFitness > populationFitness.get(0)) {
                bestFitness = populationFitness.get(0);
                bestStr = populationStr.get(0);
                printBestPop();
            }
            mate();

        }
    }


}
