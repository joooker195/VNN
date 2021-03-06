package view;


import main.MainClass;

import java.util.Scanner;

/**
 * Created by Ксю on 12.09.2017.
 */
public class ViewConsole
{
    private static boolean isModeConcole=false;
   // private static Scanner in  = new Scanner(System.in);


    public static boolean isModeConcole() {
        return isModeConcole;
    }

    public static void setModeConcole(boolean modeConcole) {
        isModeConcole = modeConcole;
    }

    public static void setvisibleView() throws Exception {
        String end = "no";
        System.out.println("Welcome!");

        String isEnd = "no";
        while (!isEnd.equals("y")) {
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
                        case 2:
                        {
                            System.out.println("Start training with WTI");
                            MainClass.runTraining("dataWTI.xls");
                        }
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
                            MainClass.runTesting("dataTestBrent.xls");
                            break;
                        }
                        case 2:
                        {
                            System.out.println("Start testing with WTI");
                            MainClass.runTesting("dataTestWTI.xls");
                        }
                    }
                    break;

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

    public static String chooseInit()
    {
        System.out.println("1-random");
        System.out.println("2-annealing");
        System.out.println("3-genetic");
        return inputCommand();
    }

    public static void printSomeInfo(String info)
    {
        System.out.println(info);
    }
}
