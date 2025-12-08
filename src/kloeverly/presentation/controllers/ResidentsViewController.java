package kloeverly.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.CommunityEvent;
import kloeverly.domain.Resident;
import kloeverly.domain.ResidentStatus;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResidentsViewController implements Initializable, AcceptsStringArgument
{
  ;
  private DataManager dataManager;

  @FXML private TableView<Resident> residentsTable;

  @FXML private TableColumn<Resident, Integer> ID;
  @FXML private TableColumn<Resident, String> name;
  @FXML private TableColumn<Resident, Integer> personalPointBalance;
  @FXML private TableColumn<Resident, Integer> reservedBalance;
  @FXML private TableColumn<Resident, Boolean> pointBoosted;
  @FXML private TableColumn<Resident, ResidentStatus> status;

  @FXML private TextField nameTextField;
  @FXML private Button addNewResidentButton;

  @FXML private Button deactivateResidentButton;

  @FXML private Button togglePointBoostButton;

  @FXML private TextField editNameTextField;
  @FXML private Button editNameButton;

  @FXML private TextField editBalanceTextField;
  @FXML private Button editBalanceButton;

  private final ObservableList<Resident> tableData = FXCollections.observableArrayList();

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("ID"));
    name.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
    personalPointBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("personalPointBalance"));
    reservedBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("reservedBalance"));
    pointBoosted.setCellValueFactory(new PropertyValueFactory<Resident, Boolean>("pointBoosted"));
    status.setCellValueFactory(new PropertyValueFactory<Resident, ResidentStatus>("status"));

    residentsTable.setItems(tableData);

    residentsTable.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> updateControls());
    nameTextField.textProperty().addListener((obs, oldText, newText) -> updateControls());
    editNameTextField.textProperty().addListener((obs, oldText, newText) -> updateControls());
    editBalanceTextField.textProperty().addListener((obs, oldText, newText) -> updateControls());
    updateControls();

    if (dataManager != null)
    {
      tableData.setAll(dataManager.getAllResidents());
    }
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllResidents());
    if (residentsTable != null)
    {
      residentsTable.setItems(tableData);
    }
    refreshView();
  }

  @FXML public void handleNameKeyReleased(KeyEvent event)
  {
    String text = nameTextField.getText();
  }

  @FXML public void handleEditNameKeyReleased(KeyEvent event)
  {
    String text = nameTextField.getText();
  }

  @FXML public void handleEditBalanceKeyReleased(KeyEvent event)
  {
    String text = nameTextField.getText();
  }

  @FXML public void handleAddNewResident(ActionEvent event)
  {
    if (nameTextField.getText() == null || nameTextField.getText().isEmpty())
    {
      return;
    }

    dataManager.addResident(new Resident(nameTextField.getText()));

    tableData.setAll(dataManager.getAllResidents());
    residentsTable.refresh();

    nameTextField.clear();
  }

  public void handleDeactivateResident(ActionEvent event)
  {
    Resident selectedResident = getSelectedResident();
    if (selectedResident == null)
    {
      System.out.println("No resident selected, cannot deactivate.");
      return;
    }
    dataManager.getCommunity().removeResident(selectedResident);
    dataManager.save();
    refreshView();
  }

  public void handleTogglePointBoost(ActionEvent event)
  {
    Resident selectedResident = getSelectedResident();
    if (selectedResident == null)
    {
      System.out.println("No resident selected, cannot toggle boost.");
      return;
    }
    selectedResident.setPointBoosted(!selectedResident.isPointBoosted());
    dataManager.save();
    refreshView();
  }

  public void handleEditName(ActionEvent event)
  {
    Resident selectedResident = getSelectedResident();
    String newName = editNameTextField.getText();
    if (selectedResident == null)
    {
      System.out.println("No resident selected, cannot edit name.");
      return;
    }
    selectedResident.setName(newName);
    dataManager.save();
    refreshView();
  }

  public void handleEditBalance(ActionEvent event)
  {
    Resident selectedResident = getSelectedResident();
    int newBalance;
    if (selectedResident == null)
    {
      System.out.println("No resident selected, cannot edit balance.");
      return;
    }
    try
    {
      newBalance = Integer.parseInt(editBalanceTextField.getText());
    }
    catch (NumberFormatException e)
    {
      System.out.println("Invalid balance input, must be an integer.");
      return;
    }
    if (newBalance < 0)
    {
      System.out.println("Balance cannot be negative.");
      return;
    }
    else
    {
      selectedResident.setPersonalPointBalance(newBalance);
      dataManager.save();
      refreshView();
    }
  }

  private Resident getSelectedResident()
  {
    Resident residentSelected = residentsTable.getSelectionModel().getSelectedItem();
    if (residentSelected == null)
    {
      System.out.println("No resident selected.");
      //      Throw error??
      return null;
    }
    return residentSelected;
  }

  private void refreshView()
  {
    if (dataManager == null)
      return;

    tableData.setAll(dataManager.getAllResidents());
    residentsTable.refresh();

    updateControls();
  }

  private void updateControls()
  {
    if (dataManager == null)
    {
      if (addNewResidentButton != null)
        addNewResidentButton.setDisable(true);
      if (deactivateResidentButton != null)
        deactivateResidentButton.setDisable(true);
      if (togglePointBoostButton != null)
        togglePointBoostButton.setDisable(true);
      if (editNameButton != null)
        editNameButton.setDisable(true);
      if (editBalanceButton != null)
        editBalanceButton.setDisable(true);
      return;
    }

    Resident selectedResident = getSelectedResident();

    boolean residentIsSelected = selectedResident != null;
    boolean isActive = residentIsSelected && selectedResident.getStatus() == ResidentStatus.ACTIVE;
    boolean isBoosted = isActive && selectedResident.isPointBoosted();

    deactivateResidentButton.setDisable(!isActive);

    togglePointBoostButton.setDisable(!residentIsSelected);

    boolean canEditName =
        residentIsSelected && editNameTextField.getText() != null && !editNameTextField.getText().isEmpty();
    editNameButton.setDisable(!canEditName);

    boolean canEditBalance =
        residentIsSelected && editBalanceTextField.getText() != null && !editBalanceTextField.getText().isEmpty();
    editBalanceButton.setDisable(!canEditBalance);
  }

  @Override public void setArgument(String argument)
  {

  }
}
