package com.lococo.toy.web;

import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductionRepositoryTest {

    @Autowired
    private ProductionRepository repository;

    @Autowired
    private ReactiveMongoOperations operations;

    @Before
    public void setUp() {

        Mono<MongoCollection<Document>> then = operations.collectionExists(Production.class)
                .flatMap(exists -> exists ? operations.dropCollection(Production.class) : Mono.just(exists))
                .then(operations.createCollection(Production.class, CollectionOptions.empty()
                        .size(1024 * 1024) //
                        .maxDocuments(100) //
                        .capped()));

        StepVerifier.create(then).expectNextCount(1).verifyComplete();
    }

    @Test
    public void givenValue_whenFindAllByValue_thenFindAccount() {
        repository.save(new Production(null, "Bill", null, null)).block();
        Flux<Production> accountFlux = repository.findAll();

        StepVerifier
                .create(accountFlux)
                .assertNext(account -> {
                    assertEquals("Bill", account.getName());
                    assertNotNull(account.getId());
                })
                .expectComplete()
                .verify();
    }
}