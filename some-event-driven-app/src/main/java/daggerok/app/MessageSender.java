package daggerok.app;

import daggerok.domainevents.DomainEvents;

import javax.inject.Inject;

public class MessageSender {

  private final DomainEvents domainEvents;

  @Inject
  public MessageSender(final DomainEvents domainEvents) {
    this.domainEvents = domainEvents;
  }

  public void send(final String message) {
    domainEvents.rise(MessageEvent.of(message));
  }
}
