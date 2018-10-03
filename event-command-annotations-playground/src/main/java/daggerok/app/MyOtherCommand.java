package daggerok.app;

import daggerok.framework.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class MyOtherCommand {

  @CommandHandler
  public void handle(final HashMap map) {
    log.info("map = {}", map);
  }
}
