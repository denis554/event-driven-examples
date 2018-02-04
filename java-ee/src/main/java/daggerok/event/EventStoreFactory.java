package daggerok.event;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
public class EventStoreFactory {

  final ConcurrentHashMap<UUID, MyEvent> events = new ConcurrentHashMap<>();

  @Produces
  @EventStore
  public Map<UUID, MyEvent> eventStore() {
    log.info("producing eventStore");
    return events;
  }
}
