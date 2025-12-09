package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashboardUtil {
  public static void openDashboardWindow() {
    try {
      URL fxml = DashboardUtil.class.getResource("/fxml/DashboardView.fxml");
      if (fxml == null) {
        System.err.println("Cannot find FXML: /fxml/DashboardView.fxml on the classpath");
        return;
      }

      FXMLLoader loader = new FXMLLoader(fxml);
      Parent root = loader.load();

      ControllerConfigurator.configure(loader.getController());

      Stage stage = new Stage();
      stage.setTitle("Dashboard");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
