package daggerok;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class App {

  public static class FooPanel1 extends JPanel implements ActionListener {
    public FooPanel1() {
      super();
      final JButton button = new JButton("click me!");
      button.addActionListener(this);
      this.add(button);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
      log.info("button has been clicked.");
    }
  }

  public static class FooPanel2 extends JPanel {
    public FooPanel2() {
      super();
      final JButton button = new JButton("click me!");
      button.addActionListener(e -> {
        log.info("button has been clicked!");
      });
      this.add(button);
    }
  }

  public static void main(String[] args) {
    log.info("yo!");
  }
}
