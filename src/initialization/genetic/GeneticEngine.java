package initialization.genetic;

import java.util.Random;


public class GeneticEngine {

    private Random random = new Random();
    private static FitnessFunction fitnessFunction;
    private int genomLength; //Длина генома в битах
    private long generationCount; //Кол-во поколений
    private int individualCount; //Кол-во Геномов(Индивидов,Особей) в поколении
    private SelectionType selectionType; //Тип Селекции
    private CrossingType crossingType; //Тип Скрещивания
    private boolean useMutation; //Использовать мутацю
    private double mutationPercent; //Как часто происходит мутация
    private static long[][] genomListParents;
    private static long[][] genomListOffsprings;
    private static long currentGeneration = 0;

    public static final int OCTET_LENGTH = 64; // Кол-во битов в лонге
    public static final int MASK_FOR_MOD = OCTET_LENGTH - 1; // вместо "x%64" я использую "x & MASK_FOR_MOD"
    public static final int SHIFT_FOR_DIVISION; // вместо "x/64" - "x >> SHIFT_FOR_DIVISION"
    static {
        int shiftForDivision = 0;
        int tmp = OCTET_LENGTH;
        while (tmp > 1) {
            tmp >>= 1;
            shiftForDivision++;
        }
        SHIFT_FOR_DIVISION = shiftForDivision;
    }

    public enum SelectionType { //селекция
        TOURNEY, //рутнир
        ROULETTE_WHEEL //рулетка
    }

    public enum CrossingType { //скрещивание
        ONE_POINT_RECOMBINATION, //Одноточечная рекомбинация.
        TWO_POINT_RECOMBINATION, //Двуточечная рекомбинация.
        ELEMENTWISE_RECOMBINATION, //Поэлементное скрещивание.
        ONE_ELEMENT_EXCHANGE //Обмен одним геном.
    }

    public GeneticEngine(FitnessFunction fitnessFunction) {...} //Конструктор


    public long[] run()
    { //Основное тело
        this.genomListParents = new long[this.individualCount][];
        this.genomListOffsprings = new long[this.individualCount][];

        // Первая генерация
        this.generateFirstGeneration();

        while (this.currentGeneration < this.generationCount) {

            this.selection();
            this.crossing();
            if (this.useMutation) {
                this.mutation();
            }

            long[][] tmp = this.genomListParents;
            this.genomListParents = this.genomListOffsprings;
            this.genomListOffsprings = tmp;

            this.currentGeneration++;
        }

        long bestFitnessFunctionResult = 0;
        long[] bestGenom = null;
        for (long[] genom : this.genomListParents) {
            long fitnessFunctionResult = this.fitnessFunction.run(genom);
            if (bestFitnessFunctionResult <= fitnessFunctionResult) {
                bestGenom = genom;
                bestFitnessFunctionResult = fitnessFunctionResult;
            }
        }

        return bestGenom;
    }


    private void generateFirstGeneration() {...} //генерация первого поколения



    private void selection() {//Процедура селекции
        switch (selectionType) {
            case ROULETTE_WHEEL: {
                float[] wheel = new float[this.individualCount];//геномы

                wheel[0] =  FF.getFitnessFunctionResult(0, genomListParents, currentGeneration); //Значение ФитнессФункции для 1-ого генома

                for (int i = 1; i < this.individualCount; i++)
                {
                    wheel[i] = wheel[i - 1] + FF.getFitnessFunctionResult(i, genomListParents, currentGeneration); //Значение ФитнессФункции для i-ого генома
                }
                float all = wheel[this.individualCount - 1];

                for (int i = 0; i < this.individualCount; i++) {
                    float index = Math.abs(this.random.nextFloat()) * all;

                    int l = 0;
                    int r = individualCount - 1;
                    int c = 0;
                    while (l < r)
                    {
                        c = (l + r) >> 1;
                        if (index <= wheel[c])
                        {
                            r = c;
                        }
                        else{
                            l = c + 1;
                        }
                    }
                    this.genomListOffsprings[i] = this.genomListParents[l].clone();
                }
                break;
            }
            case TOURNEY: {
                for (int i = 0; i < this.individualCount; i++) {

                    int index1 = random.nextInt(individualCount);
                    int index2 = random.nextInt(individualCount);

                    long fr1 = FF.getFitnessFunctionResult(index1, genomListParents, currentGeneration); //Значение ФитнессФункции для index1 Генома
                    long fr2 = FF.getFitnessFunctionResult(index2, genomListParents, currentGeneration); //Значение ФитнессФункции для index2 Генома

                    this.genomListOffsprings[i] = fr1 > fr2 ? this.genomListParents[index1].clone() : this.genomListParents[index2].clone();
                }
                break;
            }
        }
    }


    private void crossing() {
        for (int i = 0; i < individualCount / 2; i++) {
            int index1 = i << 1;
            int index2 = index1 | 1;
            cross(this.genomListOffsprings[index1], this.genomListOffsprings[index2]);
        }
    }


    private void cross(long[] genom1, long[] genom2)//Процедура скрещивания
    {
        switch (crossingType) {
            case ELEMENTWISE_RECOMBINATION: {
                for (int outerOffset = 0; outerOffset < this.individualCount; outerOffset++) {
                    long mask = this.random.nextLong(); // какие биты менять, а какие нет (1-обмениваем битами, 0-оставляем)
                    long swapMask = (genom1[outerOffset] ^ genom2[outerOffset]) & mask;

                    genom1[outerOffset] ^= swapMask;
                    genom2[outerOffset] ^= swapMask;
                }
                break;
            }
        }

        /*Чтобы обменять между собой числа a и b необходимо:
        swapMask = a xor b;
        a = a xor swapMask;
        b = b xor swapMask;

        А если на swapMask мы наложим (and) mask, то у a и b поменяются только те биты, которые == 1 в числе mask.

            swapMask = (a xor b) and mask;
        a = a xor swapMask;
        b = b xor swapMask;*/
    }

    private void mutation() {//Процедура мутации
        for (long[] genom : this.genomListOffsprings) {
            if (random.nextDouble() <= mutationPercent) {
                mutate(genom);
            }
        }
    }

    private void mutate(long[] genom) {
        int index = this.random.nextInt(this.genomLength);
        int outerOffset = index >> SHIFT_FOR_DIVISION;
        int innerOffset = (index & MASK_FOR_MOD);
        long mask = 1L << innerOffset;
        genom[outerOffset] ^= mask;

        /*Чтобы инвертировать бит необходимо:
        bit = bit xor 1;
        Следовательно, чтобы инвертировать любой бит в числе необходимо сдвинуть единицу на нужную позицию.*/

    }
}