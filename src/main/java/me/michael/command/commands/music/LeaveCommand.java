package me.michael.command.commands.music;

import me.michael.command.CommandContext;
import me.michael.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {
  @Override
  public void handle(CommandContext ctx) {
    final GuildMessageReceivedEvent event = ctx.getEvent();
    TextChannel channel = ctx.getChannel();
    AudioManager audioManager = ctx.getGuild().getAudioManager();

    if (!audioManager.isConnected()) {
      channel.sendMessage("I am not connected to a voice channel").queue();
      return;
    }

    VoiceChannel voiceChannel = audioManager.getConnectedChannel();

    if (voiceChannel.getMembers().contains(event.getMember())) {
      channel.sendMessage("You have to be in the same channel as me to use this").queue();
      return;
    }

    audioManager.closeAudioConnection();
    channel.sendMessage("Disconnected from your voice channel").queue();
  }

  @Override
  public String getName() {
    return "leave";
  }

  @Override
  public String getHelp() {
    return "Makes the bot leave your voice channel";
  }
}
