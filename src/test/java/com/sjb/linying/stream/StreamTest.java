package com.sjb.linying.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StreamTest {

    @Test
    @DisplayName("Java Collection Stream API 延迟计算")
    public void runStream() {
        Arrays.asList(1, 2, 3, 4, 5, 6).stream().map(e -> {
            System.out.print(e);
            return e;
        });
        System.out.println();
        Arrays.asList(1, 2, 3, 4, 5, 6).stream().map(e -> {
            System.out.print(e);
            return e;
        }).collect(Collectors.toList());
    }

}
