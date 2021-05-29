package com.blockwit.bwf.repository;

import com.blockwit.bwf.model.bot.telegram.TelegramUserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserCredentialRepository  extends JpaRepository<TelegramUserCredential, Long> {
    Optional<TelegramUserCredential> findByPhoneNumber(String phoneNumber);
}
