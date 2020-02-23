package me.michael;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

  private Bot() throws LoginException {

    new JDABuilder()
            .setToken("NjQ3NzI5MzU2MDM0MDE1MjMy.XlMBYQ.4e662ZkRZOi1nyRE5ZTc1mG7100")
            .addEventListeners(new Listener())
            .setActivity(Activity.watching("!help"))
            .build();
  }

  public static void main(String[] args) throws LoginException {
    new Bot();
  }
}
