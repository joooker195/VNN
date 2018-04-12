package mainfunction;

import math.Calculate;
import view.windows.ViewWindows;
import xml.DataExchange;

import java.util.ArrayList;

public class NetworkFunctional
{
    public static double SCO = 0;
    public static double err = 1;
    private static double E = 0.0001;
    private static boolean flag;

    public static ArrayList trainingNetwork(ArrayList<Double> data, ArrayList neurons) throws Exception {
        try {
            DataExchange.datain = new ArrayList<Double>();
            DataExchange.dataout = new ArrayList<Double>();
            TrainingTestingFunction.e = 1;
            if (data.size() == 0) {
                // "Данные не загружены.";
                throw new RuntimeException("Данные не загруженны");
            }
            double[] x = new double[3];
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
                        ViewWindows.setInfoToConsole(e.getLocalizedMessage());
                        numberSamples = 0;
                        continue;
                    }
                    returnSignal = TrainingTestingFunction.receiveSignal(neurons, x);
                    flag = TrainingTestingFunction.condition(returnSignal, sample);
                    if (flag) {
                        break;
                    }
                    returnSignal = Calculate.funActivator(returnSignal);
                    neurons = TrainingTestingFunction.returnSignal(neurons, x, returnSignal, sample);
                    DataExchange.datain.add(sample);
                    DataExchange.dataout.add(returnSignal);
                }
            }
            err = Calculate.error(DataExchange.datain, DataExchange.dataout);

            System.out.println(err);
        } catch (RuntimeException e) {
            ViewWindows.setInfoToConsole(e.getLocalizedMessage());
        }
        return neurons;

    }

    public static void testingNetwork(ArrayList<Double> data, ArrayList neurons) {
        try {
            DataExchange.datain = new ArrayList<Double>();
            DataExchange.dataout = new ArrayList<Double>();
            if (data.size() == 0) {
                // "Данные не загружены.";
                throw new RuntimeException("Данные не загруженны");
            }
            double[] x = new double[3];
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
                }
                catch (Exception e) {
                    ViewWindows.setInfoToConsole(e.getLocalizedMessage());
                    break;
                }
                returnSignal = TrainingTestingFunction.receiveSignal(neurons, x);
                DataExchange.datain.add(sample);
                DataExchange.dataout.add(returnSignal);
            }
            double av = Calculate.average(DataExchange.dataout);
            SCO = Calculate.sd(DataExchange.datain, av);
            System.out.println("СКО: " + SCO);
            ViewWindows.setSco(SCO);
            err = Calculate.error(DataExchange.datain, DataExchange.dataout);
            ViewWindows.setErr(err);
            System.out.println("Ошибка прогнозирования: " + err);
        }
        catch (RuntimeException e)
        {
           ViewWindows.setInfoToConsole(e.getLocalizedMessage());
        }
    }

}
