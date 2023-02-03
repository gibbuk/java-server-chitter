package com.chitter.server.repository;

import com.chitter.server.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeepsRepository extends MongoRepository<Peep, String> {
}
