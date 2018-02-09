package initialization.genetic;

public class FF
{
    private static FitnessFunction fitnessFunction;
    private static long[] actual;
    private static long[] fitnessFunctionResult;


    public static long getFitnessFunctionResult(int genomNumber, long[][] genomListParents, long currentGeneration)
    {
        if (actual[genomNumber] != currentGeneration) {
            fitnessFunctionResult[genomNumber] = fitnessFunction.run(genomListParents[genomNumber]);
            actual[genomNumber] = currentGeneration;
        }
        return fitnessFunctionResult[genomNumber];
    }


    public static long[] getActual() {
        return actual;
    }

    public static void setActual(long[] actual) {
        FF.actual = actual;
    }

    public static long[] getFitnessFunctionResult() {
        return fitnessFunctionResult;
    }

    public static void setFitnessFunctionResult(long[] fitnessFunctionResult) {
        FF.fitnessFunctionResult = fitnessFunctionResult;
    }
}
