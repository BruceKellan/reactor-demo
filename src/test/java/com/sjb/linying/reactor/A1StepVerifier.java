package com.sjb.linying.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class A1StepVerifier {

    @Test
    @DisplayName("测试StepVerifier-Flux")
    void stepVerifierFlux() {
        StepVerifier.create(Flux.just(1, 2, 3, 4, 5))
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("测试StepVerifier-Mono")
    void stpVerifierMono() {
        StepVerifier.create(Mono.error(new Exception("some error")))
                .expectErrorMessage("some error")
                .verify();
    }


}
