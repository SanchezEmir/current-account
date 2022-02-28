package com.nttdata.currentaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.currentaccount.entity.CurrentAccount;
import com.nttdata.currentaccount.entity.dto.CreditCard;
import com.nttdata.currentaccount.entity.dto.Customer;
import com.nttdata.currentaccount.repository.ICurrentAccountRepository;
import com.nttdata.currentaccount.service.ICurrentAccountService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CurrentAccountServiceImpl implements ICurrentAccountService{
  
  private final WebClient webClient;
  private final ReactiveCircuitBreaker reactiveCircuitBreaker;

  @Value("${config.base.apigateway}")
  private String url;

  public CurrentAccountServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
      this.webClient = WebClient.builder().baseUrl(this.url).build();
      this.reactiveCircuitBreaker = circuitBreakerFactory.create("customerCredit");
  }

  @Autowired
  ICurrentAccountRepository repo;

  @Override
  public Mono<CurrentAccount> findById(String id) {
    return repo.findById(id);
  }

  @Override
  public Flux<CurrentAccount> findAll() {
    return repo.findAll();
  }

  @Override
  public Mono<CurrentAccount> create(CurrentAccount t) {
    return repo.save(t);
  }

  @Override
  public Mono<CurrentAccount> update(CurrentAccount t) {
    return repo.save(t);
  }

  @Override
  public Mono<Boolean> delete(String t) {
    return repo.findById(t)
        .flatMap(ca -> repo.delete(ca).then(Mono.just(Boolean.TRUE)))
        .defaultIfEmpty(Boolean.FALSE);
  }

  @Override
  public Mono<Long> countCustomerAccountBank(String id) {
    return repo.findByCustomerId(id).count();
  }

  @Override
  public Flux<CurrentAccount> customerIdAccount(String id) {
    return repo.findByCustomerId(id);
  }

  @Override
  public Mono<Long> countCustomerAccountBankDocumentNumber(String number) {
    return repo.findByCustomerDocumentNumber(number).count();
  }

  @Override
  public Mono<Customer> findCustomerByDocumentNumber(String number) {
    log.info("buscando cliente");
    return reactiveCircuitBreaker.run(webClient.get()
        .uri(this.url + "/customer/documentNumber/{number}",number)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Customer.class),
        throwable -> {
          return this.getDefaultCustomer();
        });
  }

  @Override
  public Mono<Customer> findCustomerById(String id) {
    return reactiveCircuitBreaker.run(webClient.get()
        .uri(this.url + "/customer/find/{id}",id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Customer.class),
        throwable -> { 
          return this.getDefaultCustomer();
        });
  }

  @Override
  public Flux<CreditCard> findCreditCardByCustomerId(String id) {
    return reactiveCircuitBreaker.run(webClient.get()
        .uri(this.url + "/creditcard/creditcard/find/{id}",id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(CreditCard.class),
        throwable -> {
            return this.getDefaultCreditCard();
        });
  }

  @Override
  public Mono<CurrentAccount> findByCardNumber(String number) {
    return repo.findByCardNumber(number);
  }
  
  public Mono<Customer> getDefaultCustomer() {
    Mono<Customer> customer = Mono.just(new Customer());
    return customer;
  }
  
  public Flux<CreditCard> getDefaultCreditCard() {
    Flux<CreditCard> creditCard = Flux.just(new CreditCard("0", null, null,null,null,null));
    return creditCard;
  }

}
