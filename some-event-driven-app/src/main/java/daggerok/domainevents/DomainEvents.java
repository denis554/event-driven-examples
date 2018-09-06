package daggerok.domainevents;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Slf4j
@Singleton
public class DomainEvents {

  private final Set<Consumer<DomainEvent>> empty = new HashSet<>();
  private final Map<String, Set<Consumer<DomainEvent>>> subscriptions = new ConcurrentHashMap<>();

//public void register(final String eventType, final Consumer<DomainEvent> handler) {
//  final Consumer<DomainEvent>[] handlers = singletonList(handler).toArray(new Consumer[0]);
//  register(eventType, handlers);
//}

  public void register(final Class<? extends DomainEvent> type, final Consumer<DomainEvent>... handlers) {

    Objects.requireNonNull(type, "type may not be null");
    Objects.requireNonNull(handlers, "handlers may not be null");

    final String key = type.getName();
    final Set<Consumer<DomainEvent>> currentHandlers = subscriptions.getOrDefault(key, empty);
    final Set<Consumer<DomainEvent>> newHandlers = new HashSet<>(asList(handlers));

    subscriptions.put(key, Stream.of(currentHandlers, newHandlers)
                                 .map(Set::parallelStream)
                                 .reduce(Stream::concat)
                                 .orElse(Stream.empty())
                                 .collect(Collectors.toSet()));
  }

  public void unregister(final Consumer<DomainEvent>... handlers) {

    Objects.requireNonNull(handlers, "handlers may not be null");

    final List<Consumer<DomainEvent>> unregisters = asList(handlers);

    subscriptions.keySet()
                 .forEach(type -> subscriptions.put(type,
                                                    subscriptions.get(type)
                                                                 .parallelStream()
                                                                 .filter(handler -> !unregisters.contains(handler))
                                                                 .collect(Collectors.toSet())));
  }

  public <E extends DomainEvent> void rise(final E event) {

    Objects.requireNonNull(event, "event may not be null");

    final String type = event.type();
    final Set<Consumer<DomainEvent>> handlers = subscriptions.getOrDefault(type, empty);

    subscriptions.put(type, handlers.parallelStream()
                                    .filter(handler -> Try.run(() -> handler.accept(event))
                                                          .onFailure(e -> log.error(e.getLocalizedMessage(), e))
                                                          .isSuccess())
                                    .collect(Collectors.toSet()));
  }

  public <E extends DomainEvent> void reset(final E event) {
    Objects.requireNonNull(event, "event may not be null");
    subscriptions.put(event.type(), empty);
  }

  public void reset() {
    subscriptions.clear();
  }
}
