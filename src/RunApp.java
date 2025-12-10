import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.presentation.core.ControllerConfigurator;
import kloeverly.presentation.core.ViewManager;

public class RunApp extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    ControllerConfigurator.getDataManager();

    ViewManager.init(primaryStage, "MainView");
    ViewManager.showView("DashboardView");
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
