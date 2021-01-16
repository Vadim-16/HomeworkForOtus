package ca.study.purpose;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;

import static com.google.common.base.Functions.constant;

public class HelloOtus {

    public static void main(String[] args) {
        Function<Object, @Nullable Integer> a = constant(5);
        System.out.println("Hello world!");
        System.out.println(a);
    }


}
