package daggerok;

import daggerok.events.AppEvent1;
import daggerok.events.AppEvent2;
import daggerok.framework.DomainEvents;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  public static void main(String[] args) {
    log.info("yo!");
    DomainEvents.subscribe(AppEvent1.class, event -> System.out.println("ololo event = " + event));
    DomainEvents.publish(AppEvent1.of("hoho"));
    DomainEvents.subscribe(AppEvent2.class, event -> System.out.println("trololo event = " + event));
    DomainEvents.publish(AppEvent1.of("ohoho"));
    DomainEvents.publish(AppEvent2.of("nonono", "lololo"));
  }
}
