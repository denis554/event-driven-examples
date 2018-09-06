package daggerok;

import daggerok.context.DaggerokContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  public static void main(String[] args) {
    DaggerokContext.create(App.class)
                   .initialize();
  }
}
