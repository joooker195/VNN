package view.windows;

public class ViewWindows
{
    private static String nameTrainingFile;
    private static String nameTestingFile;
    private static int numberOfInit = 1;
    private static String info;
    private static boolean isTraining = false;
    private static  String sco;
    private static String err;

    public static String getSco() {
        return sco;
    }

    public static void setSco(double sco) {
        ViewWindows.sco = String.valueOf(sco);
    }

    public static String getErr() {
        return err;
    }

    public static void setErr(double err) {
        ViewWindows.err = String.valueOf(err);
    }

    public static boolean isTraining() {
        return isTraining;
    }

    public static void setTraining(boolean training) {
        isTraining = training;
    }

    public static String getNameTrainingFile() {
        return nameTrainingFile;
    }

    public static void setNameTrainingFile(String nameTrainingFile) {
        ViewWindows.nameTrainingFile = nameTrainingFile;
    }

    public static String getNameTestingFile() {
        return nameTestingFile;
    }

    public static void setNameTestingFile(String nameTestingFile) {
        ViewWindows.nameTestingFile = nameTestingFile;
    }

    public static int getNumberOfInit() {
        return numberOfInit;
    }

    public static void setNumberOfInit(int numberOfInit) {
        ViewWindows.numberOfInit = numberOfInit;
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        ViewWindows.info = info;
    }

    public static void setInfoToConsole(String info)
    {
        System.out.println(info);
    }






}
