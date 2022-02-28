package com.nttdata.currentaccount.service;

import com.nttdata.currentaccount.entity.CurrentAccount;
import com.nttdata.currentaccount.entity.dto.CreditCard;
import com.nttdata.currentaccount.entity.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICurrentAccountService {
  
  Mono<CurrentAccount> findById(String id);
  
  Flux<CurrentAccount> findAll();
  
  Mono<CurrentAccount> create(CurrentAccount t);
  
  Mono<CurrentAccount> update(CurrentAccount t);
  
  Mono<Boolean> delete(String t);
  
  Mono<Long> countCustomerAccountBank(String id);
  
  Flux<CurrentAccount>customerIdAccount (String id);
  
  Mono<Long> countCustomerAccountBankDocumentNumber(String number);
  
  Mono<Customer> findCustomerByDocumentNumber(String number);
  
  Mono<Customer> findCustomerById(String id);
  
  Flux<CreditCard> findCreditCardByCustomerId(String id);
  
  Mono<CurrentAccount> findByCardNumber(String number);

}
