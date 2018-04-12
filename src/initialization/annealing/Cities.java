package initialization.annealing;

import java.util.ArrayList;

/**
 * Created by Ксю on 09.03.2017.
 */
public class Cities
{
    private ArrayList<Double> statesX = new ArrayList<Double>();
    private ArrayList<Double> statesY = new ArrayList<Double>();

    public Cities(ArrayList statesX, ArrayList statesY)
    {
        this.statesX = statesX;
        this.statesY = statesY;
    }

    public int counX()
    {
        return statesX.size();
    }
    public int counY()
    {
        return statesY.size();
    }

    public double getStateX(int i)
    {
        return statesX.get(i);
    }

    public double getStateY(int i)
    {
        return statesY.get(i);
    }

    public void setStatesX(ArrayList<Double> statesX) {
        this.statesX = statesX;
    }

    public void setStatesY(ArrayList<Double> statesY) {
        this.statesY = statesY;
    }
}
