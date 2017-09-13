package view;

import logs.Log;
import main.MainClass;

import java.util.Scanner;

/**
 * Created by Ксю on 12.09.2017.
 */
public class View
{
   // private static Scanner in  = new Scanner(System.in);
    public static void setvisibleView() throws Exception {
        String end = "no";
        System.out.println("Welcome!");

        String isEnd = "no";
        while (!isEnd.equals("yes")) {
            System.out.println("1-training");
            System.out.println("2-tesiting");
            System.out.println("3-test new model");
            switch (Integer.parseInt(inputCommand()))
            {
                case 1: {
                    System.out.println("1-brent");
                    System.out.println("2-wti");
                    switch ((Integer.parseInt(inputCommand())))
                    {
                        case 1:
                        {
                            System.out.println("Start training with Brent");
                            MainClass.runTraining("dataBrent.xls");
                            break;
                        }
                        /*case 2:
                        {
                            System.out.println("Start training with WTI");
                            MainClass.runTraining("dataWTI.xls");
                        }*/
                    }
                    break;
                }
                case 2:
                {
                    System.out.println("1-brent");
                    System.out.println("2-wti");
                    switch ((Integer.parseInt(inputCommand())))
                    {
                        case 1:
                        {
                            System.out.println("Start testing with Brent");
                            MainClass.runTesting("dataBrent.xls");
                            break;
                        }
                        /*case 2:
                        {
                            System.out.println("Start testing with WTI");
                            MainClass.runTesting("dataWTI.xls");
                        }*/
                    }
                    break;

                }
                case 3:
                {
                    System.out.println("run test");
                    MainClass.test();
                }
            }

            System.out.println("is End?");
            isEnd = inputCommand();
        }

    }
    public static String inputCommand()
    {
        Scanner in  = new Scanner(System.in);
        return in.next();
    }

    public static void printSomeInfo(String info)
    {
        System.out.println(info);
    }
}
