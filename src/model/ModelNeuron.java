package model;

/**
 * Created by Ксю on 12.09.2017.
 */
public class ModelNeuron
{
    // положение нейрона в сети
    private String status;
    //сигнал
    private double x;
    // вес
    private double w;
    //фактическое значение в узле
    private double y;
    // количество слоев
    private double countLayer;

    // ссылка на нейроны
    private ModelNeuron parent;
    private ModelNeuron left;
    private ModelNeuron hiddenLeft;
    private ModelNeuron hiddenRight;
    private ModelNeuron right;


    public ModelNeuron getHiddenRight() {
        return hiddenRight;
    }

    public void setHiddenRight(ModelNeuron hiddenRight) {
        this.hiddenRight = hiddenRight;
    }

    public ModelNeuron getHiddenLeft() {
        return hiddenLeft;
    }

    public void setHiddenLeft(ModelNeuron hiddenLeft) {
        this.hiddenLeft = hiddenLeft;
    }

    public ModelNeuron getParent() {
        return parent;
    }

    public void setParent(ModelNeuron parent) {
        this.parent = parent;
    }

    public ModelNeuron getLeft() {
        return left;
    }

    public void setLeft(ModelNeuron left) {
        this.left = left;
    }

    public ModelNeuron getRight() {
        return right;
    }

    public void setRight(ModelNeuron right) {
        this.right = right;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSignal() {
        return x;
    }

    public void setSignal(double x) { this.x = x; }

    public double getWeight() {
        return w;
    }

    public void setWeight(double w) {
        this.w = w;
    }

    public double getValueNode() { return y; }

    public void setValueNode(double y) { this.y = y; }

    public double getCountLayer() {
        return countLayer;
    }

    public void setCountLayer(double countLayer) {
        this.countLayer = countLayer;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("neuron~").append(status).append("\n").append( "countLayer = ").append(countLayer);
        sb.append("weight = ").append(w).append(" signal = ").append(x).append(" valnode = ").append(y).append("\n");
        sb.append("right = ").append(right).append("\n");
        sb.append("left = ").append(left).append("\n");
        sb.append("hiddenLeft = ").append(hiddenLeft).append("\n");
        sb.append("hiddenRight = ").append(hiddenRight).append("\n");
        sb.append("parent = ").append(parent).append("\n");

        return sb.toString();

    }
}
