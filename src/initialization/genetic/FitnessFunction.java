package initialization.genetic;

import initialization.InitRandom;
import math.Calculate;
import logs.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ксю on 10.09.2017.
 */
public class FitnessFunction implements IFitnessFunction{

   // public Log log = new Log();
    StringBuffer s;

    public long prepareTime = 0;
    public long checkTime = 0;
    public long sortingTime = 0;

    private static final int BIT_TO_INT = 8;
    private int pathLength = 0;
    private int[] path = null;
    private int[] seq = null;
    private int vertexCount;
    private double[][] matrix;


    public FitnessFunction(String filename) throws IOException {
        super();
        s = new StringBuffer();
        generateRandomFile(150);
        Scanner in = new Scanner(new FileReader("ffout.txt"));
        this.vertexCount = in.nextInt();
        this.matrix = new double[this.vertexCount][this.vertexCount];
        s.append("vertexCount = "+ vertexCount).append("\n");
        for(int i=0;i<this.vertexCount;i++){
            for(int j=0;j<this.vertexCount;j++){
                this.matrix[i][j] = Double.valueOf(in.next());
            }
        }
        if(Log.isDebugEnable)
        {
            Log.debug("FitnessFunction#FitnessFunction(String filename)",  "run", false);
        }

        in.close();
        this.pathLength = vertexCount;
        this.path = new int[this.pathLength];
        this.seq = new int[this.pathLength];
    }


    @Override
    public int getArity() {

        return this.pathLength*BIT_TO_INT;
    }

    @Override
    public long run(long[] genom) {
        long old = System.currentTimeMillis(); //time
        s = new StringBuffer();
        int offset=0;
        int vertexNumber=0;
        int index = 0;
        long tmp = 0;

        for (int i=0;i<this.pathLength/BIT_TO_INT;i++){
            offset = i<<3;
            tmp = genom[i];
            for (int j=0;j<BIT_TO_INT;j++){
                vertexNumber = (int)(tmp & 255);
                index = offset+j;
                this.path[index] = vertexNumber;
                this.seq[index] = index;
                tmp >>= 8;
            }
        }

        this.prepareTime += (System.currentTimeMillis()-old); //time
        old = System.currentTimeMillis(); //time

        qsort(this.path,this.seq,0,this.pathLength-1);

        this.sortingTime += (System.currentTimeMillis()-old); //time
        old = System.currentTimeMillis(); //time

        long pathLength = this.checkPath(this.seq);

        this.checkTime += (System.currentTimeMillis()-old); //time

        return (Long.MAX_VALUE-pathLength);
    }

    private void qsort(int[] arrayToSort, int[] arrayToMix,int l, int r){
        int i = l;
        int j = r;
        int tmp = 0;
        int pivot = arrayToSort[(l+r)>>1];

        while (i <= j) {
            while (arrayToSort[i] < pivot) {i+=1;}
            while (arrayToSort[j] > pivot) {j-=1;}

            if (i <= j) {
                tmp = arrayToSort[i];
                arrayToSort[i] = arrayToSort[j];
                arrayToSort[j] = tmp;
                tmp = arrayToMix[i];
                arrayToMix[i] = arrayToMix[j];
                arrayToMix[j] = tmp;
                i+=1;
                j-=1;
            }
        }
        if (l < j){
            qsort(arrayToSort, arrayToMix, l, j);
        }
        if (i < r){
            qsort(arrayToSort, arrayToMix, i, r);
        }
    }

    public long checkPath(int[] path){
        long result = 0;
        s = new StringBuffer();
        int pathLength = path.length;
        int predVertex = path[0];
        int nextVertex = 0;
        try {
            for (int i = 1; i < pathLength; i++) {
                nextVertex = path[i];
                result += this.matrix[predVertex][nextVertex];
                /*if (log.isDebugEnable()) {
                    s.append("matrix = " + matrix[predVertex][nextVertex]).append(" result = " + result).append("\n");
                }*/
                predVertex = nextVertex;
            }
            /*if (log.isDebugEnable()) {
                log.debug("FitnessFunction#checkPath(int[] path)", s.toString(), false);
            }*/

        }
        catch (Exception e)
        {
            Log.debug("FitnessFunction#result()", "mess: "+e, true);
        }
        return result;

    }

    public ArrayList<Double> result(){
        ArrayList<Double> r = new ArrayList<Double>();
      //   s = new StringBuffer();
        String a = "";
        long result = 0;
        int pathLength = this.path.length;
        int predVertex = this.path[0];
        int nextVertex = 0;
        try {
            for (int i = 1; i < 40; i++) {
                nextVertex = path[i];
                result += this.matrix[predVertex][nextVertex];
             //   System.out.println(this.matrix[predVertex][nextVertex]);
                if (Log.isDebugEnable) {
                   // s.append("matrix = " + matrix[predVertex][nextVertex]).append(" result = " + result).append("\n");
                    a = a + "matrix = " + matrix[predVertex][nextVertex] +" result = " + result+"\n";

                }
                r.add(this.matrix[predVertex][nextVertex]);
                predVertex = nextVertex;
            }
            if (Log.isDebugEnable) {
                Log.debug("FitnessFunction#checkPath(int[] path)", a, false);
            }
        }
        catch (Exception e)
        {
            Log.debug("FitnessFunction#checkPath(int[] path)", " errorMes = " + e, true);
        }
        return r;
    }


    public static void generateRandomFile(int n) throws IOException {
        Random random = new Random();
        BufferedWriter out = new BufferedWriter(new FileWriter("ffout.txt"));
        out.write(150+"\n");

        double[][] matrix = new double[n][n];

        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
             //   matrix[i][j] = Calculate.getRandom();
                matrix[i][j] = InitRandom.initGaussianRandomWeight();
                matrix[j][i] = matrix[i][j];
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                out.write(matrix[i][j]+"\n");
            }
            out.write("\n");
        }

        out.flush();
        out.close();
    }
}
