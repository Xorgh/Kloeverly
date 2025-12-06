import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.domain.Community;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.persistence.FileDataManager;
import kloeverly.presentation.core.ViewManager;

public class RunApp extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
//    Community community = new Community("kloeverly");

    String path = System.getProperty("user.home") + "/kloeverly.dat";
    DataManager dataManager = new FileDataManager(path, "kloeverly");

    // Test
//    Resident resident1 = new Resident("Bob");
//    Resident resident2 = new Resident("Bobby");
//    Resident resident3 = new Resident("Bobbi");
//
//    dataManager.addResident(resident1);
//    dataManager.addResident(resident2);
//    dataManager.addResident(resident3);

    System.out.println(dataManager.getAllResidents());
//    System.out.println(resident1);

    ViewManager.init(primaryStage, "MainView");
    ViewManager.showView("HomeView");
  }

  public static void main(String[] args)
  {
    launch(args);

  }
}
