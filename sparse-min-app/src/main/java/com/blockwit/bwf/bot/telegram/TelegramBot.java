package com.blockwit.bwf.bot.telegram;

import com.blockwit.bwf.model.bot.telegram.TelegramUserCredential;
import com.blockwit.bwf.model.msgs.MessageParsedDataDTO;
import com.blockwit.bwf.service.TelegramUserCredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {
  private String botName;
  private String botToken;
  private ReplyKeyboardMarkup replyKeyboardMarkup;
  private KeyboardButton getNumberButton;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private TelegramUserCredentialService telegramUserCredentialService;

  public TelegramBot(String botName, String botToken) {
    this.botName = botName;
    this.botToken = botToken;
    boolean rv = register(new StartCommand("start", "Старт"));
    replyKeyboardMarkup = new ReplyKeyboardMarkup();
    getNumberButton = new KeyboardButton("Send Phone Number", true, false, null);
    log.info("Registration result rv =" + rv);
  }

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    Message msg = update.getMessage();
    Long chatId = msg.getChatId();
    String userName = msg.getFrom().getUserName();

    String phoneNumber = "";
    Contact contact = update.getMessage().getContact();
    if (contact != null) {
      phoneNumber = "+" + contact.getPhoneNumber();
      TelegramUserCredential credential = new TelegramUserCredential();
      credential.setChatNumber(chatId);
      credential.setPhoneNumber(phoneNumber);
      telegramUserCredentialService.save(credential);
    }


    SendMessage answer = new SendMessage();
    answer.setText("Echo " + phoneNumber);
    answer.setChatId(chatId.toString());
    try {
      execute(answer);
    } catch (TelegramApiException e) {

    }
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  public void sendMessage(Long chatId, String text) {
    SendMessage answer = new SendMessage();
    answer.setText(text);
    answer.setChatId(chatId.toString());
    try {
      execute(answer);
    } catch (TelegramApiException e) {

    }

  }

  public void sendMessage(String phoneNumber, MessageParsedDataDTO messageParsedDataDTO) {
    Optional<TelegramUserCredential> optCredential = telegramUserCredentialService.findByPhoneNumber(phoneNumber);
    if (optCredential.isPresent()) {
      TelegramUserCredential credential = optCredential.get();
      sendMessage(credential.getChatNumber(), messageParsedDataDTO.toString());
    }
  }

}