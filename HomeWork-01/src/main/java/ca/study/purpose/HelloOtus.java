package ca.study.purpose;


import com.google.common.base.Function;

import static com.google.common.base.Functions.constant;

public class HelloOtus {

    public static void main(String[] args) {
        Function<Object, Integer> a = constant(5);
    }


}
