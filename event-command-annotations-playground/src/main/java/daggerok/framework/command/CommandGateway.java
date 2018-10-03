package daggerok.framework.command;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandGateway {

  private static final MethodAnnotationsScanner scanner = new MethodAnnotationsScanner();
  private static final Reflections reflections = new Reflections("", scanner);
  private static final Set<Method> handlers = reflections
      .getMethodsAnnotatedWith(CommandHandler.class)
      .stream()
      .filter(method -> method.getParameters().length == 1)
      .collect(Collectors.toSet());

  public static void handle(final Object command) {
    for (final Method handler : handlers) {
      final Class<?> type = handler.getParameters()[0].getType();
      if (!type.isInstance(command)) continue;
      invoke(handler, command);
    }
  }

  @SneakyThrows
  private static void invoke(final Method handler, final Object command) {
    final Object instance = handler.getDeclaringClass().newInstance();
    handler.invoke(instance, command);
  }

  private CommandGateway() { }
}
