package com.boot3.myrestapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LambdaTest {
    @Test
    public void runnable() {
        //1. Anonymous Inner class 익명 내부 클래스
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner class");
            }
        });
        t1.start();

        //2. Lambda Expression
        Thread t2 = new Thread(() -> System.out.println("Lambda Expression"));
        t2.start();
    }
    
    @Test //@Disabled
    public void consumer() {
        List<String> list = List.of("aa", "bb", "cc"); //Immutable List
        //1. Anonymous Inner class
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s = " + s);
            }
        });

        //2.Lambda Expression
        //Consumer의 추상 메서드 void accept(T t)
        list.forEach(val -> System.out.println(" >>> " + val));

        //3.Method Reference (람다식을 simple하게 표현)
        list.forEach(System.out::println);
    }

    @Test
    public void predicate_operator() {
        List<Integer> integerList = List.of(10, 13, 20, 35, 40, 60, 55);

        integerList.stream()//Stream<Integer>
                .filter(val -> val % 2 == 0)
                .forEach(System.out::println);
        //2의 배수 합계
        int sum = integerList.stream()
                .mapToInt(Integer::intValue)//IntStream
                .sum();
        System.out.println("sum = " + sum);

    }

}