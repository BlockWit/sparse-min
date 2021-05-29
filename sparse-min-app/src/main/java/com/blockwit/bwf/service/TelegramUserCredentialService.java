package com.blockwit.bwf.service;

import com.blockwit.bwf.model.bot.telegram.TelegramUserCredential;
import com.blockwit.bwf.repository.TelegramUserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TelegramUserCredentialService {

  @Autowired
  TelegramUserCredentialRepository telegramUserCredentialRepository;

  @Transactional
  public TelegramUserCredential save(TelegramUserCredential telegramUserCredential) {
    return telegramUserCredentialRepository.save(telegramUserCredential);
  }

  public Optional<TelegramUserCredential> findByPhoneNumber(String phoneNumber) {
    return telegramUserCredentialRepository.findByPhoneNumber(phoneNumber);
  }

}
