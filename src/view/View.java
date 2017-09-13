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

        while(!end.equals("yes")) {
            System.out.println("1-training");
            System.out.println("2-tesiting");
            if (inputCommand().equals("1")) {
                System.out.println("1-brent");
                System.out.println("2-wti");
                if (inputCommand().equals("1")) {
                    MainClass.runTraining("dataBrent.xls");
                } else if (inputCommand().equals("2")) {
                    MainClass.runTraining("dataWTI.xls");
                }
            } else if (inputCommand().equals("2")) {
                System.out.println("1-brent");
                System.out.println("2-wti");
                if (inputCommand().equals("1")) {
                    MainClass.runTesting("dataBrent.xls");
                } else if (inputCommand().equals("2")) {
                    MainClass.runTesting("dataWTI.xls");
                }
            }
            else if(inputCommand().equals("3"))
            {
                MainClass.test();
            }

            System.out.println("End?");
            end = inputCommand();
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
