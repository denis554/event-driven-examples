package daggerok;

import daggerok.app.MessageEvent;
import daggerok.app.MessageReceiver;
import daggerok.app.MessageSender;
import daggerok.context.DaggerokContext;
import daggerok.domainevents.DomainEvent;
import daggerok.domainevents.DomainEvents;
import daggerok.extensions.CaptureSystemOutput;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@CaptureSystemOutput
@DisplayName("Domain events test")
class AppJUnit5Test {

//    outputCapture.expect((containsString("'assert before'")));
//    log.info("see line 20: we are expecting this string: 'assert before'");
//
//    log.info("see next line 20: 'assert after'");
//    assertThat(outputCapture.toString(), containsString("'assert after'"));

  @Test
  @SneakyThrows
  @DisplayName("test event subscription")
  void test(final CaptureSystemOutput.OutputCapture outputCapture) {

    final DaggerokContext context = DaggerokContext.create(App.class).initialize();
    final MessageReceiver receiver = context.getBean(MessageReceiver.class);
    final MessageSender sender = context.getBean(MessageSender.class);

    assertThat(context.size()).isEqualTo(4);
    assertThat(context.getBean(DomainEvents.class)).isNotNull();
    assertThat(sender).isNotNull();
    assertThat(receiver).isNotNull();

    receiver.subscribe(MessageEvent.class, event -> {
      final Serializable body = event.body();
      log.info("received message: {}", body);
      final ZonedDateTime when = event.when();
      log.info("when: {}", when);
      assertThat(when).isBeforeOrEqualTo(ZonedDateTime.now());
      assertThat(String.class.cast(body)).containsIgnoringCase("hello");
    });

    sender.send("hello!");
    sender.send("ololo, trolololo hellolo!");
  }
}
