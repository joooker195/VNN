package main;

import controll.Controller;
import model.ModelNetwork;
import view.ViewConsole;
import view.windows.ViewWindows;

/**
 * Created by Ксю on 12.09.2017.
 */
public class MainClass
{
    public static void runTraining(String file) throws Exception
    {
        ViewWindows.setInfoToConsole("Началось обучение");
        Controller.getData(file);
        Controller.trainingNetwork();
    }
    public static void runTesting(String file) throws Exception
    {
        Controller.getData(file);
        Controller.testingNetwork();
        Controller.setData("file.xlsx");
        ProcessBuilder pb = new ProcessBuilder("cmd", "Excel.exe", "/r", "file.xlsx");
        pb.start();
    }

    public static void test()
    {
        ViewWindows.setInfoToConsole("Создается сеть");
        ModelNetwork mn = new ModelNetwork();
        mn.createNetwork();
    }

    public static void main(String[] args) throws Exception
    {
        ViewConsole.setvisibleView();
    }
}
