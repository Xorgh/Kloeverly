import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.domain.Community;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.persistence.FileDataManager;
import kloeverly.presentation.core.ControllerConfigurator;
import kloeverly.presentation.core.ViewManager;

import java.nio.file.Paths;

public class RunApp extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    ControllerConfigurator.getDataManager();

    ViewManager.init(primaryStage, "MainView");
    ViewManager.showView("HomeView");
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
