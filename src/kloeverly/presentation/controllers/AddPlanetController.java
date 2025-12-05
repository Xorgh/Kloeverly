package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.ViewManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPlanetController implements Initializable
{
  public TextField nameInput;
  public TextField climateDescriptionInput;
  public TextField distanceInput;
  public CheckBox hasAtmosphereCheckBox;
  public CheckBox hasLifeCheckBox;
  public Button addPlanetButton;
  public Label statusOutputLabel;

  private DataManager dataManager;


  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
  }



  @Override public void initialize(URL location, ResourceBundle resources)
  {
    
  }

  public void onNameInputKeyReleased()
  {
    String text = nameInput.getText();
    boolean shouldDisable = text == null || text.isEmpty();
    addPlanetButton.setDisable(shouldDisable);
  }

  public void onDescriptionInputKeyReleased()
  {
    String text = climateDescriptionInput.getText();
    boolean shouldDisable = text == null || text.isEmpty();
    addPlanetButton.setDisable(shouldDisable);
  }

  public void onDistanceInputKeyReleased()
  {
    String text = distanceInput.getText();
    boolean shouldDisable = text == null || text.isEmpty();
    addPlanetButton.setDisable(shouldDisable);
  }

  public void handleHasAtmosphereCheckBox(ActionEvent event)
  {
    addPlanetButton.setDisable(hasAtmosphereCheckBox.isIndeterminate());
  }

  public void handleHasLifeCheckBox(ActionEvent event)
  {
    addPlanetButton.setDisable(hasLifeCheckBox.isIndeterminate());
  }

  public void updateStatusOutputLabel (String text)
  {
    statusOutputLabel.setText(text);
  }
}
