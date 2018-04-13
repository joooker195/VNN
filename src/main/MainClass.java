package main;

import controll.Controller;
import initialization.g.GA;
import model.ModelNetwork;
import org.apache.log4j.Logger;
import view.ViewConsole;
import view.windows.ViewWindows;

public class MainClass
{
    private static final Logger log = Logger.getLogger(MainClass.class);

    public static void runTraining(String file) throws Exception
    {
        log.debug("Началось обучение");
        ViewWindows.setInfoToConsole("Началось обучение");
        Controller.getData(file);
        Controller.trainingNetwork();
    }
    public static void runTesting(String file) throws Exception
    {
        log.debug("Началось тестирование");
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
        ViewConsole.setModeConcole(true);
        ViewConsole.setvisibleView();
        GA d = new GA();
        d.run();
    }
}
