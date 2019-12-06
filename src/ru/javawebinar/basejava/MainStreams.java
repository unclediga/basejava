package ru.javawebinar.basejava;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class MainStreams {
    public static void main(String[] args) {
        MainStreams mainStreams = new MainStreams();
//        System.out.println(mainStreams.minValue(new int[]{1, 2, 3, 3, 2, 3}));
//        System.out.println(mainStreams.minValue(new int[]{9, 8, 7}));
//        System.out.println(mainStreams.oddOrEven(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7)));
        mainStreams.oddOrEven(asList(0, 1, 2, 3, 4, 5, 6, 7));
    }

    int minValue(int[] values) {
        return stream(values).distinct().sorted().reduce(0, (acc, x) -> acc * 10 + x);
    }


    List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> list = integers;
        AtomicInteger cnt = new AtomicInteger(0);
//        List<Integer> collect = list.stream().peek(x -> cnt.set(cnt.incrementAndGet())).filter(x -> {if (cnt.get() % 2 == 0) return true; else return false;}).collect(Collectors.toList());
        List<Integer> collect = list.stream().filter(x -> (list.stream().reduce(0, (acc, y) -> acc + y)) == 3).collect(Collectors.toList());
        System.out.println("coll = " + collect);
        return list;
    }
}
