package daggerok;

import daggerok.framework.command.CommandGateway;
import io.vavr.collection.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  public static void main(String[] args) {
    log.info("yo!");
    CommandGateway.handle("Hey");
    CommandGateway.handle("Ho!");
    CommandGateway.handle(LinkedHashMap.of("message", "Hello, World!").toJavaMap());
  }
}
