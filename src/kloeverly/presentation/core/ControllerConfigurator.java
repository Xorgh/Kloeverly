package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;
import kloeverly.persistence.FileDataManager;
import kloeverly.presentation.controllers.*;

import java.nio.file.Path;
import java.nio.file.Paths;

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
      case GreenTasksViewController ctrl -> ctrl.init(getDataManager());
      case CommunityTasksViewController ctrl -> ctrl.init(getDataManager());
      case ExchangeTasksViewController ctrl -> ctrl.init(getDataManager());
      case CommunityEventsViewController ctrl -> ctrl.init(getDataManager());
      case DashboardViewController ctrl -> ctrl.init(getDataManager());
      default -> throw new RuntimeException("Controller of type '" + controller.getClass().getSimpleName() + "' not valid.");
    }
  }

  public static DataManager getDataManager()
  {
    if (INSTANCE == null)
    {
      Path path = Paths.get(System.getProperty("user.home"), "kloeverly.dat");
      INSTANCE = new FileDataManager(path.toString(), "kloeverly");
    }
    return INSTANCE;
  }
}
