package com.example.main_service.Repository;

import com.example.main_service.Entity.ConversionsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends ReactiveCrudRepository<ConversionsEntity, Long> {
}