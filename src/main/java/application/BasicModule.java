package application;

import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import application.view.ControlBar;
import application.view.Graphics;
import application.view.button_services.ButtonService;
import application.view.button_services.ClearButtonService;
import application.view.button_services.RandomButtonService;
import application.view.button_services.StartButtonService;
import application.view.button_services.StopButtonService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class BasicModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ViewController.class).in(Scopes.SINGLETON);
    bind(ModelController.class).in(Scopes.SINGLETON);
    bind(Logic.class).in(Scopes.SINGLETON);
    bind(Field.class).in(Scopes.SINGLETON);
    bind(ControlBar.class).in(Scopes.SINGLETON);

    bind(Graphics.class).in(Scopes.SINGLETON);

    bind(ButtonService.class)
        .annotatedWith(Names.named("StartButtonAction"))
        .to(StartButtonService.class)
        .in(Scopes.SINGLETON);

    bind(ButtonService.class)
        .annotatedWith(Names.named("StopButtonAction"))
        .to(StopButtonService.class)
        .in(Scopes.SINGLETON);

    bind(ButtonService.class)
        .annotatedWith(Names.named("ClearButtonAction"))
        .to(ClearButtonService.class)
        .in(Scopes.SINGLETON);

    bind(ButtonService.class)
        .annotatedWith(Names.named("RandomButtonAction"))
        .to(RandomButtonService.class)
        .in(Scopes.SINGLETON);
  }
}
