package me.michael.command.commands.music;

import me.michael.command.CommandContext;
import me.michael.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {
  @Override
  public void handle(CommandContext ctx) {

    final TextChannel channel = ctx.getChannel();
    final GuildMessageReceivedEvent event = ctx.getEvent();
    AudioManager audioManager = event.getGuild().getAudioManager();

    if (audioManager.isConnected()) {
      channel.sendMessage("I am already connected to a voice channel").queue();
      return;
    }

    GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

    if (!memberVoiceState.inVoiceChannel()) {
      channel.sendMessage("Please join a voice channel first").queue();
      return;
    }

    VoiceChannel voiceChannel = memberVoiceState.getChannel();
    Member selfMember = event.getGuild().getSelfMember();

    if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
      channel.sendMessageFormat("I am missing permission to join %s", voiceChannel).queue();
      return;
    }

    audioManager.openAudioConnection(voiceChannel);
    channel.sendMessage("Joining your voice channel").queue();
  }

  @Override
  public String getName() {
    return "join";
  }

  @Override
  public String getHelp() {
    return "Makes bot join  your voice channel";
  }
}
