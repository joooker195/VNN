package model;

/**
 * Created by Ксю on 12.09.2017.
 */
public class ModelNeuron
{
    // положение нейрона в сети
    private String status;
    // вес
    private double w;
    // количество слоев
    private double countLayer;


    public void setStatus(String status) {
        this.status = status;
    }


    public double getWeight() {
        return w;
    }

    public void setWeight(double w) {
        this.w = w;
    }


    public void setCountLayer(double countLayer) {
        this.countLayer = countLayer;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\n").append("neuron~").append(status).append("\n").append( "countLayer = ").append(countLayer);
        sb.append(" weight = ").append(w).append("\n");

        return sb.toString();

    }
}
