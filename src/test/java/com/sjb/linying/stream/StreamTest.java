package com.sjb.linying.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    @DisplayName("JavaStream同步API阻塞计算")
    public void runStream() {
        long current = System.currentTimeMillis();
        System.out.println("Start 1");
        Stream.of(1, 2, 3, 4, 5, 6).map(e -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return e;
        });
        System.out.println("End 1");
        System.out.println("Use " + (System.currentTimeMillis() - current) + "ms");
        current = System.currentTimeMillis();
        System.out.println("Start 2");
        Stream.of(1, 2, 3, 4, 5, 6).map(e -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return e;
        }).collect(Collectors.toList());
        System.out.println("End 2");
        System.out.println("Use " + (System.currentTimeMillis() - current) + "ms");
    }

}
