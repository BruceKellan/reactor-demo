package com.sjb.linying.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.UnicastProcessor;

import java.util.Arrays;

@DisplayName("Demo测试A：冷流热流")
class AHotCold {

    @Test
    @DisplayName("冷流：订阅前什么都不会发生，一个元素被多次订阅后将分别重复执行")
    void cold1() {
        Flux<String> source = Flux.fromIterable(Arrays.asList("test1", "test2")).map(String::toUpperCase);
        source.subscribe(e -> System.out.println("Subscriber 1:" + e));
        source.subscribe(e -> System.out.println("Subscriber 2:" + e));
    }

    @Test
    @DisplayName("热流：在组装期就可以拿到数据，之后被谁订阅了，就重新发送数据给订阅者")
    void hot() {
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        hotSource.onNext("blue");
        hotSource.onNext("green");
        Flux<String> hotFlux = hotSource.publish().autoConnect().map(String::toUpperCase);
        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));
        hotSource.onNext("orange");
        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));
        hotSource.onNext("purple");
        hotFlux.subscribe(d -> System.out.println("Subscriber 3 to Hot Source: " + d));
        hotSource.onNext("red");
    }


}
