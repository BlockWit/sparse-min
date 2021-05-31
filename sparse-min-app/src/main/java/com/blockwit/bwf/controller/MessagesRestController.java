package com.blockwit.bwf.controller;

import com.blockwit.bwf.bot.telegram.TelegramBot;
import com.blockwit.bwf.model.msgs.MessageDTO;
import com.blockwit.bwf.model.msgs.MessageParsedDataDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@Api("SMS processor for Android app")
@RequestMapping("/api/v1/msgs")
public class MessagesRestController {

  public static final String pattern = "^Пополнение\\.[\\p{Z}\\s]+Счет[\\p{Z}\\s]+.*?\\.[\\p{Z}\\s]+([\\d\\.]+)[\\p{Z}\\s]+(.*?)\\.[\\p{Z}\\s]+(.*?)\\.[\\p{Z}\\s]+Доступно[\\p{Z}\\s]+([\\d\\.]+)[\\p{Z}\\s]+(.*?)$";

  public static final Pattern compiledPattern = Pattern.compile(pattern);

  public static final String Tinkoff = "Tinkoff";

  @Autowired
  private TelegramBot telegramBot;

  @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageDTO> save(@RequestBody MessageDTO messageDTO) {
    log.info(messageDTO.toString());
    if (messageDTO.getFrom().equals(Tinkoff)) {
      log.info("Tinkoff detected ");
      Matcher matcher = compiledPattern.matcher(messageDTO.getText());
      if (matcher.matches()) {
        log.info("Tinkoff matches ");
        MessageParsedDataDTO messageParsedDataDTO = MessageParsedDataDTO.builder()
            .date(messageDTO.getDate())
            .amount(matcher.group(1))
            .balance(matcher.group(4))
            .originalMsg(messageDTO.getText())
            .build();
        log.info("Sending messages to tg bot");
        telegramBot.sendMessage(messageDTO.getTo(), messageParsedDataDTO);
      }
    }
    return ResponseEntity.ok(messageDTO);
  }

}
