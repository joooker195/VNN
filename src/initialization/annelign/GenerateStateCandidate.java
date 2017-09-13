package initialization.annelign;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ксю on 09.03.2017.
 */
public class GenerateStateCandidate
{
    public static ArrayList<Double> generate(ArrayList<Double> states)
    {
        int n = states.size();
        Random r = new Random();
        int i = r.nextInt(n);
        int j = r.nextInt(n);

        if(i> j)
        {
            double state = states.get(i);
            states.remove(i);
            states.add(i, states.get(j));
            states.remove(j);
            states.add(j, state);
        }

        return states;
    }
}
