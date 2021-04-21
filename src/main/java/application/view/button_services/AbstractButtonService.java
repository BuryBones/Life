package application.view.button_services;

import application.controller.ModelController;
import application.view.Graphics;
import com.google.inject.Inject;

public abstract class AbstractButtonService implements ButtonService {

  @Inject
  protected ModelController modelController;
  @Inject
  protected Graphics graphics;
}
