package com.blockwit.bwf.model.bot.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "telegram_user_credential")
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUserCredential {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  @Column(nullable = false, unique = true)
  private Long chatNumber;


  @Column(nullable = false, unique = true)
  private String phoneNumber;
}
