package com.sjb.linying.reactor;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class A3Schedulers {

    String getStringSync() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "reactor";
    }

    @Test
    @DisplayName("测试Elastic")
    void elastic() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(this::getStringSync)
                // subscribeOn将任务调度到Schedulers内置的弹性线程池执行
                .subscribeOn(Schedulers.elastic())
                .subscribe(System.out::println, null, countDownLatch::countDown);

    }

    void printThreadId(String step, Integer element) {
        System.out.println("[" + step + "]: " + element + "-" + Thread.currentThread().getId());
    }

    @Test
    @DisplayName("测试publishOn和subscribeOn")
    void publishAndSubscribe() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.range(1, 5)
                .map(e -> {
                    this.printThreadId("step 1", e);
                    return e;
                })
                .publishOn(Schedulers.parallel())
                .flatMap(e -> {
                    this.printThreadId("step 2", e);
                    return Flux.fromIterable(Lists.newArrayList(e, e * e)).delayElements(Duration.ofMillis(200));
                })
                .publishOn(Schedulers.elastic())
                .map(e -> {
                    this.printThreadId("step 3", e);
                    return e;
                })
                .subscribeOn(Schedulers.elastic())
                .subscribe(null, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

}
