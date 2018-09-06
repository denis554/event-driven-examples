package daggerok.app;

import daggerok.domainevents.DomainEvent;
import daggerok.domainevents.DomainEvents;

import javax.inject.Inject;
import java.util.function.Consumer;

public class MessageReceiver {

  private final DomainEvents domainEvents;

  @Inject
  public MessageReceiver(final DomainEvents domainEvents) {
    this.domainEvents = domainEvents;
  }

  public void subscribe(final Class<? extends DomainEvent> type, final Consumer<DomainEvent> handler) {
    domainEvents.register(type, handler);
  }
}
