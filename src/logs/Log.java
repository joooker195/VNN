package logs;

import java.io.BufferedWriter;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Created by Ксю on 12.09.2017.
 */
public class Log
{
    public static boolean isDebugEnable = true;
    static BufferedWriter out;
    static StringBuffer  mes = new StringBuffer();
    BufferedReader reader = null;
    static String fileName = "D:\\Ксюша\\Универ\\TheDawnOfNewEra\\VNN\\debug\\debug.log";

    public void clear()
    {
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            out.write("");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Log() {
        try
        {
            // out = new BufferedWriter(new FileWriter(fileName));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                mes.append(line).append("\n");
            }
            reader.close();
            //  out = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.out.println("file debug not found " + e);
        }
    }


    public static void debug(String from, String message, boolean isCons) {
        if(isCons)
        {
            System.out.println("System.out: "+message);
        }
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            System.out.println("Message added to log from " + from);
            mes.append(getTime()+" ").append(from + ": " ).append(message).append("\n");
            //  out = new BufferedWriter(new FileWriter(fileName));
            out.write(mes.toString());
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("file debug not found " + e);
        }

    }

    private static String getTime()
    {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("mm:ss");
        return formatForDateNow.format(dateNow);
    }

    public void debug(String message, StackTraceElement[] el) {
        try {
            //   out = new BufferedWriter(new FileWriter(fileName));
            System.out.println("Error, see debug.log" + message);
            out.write(getTime()+" "+message+"\n");
            for(int i=0; i<el.length; i++) {
                out.write(el[i]+"\n");
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("file debug not found " + e);
        }
    }

    public static void debug(String from, String message, StackTraceElement[] el) {
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            System.out.println("From: "+ from +" Error, see debug.log: " + message);
            out.write(getTime()+" "+message+"\n");
            for(int i=0; i<el.length; i++) {
                out.write(el[i]+"\n");
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("file debug not found " + e);
        }
    }

}
