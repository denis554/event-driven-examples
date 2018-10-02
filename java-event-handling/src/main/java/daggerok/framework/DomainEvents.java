package daggerok.framework;

import java.util.Collection;
import java.util.EventListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class DomainEvents {

  private static final Map<Class, Collection<DomainEventHandler>> pubSub = new ConcurrentHashMap<>();

  public static <E> void subscribe(final Class<E> type, final DomainEventHandler<E> handler) {
    Objects.requireNonNull(type, "domain.event.type.null");
    Objects.requireNonNull(handler, "domain.event.handler.null");
    pubSub.putIfAbsent(type, new CopyOnWriteArrayList<>());
    pubSub.computeIfPresent(
        type, (eventType, handlers) -> new CopyOnWriteArrayList<>(
            Stream.concat(handlers.stream(), Stream.of(handler))
                  .collect(toList())
        )
    );
  }

  public static <E> void publish(final E event) {
    Objects.requireNonNull(event, "domain.event.null");
    pubSub.entrySet()
          .parallelStream()
          .filter(e -> e.getKey().isInstance(event))
          .map(Map.Entry::getValue)
          .flatMap(Collection::stream)
          .forEach(handler -> handler.handle(event));
  }

  public static <E> void unsubscribe(final Class<E> type, final DomainEventHandler<E> handler) {
    Objects.requireNonNull(type, "domain.event.type.null");
    Objects.requireNonNull(handler, "domain.event.handler.null");
    pubSub.getOrDefault(type, new CopyOnWriteArrayList<>())
          .remove(handler);
  }

  public static <E> void unregister(final Class<E> type) {
    Objects.requireNonNull(type, "domain.event.type.null");
    pubSub.put(type, new CopyOnWriteArrayList<>());
  }

  @FunctionalInterface
  public interface DomainEventHandler<E> extends EventListener {
    void handle(final E event);
  }

  private DomainEvents() { }
}
