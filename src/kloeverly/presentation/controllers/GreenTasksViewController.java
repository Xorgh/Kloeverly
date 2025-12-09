package kloeverly.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class GreenTasksViewController implements Initializable
{
  private DataManager dataManager;

  @FXML private TableView<GreenTask> greenTasksTable;
  @FXML private TableColumn<GreenTask, Integer> ID;
  @FXML private TableColumn<GreenTask, String> title;
  @FXML private TableColumn<GreenTask, String> description;
  @FXML private TableColumn<GreenTask, Integer> pointValue;
  @FXML private TableColumn<GreenTask, Resident> completedBy;
  @FXML private TableColumn<GreenTask, LocalDate> completedDate;

  @FXML private TextField titleTextField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TextField pointsTextField;
  @FXML private Button addNewGreenTaskButton;

  @FXML private ChoiceBox<Resident> residentChoiceBox;

  @FXML private Label greenPointsLabel;

  private final ObservableList<GreenTask> tableData = FXCollections.observableArrayList();
  private final ObservableList<Resident> residentData = FXCollections.observableArrayList();


  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<GreenTask, Integer>("ID"));
    title.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("title"));
    description.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("description"));
    pointValue.setCellValueFactory(new PropertyValueFactory<GreenTask, Integer>("pointValue"));
    completedBy.setCellValueFactory(new PropertyValueFactory<GreenTask, Resident>("completedBy"));
    completedDate.setCellValueFactory(new PropertyValueFactory<GreenTask, LocalDate>("completed"));

    greenTasksTable.setItems(tableData);

    residentChoiceBox.setItems(residentData);
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllGreenTasks());
    residentData.setAll(dataManager.getActiveResidents());
    updateGreenPointsLabel();
  }

  @FXML void handleTitleKeyReleased(KeyEvent event)
  {
    String text = titleTextField.getText();
  }
  @FXML void handleDescriptionKeyReleased(KeyEvent event)
  {
    String text = descriptionTextArea.getText();
  }

  @FXML void handlePointsKeyReleased(KeyEvent event)
  {
    String text = pointsTextField.getText();
  }

  @FXML void handleAddNewGreenTask(ActionEvent event)
  {
    String taskTitle = titleTextField.getText();
    String taskDescription = descriptionTextArea.getText();
    int taskPoints = Integer.parseInt(pointsTextField.getText());
    Resident selectedResident = residentChoiceBox.getValue();

    if (taskTitle == null ||
        taskTitle.isEmpty() ||
        taskDescription == null ||
        taskDescription.isEmpty() ||
        selectedResident == null)
    {
      return;
    }
    GreenTask newTask = new GreenTask(taskTitle, taskDescription, taskPoints, selectedResident);

    dataManager.addTask(newTask);

    newTask.completeTask(dataManager.getCommunity());

    dataManager.save();

    tableData.setAll(dataManager.getAllGreenTasks());
    greenTasksTable.refresh();
    updateGreenPointsLabel();

    titleTextField.clear();
    descriptionTextArea.clear();
    pointsTextField.clear();
    residentChoiceBox.getSelectionModel().clearSelection();
  }

  private void updateGreenPointsLabel()
  {
    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();
    greenPointsLabel.setText(Integer.toString(greenPoints));
  }
}
