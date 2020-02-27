package me.michael;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

  private Bot() throws LoginException {
    WebUtils.setUserAgent("Bard bot/Drizmi (Basam)#1191");
    EmbedUtils.setEmbedBuilder(
            () -> new EmbedBuilder()
            .setColor(0xdaa520)
            .setFooter("Bard")
    );

    new JDABuilder()
            .setToken(Config.get("token"))
            .addEventListeners(new Listener())
            .setActivity(Activity.watching("!help"))
            .build();
  }

  public static void main(String[] args) throws LoginException {
    new Bot();
  }
}
