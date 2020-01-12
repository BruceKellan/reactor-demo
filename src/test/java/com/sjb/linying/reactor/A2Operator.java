package com.sjb.linying.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class A2Operator {

    @Test
    @DisplayName("测试使用FlatMap，流的合并是异步的")
    void flatMap() {
        StepVerifier.create(
                Flux.just("flux", "mono")
                        .flatMap(s -> Flux.fromArray(s.split("\\s*"))
                                .delayElements(Duration.ofMillis(100))
                        )
                        .doOnNext(System.out::print)
        ).expectNextCount(8)
                .verifyComplete();
    }

    @Test
    @DisplayName("测试使用filter")
    void filter() {
        StepVerifier.create(Flux.range(1, 6)
                .map(i -> i * i))
                .expectNext(1, 4, 9, 16, 25, 36)
                .expectComplete()
                .verify();
    }

    Flux<String> generateZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        return Flux.fromArray(desc.split("\\s+"));
    }

    @Test
    @DisplayName("测试使用zip")
    void zip() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.zip(generateZipDescFlux(),
                Flux.interval(Duration.ofMillis(200)))
                .subscribe(e -> System.out.println(e.getT1()), null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

}
