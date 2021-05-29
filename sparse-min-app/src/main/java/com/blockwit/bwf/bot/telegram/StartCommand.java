package com.blockwit.bwf.bot.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartCommand extends BotCommand {
  public StartCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }

  public static void sendAnswer(AbsSender absSender, Long chatId, String commandName,
                                String userName, String text) {
    SendMessage message = new SendMessage();
    message.enableMarkdown(true);
    message.setChatId(chatId.toString());
    message.setText(text);
    setButtons(message);
    try {
      absSender.execute(message);
    } catch (TelegramApiException e) {
    }
  }

  public static void setButtons(SendMessage sendMessage) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    sendMessage.setReplyMarkup(replyKeyboardMarkup);
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(true);
    List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
    KeyboardRow keyboardFirstRow = new KeyboardRow();
    KeyboardButton shareNumBtn = new KeyboardButton("Send phone number");
    shareNumBtn.setRequestContact(true);
    shareNumBtn.setRequestLocation(false);
    keyboardFirstRow.add(shareNumBtn);
    keyboard.add(keyboardFirstRow);
    replyKeyboardMarkup.setKeyboard(keyboard);
  }


  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    String userName = (user.getUserName() != null) ? user.getUserName() :
        String.format("%s %s", user.getLastName(), user.getFirstName());
    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        "Для продолжения работы необходимо кликнуть на кнопке sendPhone number");

  }
}