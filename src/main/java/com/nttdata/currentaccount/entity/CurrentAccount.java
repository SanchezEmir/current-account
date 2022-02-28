package com.nttdata.currentaccount.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.currentaccount.entity.dto.Customer;
import com.nttdata.currentaccount.entity.dto.Managers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection =  "current_account")
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount {
  
  @Id
  private String id;
  
  @NotNull
  private Customer customer;
  
  @NotNull
  private String cardNumber;
  
  @NotNull
  private Integer freeTransactions;
  
  @NotNull
  private Double commissionTransactions;
  
  @NotNull
  private Double commissionMaintenance;
  
  private Double balance;
  
  private LocalDateTime createdAt;
  
  private List<Managers> owners;
  
  private List<Managers> signatories;

}
