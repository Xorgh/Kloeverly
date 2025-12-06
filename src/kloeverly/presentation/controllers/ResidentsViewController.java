package kloeverly.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResidentsViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  @FXML
  private TableView<Resident> residentsTable;
  @FXML
  private TableColumn<Resident, Integer> ID;

  @FXML
  private TableColumn<Resident, String> name;

  @FXML
  private TableColumn<Resident, Integer> personalPointBalance;

  @FXML
  private TableColumn<Resident, Integer> reservedBalance;

  @FXML
  private TableColumn<Resident, Boolean> pointBoosted;

  @FXML private TextField nameTextField;

  private final ObservableList<Resident> tableData = FXCollections.observableArrayList();

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    List<Resident> residents = dataManager.getAllResidents();
    tableData.setAll(residents);
    if (residentsTable != null)
    {
      residentsTable.setItems(tableData);
    }
  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("ID"));
    name.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
    personalPointBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("personalPointBalance"));
    reservedBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("reservedBalance"));
    pointBoosted.setCellValueFactory(new PropertyValueFactory<Resident, Boolean>("pointBoosted"));

    residentsTable.setItems(tableData);

    if (dataManager != null)
    {
      tableData.setAll(dataManager.getAllResidents());
    }
  }

  @Override public void setArgument(String argument)
  {

  }

  @FXML
  public void handleNameKeyReleased(KeyEvent event)
  {
    String text = nameTextField.getText();
  }

  public void handleAddNewResident(ActionEvent event)
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
}
