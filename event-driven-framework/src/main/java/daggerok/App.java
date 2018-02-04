package daggerok;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/* Framework API */

interface Message {
  default Class<? extends Message> getType() {
    return getClass();
  }
}

interface Channel<E extends Message> {
  void dispatch(final E message);
}

interface DynamicRouter<E extends Message> {

  void registerChannel(final Class<E> type,
                       final Channel<E> channel);

  void dispatch(final E message);
}

/* Implementations */

@Getter
@ToString
@RequiredArgsConstructor
class Event implements Message {
  private final String body;
}

@Slf4j
class EventHandler implements Channel<Event> {

  @Override
  public void dispatch(final Event message) {
    log.info("received: {}", message);
  }
}

class EventDispatcher implements DynamicRouter<Event> {

  private final Map<Class<? extends Event>, EventHandler> handlers = new HashMap<>();

  @Override
  public void registerChannel(final Class<Event> type,
                              final Channel<Event> channel) {

    handlers.put(type, EventHandler.class.cast(channel));
  }

  @Override
  public void dispatch(final Event message) {
    handlers.get(message.getType()).dispatch(message);
  }
}

/* Usage */

public class App {
  public static void main(String[] args) {
    final EventDispatcher dispatcher = new EventDispatcher();
    dispatcher.registerChannel(Event.class, new EventHandler());
    dispatcher.dispatch(new Event("hey!"));
    dispatcher.dispatch(new Event("ho!"));
  }
}
