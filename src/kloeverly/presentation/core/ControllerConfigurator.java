package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;
import kloeverly.persistence.ListDataManager;
import kloeverly.presentation.controllers.AddPlanetController;
import kloeverly.presentation.controllers.ResultViewController;

public class ControllerConfigurator
{
  private DataManager dataManager;

  public static void configure(Object controller)
  {
    if (controller == null) return;
    switch (controller)
    {
      case AddPlanetController ctrl -> ctrl.init(getDataManager());
      case ResultViewController ctrl -> ctrl.init(getDataManager());
      default -> throw new RuntimeException("Controller of type '" + controller.getClass().getSimpleName() + "' not valid.");
    }
  }

  public static DataManager getDataManager()
  {
    return new ListDataManager();
  }

}
