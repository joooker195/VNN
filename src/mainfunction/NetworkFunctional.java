package mainfunction;

import controll.Calculation;
import controll.DataExchange;
import controll.MainFunctional;
import logs.Log;
import math.Calculate;
import model.ModelNeuron;

import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class NetworkFunctional
{
    public static double SCO = 0;
    public static double err = 1;
    private static double E = 0.001;
    private static boolean flag;

    public static ModelNeuron[] trainingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons) throws Exception {
        try {
            DataExchange.datain = new ArrayList<Double>();
            DataExchange.dataout = new ArrayList<Double>();
            TrainingTestingFunction.e = 1;
            if (data.size() == 0)
            {
                // "Данные не загружены.";
                throw new RuntimeException("Данные не загруженны");
            }
            double[] x = new double[4];
            double returnSignal;
            double sample;
            int numberSamples = 0;
            while (TrainingTestingFunction.e > E) {
                TrainingTestingFunction.e = 0;
                for (; numberSamples < data.size(); ) {
                    try {
                        x[0] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                        numberSamples++;
                        x[1] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                        numberSamples++;
                        x[2] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                        numberSamples++;
                        sample = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                        numberSamples++;
                    } catch (Exception e) {
                        Log.debug("NetworkFunctional#trainingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons)",
                                e.toString(), false);
                        numberSamples = 0;
                        continue;
                    }
                    returnSignal = TrainingTestingFunction.receiveSignal(neurons, x);
                    flag = MainFunctional.condition(returnSignal, sample);
                    if(flag)
                    {
                        break;
                    }
                    returnSignal = Calculation.funActivator(returnSignal);
                    neurons = TrainingTestingFunction.returnSignal(neurons, x, returnSignal, sample);
                    DataExchange.datain.add(sample);
                    DataExchange.dataout.add(returnSignal);
                }
            }
            err = Calculation.error(DataExchange.datain, DataExchange.dataout);
            System.out.println(err);
        } catch (RuntimeException e) {
            Log.debug("NetworkFunctional#trainingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons)",
                    e.toString(), e.getStackTrace());
        }
        return neurons;

    }

    public static void testingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons) {
        try {
            DataExchange.datain = new ArrayList<Double>();
            DataExchange.dataout = new ArrayList<Double>();
            if (data.size() == 0) {
                // "Данные не загружены.";
                throw new RuntimeException("Данные не загруженны");
            }
            double[] x = new double[4];
            double returnSignal;
            double sample;
            int numberSamples = 0;
            while (numberSamples <= data.size()) {
                try {
                    x[0] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                    numberSamples++;
                    x[1] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                    numberSamples++;
                    x[2] = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                    numberSamples++;
                    sample = Calculate.normalize(data.get(numberSamples), TrainingTestingFunction.MO, TrainingTestingFunction.D);
                    numberSamples++;
                } catch (Exception e) {
                    Log.debug("NetworkFunctional#testingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons)",
                            e.toString(), false);
                    break;
                }
                returnSignal = MainFunctional.receiveSignalThreeRoot(neurons, x);
                DataExchange.datain.add(sample);
                DataExchange.dataout.add(returnSignal);
            }
            double av = Calculation.average(DataExchange.dataout);
            SCO = Calculation.sd(DataExchange.dataout, av);
            System.out.println("СКО: " + SCO);
            err = Calculation.error(DataExchange.datain, DataExchange.dataout);
            System.out.println("Ошибка прогнозирования: " + err);
        }
        catch (RuntimeException e)
        {
            Log.debug("NetworkFunctional#trainingNetworkThreeRoot(ArrayList<Double> data, ModelNeuron[] neurons)",
                    e.toString(), e.getStackTrace());
        }
    }

}
