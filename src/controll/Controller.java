package controll;

import model.*;
import mainfunction.*;

import xml.DataExchange;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ксю on 12.09.2017.
 */
public class Controller
{
    private static ArrayList neurons;
    private static ArrayList<Double> data = new ArrayList<Double>();


    public static void trainingNetwork() throws Exception {
        ModelNetwork model = new ModelNetwork();
        Controller.neurons = model.createNetwork();
        neurons = NetworkFunctional.trainingNetwork(data, neurons);
    }

    public static void testingNetwork() throws Exception
    {
        NetworkFunctional.testingNetwork(data, neurons);
    }

    public static void getData(String file) throws IOException {

        data = DataExchange.readFromExcel(file, "Лист1");
        System.out.println("Данные получены.");
      //  View.info = "Данные получены.";
    }

    public static void setData(String file) throws IOException {
        //   DataExchange.writeToExcel("\\Result"+file);
        DataExchange.writeToExcel(file);
        System.out.println("Запись окончена.");
    }

    /*public static void setNetwork() throws IOException {
        SaveNetwork.marshalling(neurons);
        //View.info = "Сеть сохранена.";
    }

    public static void getNetwork(String file) throws IOException, ClassNotFoundException
    {
        neurons = SaveNetwork.unmarshalling(file);
        //View.info = "Сеть загружена.";
    }*/
}
