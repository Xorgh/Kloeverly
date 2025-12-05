package kloeverly.presentation.controllers;

import javafx.fxml.Initializable;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidentsViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {

  }

  @Override public void setArgument(String argument)
  {

  }
}
