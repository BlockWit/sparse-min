package com.blockwit.bwf.model.msgs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {

  private MessageProviderType providerType;

  private Long date;

  private String from;

  private String to;

  private String text;

}
