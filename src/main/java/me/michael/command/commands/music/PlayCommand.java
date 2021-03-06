package me.michael.command.commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import me.michael.Config;
import me.michael.command.CommandContext;
import me.michael.command.ICommand;
import me.michael.music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements ICommand {
  private final YouTube youTube;

  public PlayCommand() {
    YouTube temp = null;

    try {
      temp = new YouTube.Builder(
              GoogleNetHttpTransport.newTrustedTransport(),
              JacksonFactory.getDefaultInstance(),
              null
      )
              .setApplicationName("Bard bot")
              .build();
    } catch (Exception e) {
      e.printStackTrace();
    }

    youTube = temp;
  }

  @Override
  public void handle(CommandContext ctx) {
    final TextChannel channel = ctx.getChannel();
    final List<String> args = ctx.getArgs();

    if (args.isEmpty()) {
      channel.sendMessage("Please provide some arguments").queue();
      return;
    }

    String input = String.join(" ", args);

    if (!isUrl(input)) {
      String ytSearched = searchYoutube(input);

      if (ytSearched == null) {
        channel.sendMessage("Youtube returned no results").queue();

        return;
      }

      input = ytSearched;
    }

    PlayerManager playerManager = PlayerManager.getInstance();

    playerManager.loadAndPlay(channel, input);

    playerManager.getGuildMusicManager(ctx.getGuild()).player.setVolume(10);
  }

  private boolean isUrl(String input) {
    try {
      new URL(input);
      return true;
    } catch (MalformedURLException ignored) {
      return false;
    }
  }

  @Nullable
  private String searchYoutube(String input) {
    try {
      List<SearchResult> results = youTube.search()
              .list("id,snippet")
              .setQ(input)
              .setMaxResults(1L)
              .setType("video")
              .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
              .setKey(Config.get("youtubekey"))
              .execute()
              .getItems();

      if (!results.isEmpty()) {
        String videoId = results.get(0).getId().getVideoId();

        return "https://www.youtube.com/watch?v=" + videoId;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getHelp() {
    return "Plays a song\n" +
            "Usage: `!bard play <track url>`";
  }
}
