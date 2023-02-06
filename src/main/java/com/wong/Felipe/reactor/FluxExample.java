package com.wong.Felipe.reactor;

import com.wong.Felipe.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

//@Configuration
public class FluxExample {
    private final static Logger LOG = LoggerFactory.getLogger(FluxExample.class);

    @Bean
    public CommandLineRunner runFluxExample() {
        return args -> {
            EmitterProcessor<ToDo> stream = EmitterProcessor.create();
            Mono<List<ToDo>> promise = stream
                    .filter(ToDo::isCompleted)
                    .doOnNext(s -> LOG.info("FLUX >>> ToDo {}", s.getDescription()))
                    .collectList()
                    .subscribeOn(Schedulers.single());
            stream.onNext(new ToDo("Hello World", true));
            stream.onNext(new ToDo("Winter is coming", true));
            stream.onNext(new ToDo("Royal never give up", true));
            stream.onComplete();
            promise.block(Duration.ofMillis(1000));

        };

    }
}
