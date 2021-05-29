package com.blockwit.bwf.model.msgs;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageParsedDataDTO implements Serializable {

  Long date;

  String amount;

  String balance;

  String originalMsg;

}
