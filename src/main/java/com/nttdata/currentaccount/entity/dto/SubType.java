package com.nttdata.currentaccount.entity.dto;

import com.nttdata.currentaccount.entity.enums.ESubType;

import lombok.Data;

@Data
public class SubType {
  
  private String id;
  
  private ESubType value;

}
