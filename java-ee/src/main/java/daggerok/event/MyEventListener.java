package daggerok.event;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Stateless
public class MyEventListener {

  @Inject @EventStore Map<UUID, MyEvent> eventStore;

  public void listen(@Observes final MyEvent event) {
    eventStore.put(event.id, event);
    log.info("'{}' saved.", event);
  }
}
