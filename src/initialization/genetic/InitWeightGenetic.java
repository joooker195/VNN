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
        StringBuffer s = new StringBuffer();
        ArrayList<Double> result;
        FitnessFunction ff = new FitnessFunction("matrix.txt");
        GeneticEngine ge = new GeneticEngine(ff);
        ge.setIndividualCount(100);
        ge.setGenerationCount(10000);
        ge.setSelectionType(GeneticEngine.SelectionType.TOURNEY);
        ge.setCrossingType(GeneticEngine.CrossingType.ELEMENTWISE_RECOMBINATION);
        ge.setUseMutation(true);
        ge.setMutationPercent(0.02d);

        long time = System.currentTimeMillis();
        long[] better = ge.run();
        result = ff.result();

        long timeToFF = ge.timeToFF;
        long timeToCrossing = ge.timeToCrossing;
        long timeToMutate = ge.timeToMutate;

        if(Log.isDebugEnable)
        {
            s.append("Running:\t" + (System.currentTimeMillis() - time) / 1000 + " secs").append("\n");
            s.append("FitnessFunc:\t" + timeToFF / 1000 + " secs").append("\n");
            s.append(" - FF Prepare:\t" + ff.prepareTime / 1000 + " secs").append("\n");
            s.append(" - FF QSort:\t" + ff.sortingTime / 1000 + " secs");
            s.append(" - FF Check: \t" + ff.checkTime / 1000 + " secs").append("\n");
            s.append("Crossing:\t" + timeToCrossing / 1000 + " secs").append("\n");
            s.append("Mutate: \t" + timeToMutate / 1000 + " secs").append("\n");
            /*s.append(Long.MAX_VALUE - ff.run(better)).append("\n");*/

           // Log.debug("InitWeightGenetic#initWeight(int count)", s.toString(), false);
        }
        return result;
    }

    private static void printLongInBin(long l, int last){
        if (last>0){
            int p = (int)(l & 1);
            printLongInBin(l>>1,--last);
            System.out.print(p);
        }
    }
}
