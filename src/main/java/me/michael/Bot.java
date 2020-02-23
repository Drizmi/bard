package me.michael;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {
  public static JDA jda;

  public static void main(String[] args) throws LoginException {
    jda = new JDABuilder(AccountType.BOT).setToken("131WgstioQaUTAdAu-UJ9BVI-8mBAtch").build();
  }
}
