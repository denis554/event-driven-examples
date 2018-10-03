package daggerok.app;

import daggerok.framework.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyAnyHandlingCommand {

  @CommandHandler
  public void handleString(final Object string) {
    log.info("any handler = {}", string);
  }
}
