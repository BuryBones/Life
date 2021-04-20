package application;

import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class BasicModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ViewController.class).in(Scopes.SINGLETON);
    bind(ModelController.class).in(Scopes.SINGLETON);
    bind(Logic.class).in(Scopes.SINGLETON);
    bind(Field.class).in(Scopes.SINGLETON);
  }
}
