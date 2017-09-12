package xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.ModelNeuron;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксю on 13.06.2016.
 */
public class SaveNetwork
{
    public static ModelNeuron[] unmarshalling(String fileName) throws IOException, ClassNotFoundException
    {//чтение из xml
        ArrayList<ModelNeuron> list;
        XStream xStream = new XStream(new DomDriver());//создаем объект
        xStream.alias("Data", List.class);
        xStream.registerConverter((Converter) new EncodedByteArrayConverter());
        list = (ArrayList<ModelNeuron>)xStream.fromXML(new File(fileName));
        ModelNeuron[] neurons = new ModelNeuron[list.size()];
        for (int i=0; i<list.size();i++)
        {
            neurons[i] = list.get(i);
        }
        return neurons;
    }

    public static void marshalling(ModelNeuron[] neurons) throws IOException
    {//запись в xml
        ArrayList<ModelNeuron> a = new ArrayList<ModelNeuron>();
        for(int i=0; i<neurons.length; i++)
        {
            a.add(neurons[i]);
        }
        XStream xStream = new XStream(new DomDriver()); //создаем объект XStream
        xStream.alias("Data", List.class);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("savedNetwork.xml")));//записываем текст xml обычным бафером в файл saveData.xml
        String xml = xStream.toXML(a);//запихиваем в xml наш объект "большой список"
        bufferedWriter.write(xml);//записываем xml в файл
        bufferedWriter.close();
    }
}
