package daggerok;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Handler;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "of")
class Event {

  private final char type;
  private final String data;
}

@Slf4j
class EventHandler {

  public static void handleH(final Event event) {
    log.info("human says: {}", event.getData());
  }

  public static void handleS(final Event event) {
    log.info("santa says: {}", event.getData());
  }
}

public class App {
  public static void main(String[] args) {
    final Queue<Event> events = new ArrayDeque<>();
    events.add(Event.of('H', "hi!"));
    events.add(Event.of('S', "ho-ho-ho"));
    events.add(Event.of('H', "hey!"));

    // event-loop
    while (!events.isEmpty()) {

      final Event event = events.remove();

      if (event.getType() == 'H')
        EventHandler.handleH(event);

      if (event.getType() == 'S')
        EventHandler.handleS(event);
    }
  }
}
