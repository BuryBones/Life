package application.model;

import java.util.concurrent.ThreadFactory;

public class LoopFactory implements ThreadFactory {

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r);
    thread.setDaemon(true);
    return thread;
  }
}
