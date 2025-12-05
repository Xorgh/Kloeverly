import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.domain.Community;
import kloeverly.domain.Resident;
import kloeverly.presentation.core.ViewManager;

public class RunApp extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    Community community = new Community("kloeverly");

    // Test
    Resident resident1 = new Resident("Bob");
    Resident resident2 = new Resident("Bobby");
    Resident resident3 = new Resident("Bobbi");

    community.addResident(resident1);
    community.addResident(resident2);
    community.addResident(resident3);

    System.out.println(community.getResidents());
    System.out.println(resident1);

    ViewManager.init(primaryStage, "MainView");
    ViewManager.showView("HomeView");
  }

  public static void main(String[] args)
  {
    launch(args);

  }
}
