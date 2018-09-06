package daggerok.domainevents;

import java.io.Serializable;
import java.time.ZonedDateTime;

public interface DomainEvent {

  Serializable body();

  default String type() {
    return getClass().getName();
  }

  ZonedDateTime when();
}
