package kloeverly.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CommunityTasksViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  @FXML private TableView<CommunityTask> communityTasksTable;

  @FXML private TableColumn<CommunityTask, Integer> ID;
  @FXML private TableColumn<CommunityTask, String> title;
  @FXML private TableColumn<CommunityTask, String> description;
  @FXML private TableColumn<CommunityTask, Integer> pointValue;
  @FXML private TableColumn<CommunityTask, TaskStatus> status;
  @FXML private TableColumn<CommunityTask, LocalDate> created;
  @FXML private TableColumn<CommunityTask, Resident> assignedTo;
  @FXML private TableColumn<CommunityTask, LocalDate> completed;
  @FXML private TableColumn<CommunityTask, Resident> completedBy;

  @FXML private ChoiceBox<TaskTemplate> TaskCatalogueChoiceBox;
  @FXML private Button addTaskButton;

  @FXML private TextField titleTextField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TextField pointsTextField;
  @FXML private Button addTaskTemplateButton;

  @FXML private ChoiceBox<TaskTemplate> TaskTemplateDeleteChoiceBox;
  @FXML private Button deleteTaskTemplateButton;

  @FXML private ChoiceBox<Resident> ResidentChoiceBox;
  @FXML private Button assignResident;

  @FXML private Button completeTaskButton;

  @FXML private Button deleteTaskButton;

  private final ObservableList<CommunityTask> tableData = FXCollections.observableArrayList();
  private final ObservableList<Resident> residentData = FXCollections.observableArrayList();
  private final ObservableList<TaskTemplate> taskTemplateData = FXCollections.observableArrayList();

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    title.setCellValueFactory(new PropertyValueFactory<>("title"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    pointValue.setCellValueFactory(new PropertyValueFactory<>("pointValue"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    created.setCellValueFactory(new PropertyValueFactory<>("created"));
    assignedTo.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
    completed.setCellValueFactory(new PropertyValueFactory<>("completed"));
    completedBy.setCellValueFactory(new PropertyValueFactory<>("completedBy"));

    communityTasksTable.setItems(tableData);
    ResidentChoiceBox.setItems(residentData);
    TaskCatalogueChoiceBox.setItems(taskTemplateData);
    TaskTemplateDeleteChoiceBox.setItems(taskTemplateData);
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllCommunityTasks());
    residentData.setAll(dataManager.getAllResidents());
    taskTemplateData.setAll(dataManager.getAllTaskTemplates());
  }

  @FXML void handleAddTaskFromCatalogue(ActionEvent event)
  {
    TaskTemplate selectedTemplate = TaskCatalogueChoiceBox.getValue();

    if (selectedTemplate == null)
    {
      return;
    }
    CommunityTask newTask = new CommunityTask(selectedTemplate);

    dataManager.addTask(newTask);
    dataManager.save();

    refreshView();
  }

  @FXML void handleTitleKeyReleased(KeyEvent event)
  {
    String text = titleTextField.getText();
  }

  @FXML void handleDescriptionKeyReleased(KeyEvent event)
  {
    String text = titleTextField.getText();
  }

  @FXML void handlePointsKeyReleased(KeyEvent event)
  {
    String text = titleTextField.getText();
  }

  @FXML void handleAddTaskTemplate(ActionEvent event)
  {
    String templateTitle = titleTextField.getText();
    String templateDescription = descriptionTextArea.getText();
    int templatePoints = Integer.parseInt(pointsTextField.getText());

    if (templateTitle == null || templateTitle.isEmpty() || templateDescription == null || templateDescription.isEmpty()
        || templatePoints <= 0)
    {
      return;
    }
    TaskTemplate newTemplate = new TaskTemplate(templateTitle, templateDescription, templatePoints);

    dataManager.addTaskTemplate(newTemplate);

    dataManager.save();

    taskTemplateData.setAll(dataManager.getAllTaskTemplates());

    titleTextField.clear();
    descriptionTextArea.clear();
    pointsTextField.clear();
  }

  @FXML void handleAssignResidentToTask(ActionEvent event)
  {

  }

  @FXML void handleCompleteTask(ActionEvent event)
  {

  }

  @Override public void setArgument(String argument)
  {

  }

  @FXML void handleCancelTask(ActionEvent event)
  {

  }

  @FXML void handleDeleteTaskTemplate(ActionEvent event)
  {
    TaskTemplate selectedTemplate = TaskTemplateDeleteChoiceBox.getValue();
    if (selectedTemplate == null)
    {
      return;
    }
    dataManager.getCommunity().removeTaskTemplate(selectedTemplate);
  }

  private void refreshView()
  {
    tableData.setAll(dataManager.getAllCommunityTasks());
    communityTasksTable.refresh();

    residentData.setAll(dataManager.getAllResidents());
    taskTemplateData.setAll(dataManager.getAllTaskTemplates());

    ResidentChoiceBox.setItems(residentData);
    TaskCatalogueChoiceBox.setItems(taskTemplateData);
    TaskTemplateDeleteChoiceBox.setItems(taskTemplateData);

    ResidentChoiceBox.getSelectionModel().clearSelection();
    TaskCatalogueChoiceBox.getSelectionModel().clearSelection();
    TaskTemplateDeleteChoiceBox.getSelectionModel().clearSelection();
  }
}
