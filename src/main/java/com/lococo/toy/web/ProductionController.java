package com.lococo.toy.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class ProductionController {

    private ProductionRepository repository;

    @GetMapping("/hello/mono")
    public Mono<Production> hello() {
        Production production = new Production(null, "아이폰", null, null);
        Mono<Production> save = repository.save(production);

//        Mono<Production> byId = repository.findById("1");
        return save;
    }

    @GetMapping("/hello/flux")
    public Flux<Production> hello2() {
        return repository.findAll();
    }
}
