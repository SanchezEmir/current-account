package com.nttdata.currentaccount.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.currentaccount.entity.CurrentAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICurrentAccountRepository extends ReactiveMongoRepository<CurrentAccount, String> {
  
  public Flux<CurrentAccount> findByCustomerId(String id);
  public Flux<CurrentAccount> findByCustomerDocumentNumber(String number);
  Mono<CurrentAccount> findByCardNumber(String number);

}
