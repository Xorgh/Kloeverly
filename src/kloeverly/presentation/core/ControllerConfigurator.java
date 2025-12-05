package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;
import kloeverly.persistence.FileDataManager;
import kloeverly.presentation.controllers.MainViewController;
import kloeverly.presentation.controllers.ResidentsViewController;

public class ControllerConfigurator
{
  private static DataManager INSTANCE;

  public static void configure(Object controller)
  {
    if (controller == null) return;
    switch (controller)
    {
      case MainViewController ctrl -> ctrl.init(getDataManager());
      case ResidentsViewController ctrl -> ctrl.init(getDataManager());
      default -> throw new RuntimeException("Controller of type '" + controller.getClass().getSimpleName() + "' not valid.");
    }
  }

  public static DataManager getDataManager()
  {
    if (INSTANCE == null)
    {
      String path = System.getProperty("user.home") + "/kloeverly.dat";
      INSTANCE = new FileDataManager(path, "kloeverly");
    }
    return INSTANCE;
  }
}
