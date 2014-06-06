package io.core9.plugin.test.async;

import rx.Observable;

public class RxUsingJava8 {

    public static void main(String args[]) {

        /*
         * Example using single-value lambdas (Func1)
         */
        Observable.from(1, 2, 3, 4, 5)
                .filter((v) -> {
                    return v < 4;
                })
                .subscribe((value) -> {
                    System.out.println("Value: " + value);
                });

        /*
         * Example with 'reduce' that takes a lambda with 2 arguments (Func2)
         */
        Observable.from(1, 2, 3, 4, 5)
                .reduce((seed, value) -> {
                    // sum all values from the sequence
                    return seed + value;
                })
                .map((v) -> {
                    return "DecoratedValue: " + v;
                })
                .subscribe((value) -> {
                    System.out.println(value);
                });

    }
}