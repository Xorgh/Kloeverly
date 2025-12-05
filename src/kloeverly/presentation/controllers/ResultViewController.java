package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultViewController implements Initializable, AcceptsStringArgument
{
  public Label resultOutputLabel;
  public Button backToMainViewButton;
  private DataManager dataManager;


  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {

  }

  public void handleBackToMainView(ActionEvent event)
  {
  }

  @Override public void setArgument(String argument)
  {
    resultOutputLabel.setText(argument);
  }
}
