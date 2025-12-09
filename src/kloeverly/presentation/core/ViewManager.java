package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager
{
  private static BorderPane mainLayout;
  private final static String fxmlDirectoryPath = "/fxml/";
  private static String currentView = null;

  public static void init(Stage primaryStage, String initialView) throws IOException
  {
    BorderPane root = FXMLLoader.load(ViewManager.class.getResource(fxmlDirectoryPath + initialView + ".fxml"));

    mainLayout = root;

    Scene scene = new Scene(root, 1920, 1080);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Kløverly");
    primaryStage.show();
  }

  public static void showView(String viewName)
  {
    try
    {
      currentView = viewName;
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ViewManager.class.getResource(fxmlDirectoryPath + viewName + ".fxml"));
      Parent root = loader.load();
      Object controller = loader.getController();
      ControllerConfigurator.configure(controller);
      mainLayout.setCenter(root);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Alert error = new Alert(Alert.AlertType.ERROR, "Cannot find view: " + viewName);
      error.show();
    }
  }

  public static void showView(String viewName, String argument)
  {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ViewManager.class.getResource(fxmlDirectoryPath + viewName + ".fxml"));
    try
    {
      Parent root = loader.load();
      AcceptsStringArgument controller = loader.getController();
      ControllerConfigurator.configure(controller);
      controller.setArgument(argument);
      mainLayout.setCenter(root);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Alert error = new Alert(Alert.AlertType.ERROR, "Cannot find view: " + viewName);
      error.show();
    }
  }

  public static void reloadCurrentView()
  {
    if (currentView != null)
    {
      showView(currentView);
    }
  }
}
