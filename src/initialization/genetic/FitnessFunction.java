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
    Random r = new Random();

    public long prepareTime = 0;
    public long checkTime = 0;
    public long sortingTime = 0;

    private static final int BIT_TO_INT = 8;
    private int pathLength = 0;//столько же, соклько и нейронов
    private int[] path = null;
    private int[] seq = null;
    private int vertexCount;//количество геномов(нейронов)
    private int[][] matrix;


    public FitnessFunction(String filename) throws IOException {
        super();
        generateRandomFile(320);
        Scanner in = new Scanner(new FileReader("ffout.txt"));
        this.vertexCount = in.nextInt();
       // this.matrix = new double[this.vertexCount][this.vertexCount];
        in.close();
        this.pathLength = vertexCount;
        this.path = new int[this.pathLength];
        this.seq = new int[this.pathLength];
    }


    @Override
    public int getArity() {

        return this.pathLength*BIT_TO_INT;//Кол-во битов в геноме
    }

    @Override
    public long run(long[] genom)
    {
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
        qsort(this.path,this.seq,0,this.pathLength-1);
        long pathLength = this.checkPath(this.seq);
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
        int pathLength = path.length;
        int predVertex = path[0];
        int nextVertex = 0;
        try {
            for (int i = 1; i < pathLength; i++) {
                nextVertex = path[i];
                double a = this.matrix[predVertex][nextVertex];
                result += a;
                predVertex = nextVertex;
            }

        }
        catch (Exception e)
        {
            Log.debug("FitnessFunction#result()", "mess: "+e, true);
        }
        return result;

    }


    private void generateRandomFile(int n) throws IOException {
        Random random = new Random();
        BufferedWriter out = new BufferedWriter(new FileWriter("ffout.txt"));
        out.write(n+"\n");

        this.matrix = new int[n][n];

        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
             //   matrix[i][j] = Calculate.getRandom();
                this.matrix[i][j] = r.nextInt(320);
                this.matrix[j][i] = this.matrix[i][j];
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
