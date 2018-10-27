package ua.training.stream_api_test;

import javafx.util.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestingGrounds {
    private static final Logger LOG = LoggerFactory.getLogger("consoleLogger");
    private int[] victim = {1, 0, -2, 5, 5, -5, 10, 9, -7, 0};

    // Helper method: returns stream of  array's values
    private IntStream getValuesStream() {
        return Arrays.stream(victim);
    }

    // Helper method: returns stream of array's index-value pairs
    private Stream<Pair<Integer, Integer>> getKeyValueStream() {
        List<Pair<Integer, Integer>> pairs = new LinkedList<>();
        IntStream.range(0, victim.length).forEach(x -> pairs.add(new Pair<>(x, victim[x])));
        return pairs.stream();
    }

    @Test
    public void displaysAverage() {
        LOG.info("1a. Найти среднее значение элементов массива: {}", victim);

        IntStream stream = Arrays.stream(victim);
        double average = stream.average().getAsDouble();

        LOG.info("Среднее значение элементов равно {} (исп. ст. методы)", average);
    }

    @Test
    public void displaysAverageWithoutUsingAverageMethod() {
       LOG.info("1b. Найти минимальный элемент, значение и индекс массива: {}", victim);

       double average = (double) getValuesStream().reduce(Integer::sum).getAsInt()/victim.length;

        LOG.info("Среднее значение элементов равно {} (не исп. ст. методы)", average);
    }

    @Test
    public void displaysMinimumElementValueAndIndex() {
        LOG.info("2. Найти минимальный элемент, значение и индекс массива: {}", victim);

        Pair<Integer, Integer> minElement = getKeyValueStream().reduce((p1,p2)->p1.getValue()>p2.getValue()?p2:p1).get();

        LOG.info("Минимальный элемент: индекс = {}, значение = {}", minElement.getKey(), minElement.getValue());
    }

    @Test
    public void displaysNumberOfZeroElements() {
        LOG.info("3. Посчитать количество элементов равных нулю в массиве: {}", victim);

        long numberOfZeros = getValuesStream().filter(x->Objects.equals(x,0)).count();

        LOG.info("Количество элементов равных нулю - {}", numberOfZeros);
    }

    @Test
    public void displaysNumberOfPositiveElements() {
        LOG.info("4. Посчитать количество элементов больше нуля в массиве: {}", victim);

        long numberOfPositives = getValuesStream().filter(x->x>0).count();

        LOG.info("Количество элементов больше нуля - {}", numberOfPositives);
    }

    @Test
    public void displaysArraysElementsMultipliedWithQuotient() {
        final int quotient = 11;

        LOG.info("5. Помножить элементы массива {} на число {}", victim, quotient);

        List<Integer> updatedValues = getValuesStream().boxed().map(x->x*quotient).collect(Collectors.toList());

        LOG.info("Изменённый массив: {}", updatedValues);
    }

    @Test
    public void displaysCountersForEachElement() {
        LOG.info("6. Подсчитать количество значений для каждого элемента массива {}", victim);

        String result = getValuesStream()
                        .distinct()
                        .mapToObj(x-> x + " -> " + getValuesStream().filter(y->y==x).count())
                        .collect(Collectors.joining(System.lineSeparator()));

        LOG.info("Вхождений элементов в массив: {}{}", System.lineSeparator(), result);
    }


}
