package daggerok.app;

import daggerok.framework.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCommand {

  @CommandHandler
  public void handleString(final String string) {
    log.info("string = {}", string);
  }
}
