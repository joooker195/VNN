package initialization.annealing;

/**
 * Created by Ксю on 09.03.2017.
 */
public class Metric
{
    //теорема Пифагора для евклида (расстояние между двумя точками маршрута)
    private static double distance;
    public static double calculate(double ax, double ay, double bx, double by)
    {
        return Math.sqrt(Math.pow(ax - ay,2) + Math.pow(bx - by,2));
    }
}