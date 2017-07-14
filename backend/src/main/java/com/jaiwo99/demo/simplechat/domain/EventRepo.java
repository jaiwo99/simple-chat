package com.jaiwo99.demo.simplechat.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepo extends MongoRepository<Event, String> {
}
