package initialization.annealing;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;

public class GenerateStateCandidate
{
    private static final Logger LOG = Logger.getLogger(GenerateStateCandidate.class);
    public static ArrayList<Double> generate(ArrayList<Double> states)
    {
        int n = states.size()-1;
        Random r = new Random();
        int i = r.nextInt(n);//выципляем две координаты
        int j = r.nextInt(n);

        if(states.get(i)>states.get(j)) //если первая больше второй, то меняем две точки пути
        {
            double state = states.get(i);
            states.set(i, states.get(j));
            states.set(j, state);
        }
        return states;
    }
}
