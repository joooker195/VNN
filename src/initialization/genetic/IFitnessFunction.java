package initialization.genetic;

/**
 * Created by Ксю on 10.09.2017.
 */
public interface IFitnessFunction
{
    int getArity(); //Кол-во битов в геноме
    long run(long[] genom); //Собственно сама ФитнессФункция от генома.
}
