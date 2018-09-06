package daggerok.app;

import daggerok.domainevents.DomainEvent;
import io.vavr.Lazy;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@ToString
public class MessageEvent implements DomainEvent {

  public final String body;
  private final Lazy<ZonedDateTime> lazy = Lazy.of(ZonedDateTime::now);

  private MessageEvent(String body) {
    this.body = body;
    lazy.get();
  }

  @Override
  public Serializable body() {
    return body;
  }

  @Override
  public ZonedDateTime when() {
    return lazy.get();
  }

  public static MessageEvent of(final String body) {
    return new MessageEvent(body);
  }
}
