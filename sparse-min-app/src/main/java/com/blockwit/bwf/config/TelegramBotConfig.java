package com.blockwit.bwf.config;


import com.blockwit.bwf.bot.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

  @Value("${bwf.bot.name}")
  private String botName;

  @Value("${bwf.bot.token}")
  private String botToken;

  @Bean
  public TelegramBot telegramBotCreate() {
    TelegramBot aBot = null;
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      aBot = new TelegramBot(botName, botToken);
      BotSession bs = botsApi.registerBot(aBot);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    return aBot;
  }

}
