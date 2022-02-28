package com.nttdata.currentaccount.entity.dto;

import com.nttdata.currentaccount.entity.enums.ETypeCustomer;

import lombok.Data;

@Data
public class TypeCustomer {
  
  private String id;
  
  private ETypeCustomer value;
  
  private SubType subType;

}
