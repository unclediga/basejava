package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Arrays.stream;

public class MainStreams {
    public static void main(String[] args) {
        MainStreams mainStreams = new MainStreams();
        System.out.println(mainStreams.getMinNumber(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(mainStreams.getMinNumber(new int[]{9, 8, 7}));

        final int SIZE = 1000;
        int sum = 0;
        Random rnd = new Random();
        ArrayList<Integer> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            int num = rnd.nextInt(SIZE);
            list.add(num);
            sum += num;
        }
        System.out.printf("sum =%d : %s   \n", sum, list.subList(0, Math.min(list.size(), 20)));
        List<Integer> list2 = mainStreams.getOddOrEven(list);
        System.out.println(list2.subList(0, Math.min(list2.size(), 20)));
    }

    int getMinNumber(int[] values) {
        return stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    List<Integer> getOddOrEven(List<Integer> list) {
        Supplier<Combi> supplier = Combi::new;
        BiConsumer<Combi, Integer> accumulator = (acc, x) -> {
            if (x % 2 == 0) {
                acc.ev.add(x);
            } else {
                acc.od.add(x);
            }
            acc.sum += x;
        };
        BinaryOperator<Combi> combiner = (left, right) -> {
            left.ev.addAll(right.ev);
            left.od.addAll(right.od);
            left.sum += right.sum;
            return left;
        };
        Function<Combi, List<Integer>> finisher = c -> c.sum % 2 == 0 ? c.ev : c.od;

        return list
                .stream()
                .collect(Collector.of(supplier, accumulator, combiner, finisher));
    }

    static class Combi {
        int sum = 0;
        ArrayList<Integer> ev = new ArrayList<>();
        ArrayList<Integer> od = new ArrayList<>();
    }
}

