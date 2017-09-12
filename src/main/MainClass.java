package main;

import controll.Controller;
import logs.Log;
import view.View;

/**
 * Created by Ксю on 12.09.2017.
 */
public class MainClass
{
    /*public static String training = null;
    public static String testing = null;
     public static Log log = new Log();*/
    public static void runTraining(String file) throws Exception
    {
        Controller.getData(file);
        Controller.trainingNetwork();
    }
    public static void runTesting(String file) throws Exception
    {
        Controller.getData(file);
        Controller.testingNetwork();
        Controller.setData("file.xlsx");
    }

    public static void main(String[] args) throws Exception
    {
        Log.isDebugEnable = true;
        View.setvisibleView();
    }
}
