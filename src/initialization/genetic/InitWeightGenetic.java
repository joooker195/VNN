package initialization.genetic;

import math.Calculate;
import initialization.IInitWeight;
import logs.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ксю on 10.09.2017.
 */
public class InitWeightGenetic implements IInitWeight {

  //  Log log = new Log();

    @Override
    public ArrayList<Double> initWeight(int count) throws IOException {
        ArrayList<Double> result = new ArrayList<Double>();
        FitnessFunction ff = new FitnessFunction("matrix.txt");
        GeneticEngine ge = new GeneticEngine(ff);
        ge.setIndividualCount(5);
        ge.setGenerationCount(10);
        ge.setSelectionType(GeneticEngine.SelectionType.TOURNEY);
        ge.setCrossingType(GeneticEngine.CrossingType.ELEMENTWISE_RECOMBINATION);
        ge.setUseMutation(true);
        ge.setMutationPercent(0.01d);
        long[] better = ge.run();
       // result = ff.result();

        for(int i=0; i< better.length; i++)
        {
            result.add(Double.valueOf(better[i]));
        }
        return result;
    }

}
