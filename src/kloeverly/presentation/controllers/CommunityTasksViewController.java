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

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CommunityTasksViewController implements Initializable
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

    communityTasksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> updateControls());
    ResidentChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    TaskCatalogueChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    TaskTemplateDeleteChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    titleTextField.textProperty().addListener((obs, oldV, newV) -> updateControls());
    descriptionTextArea.textProperty().addListener((obs, oldV, newV) -> updateControls());
    pointsTextField.textProperty().addListener((obs, oldV, newV) -> updateControls());

    updateControls();
  }


  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllCommunityTasks());
    residentData.setAll(dataManager.getActiveResidents());
    taskTemplateData.setAll(dataManager.getAllTaskTemplates());
    updateControls();
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

  private CommunityTask getSelectedTask()
  {
    CommunityTask taskSelected = communityTasksTable.getSelectionModel().getSelectedItem();
    if (taskSelected == null) {
      System.out.println("No task selected.");
//      Throw error??
      return null;
    }
    return taskSelected;
  }

  @FXML void handleAssignResidentToTask(ActionEvent event)
  {
    CommunityTask task = getSelectedTask();
    Resident resident = ResidentChoiceBox.getValue();

    //    Test
    System.out.println("task = " + task);
    System.out.println("resident = " + resident);

    if (task == null || resident == null)
    {
      System.out.println("Task or Resident is null, cannot assign.");
      return;
    }
    if (task.getStatus() != TaskStatus.ACTIVE)
    {
      System.out.println("Task is not active, cannot assign.");
      return;
    }
    task.assignTask(resident);
    dataManager.save();
    refreshView();
  }

  @FXML void handleCompleteTask(ActionEvent event)
  {
    CommunityTask task = getSelectedTask();

    //    Test
    System.out.println("task = " + task);

    if (task == null)
    {
      System.out.println("No task selected, cannot complete.");
      return;
    }
    if (task.getStatus() != TaskStatus.ACTIVE)
    {
      System.out.println("Task is not active, cannot complete.");
      return;
    }
    if (task.getAssignedTo() == null)
    {
      System.out.println("Task has no assigned resident, cannot complete.");
      return;
    }
    task.completeTask();
    dataManager.save();
    refreshView();
  }

  @FXML void handleCancelTask(ActionEvent event)
  {
    CommunityTask task = getSelectedTask();
    if (task == null)
    {
      System.out.println("No task selected, cannot delete.");
      return;
    }
    if (task.getStatus() != TaskStatus.ACTIVE)
    {
      System.out.println("Task is not active, cannot delete.");
      return;
    }
    task.cancelTask();
    dataManager.save();
    refreshView();
  }


  @FXML void handleDeleteTaskTemplate(ActionEvent event)
  {
    TaskTemplate selectedTemplate = TaskTemplateDeleteChoiceBox.getValue();
    if (selectedTemplate == null)
    {
      return;
    }
    dataManager.getCommunity().removeTaskTemplate(selectedTemplate);
    dataManager.save();
    TaskTemplateDeleteChoiceBox.getSelectionModel().clearSelection();
    refreshView();
  }

  private void refreshView()
  {
    tableData.setAll(dataManager.getAllCommunityTasks());
    communityTasksTable.refresh();

    residentData.setAll(dataManager.getActiveResidents());
    taskTemplateData.setAll(dataManager.getAllTaskTemplates());

    ResidentChoiceBox.setItems(residentData);
    TaskCatalogueChoiceBox.setItems(taskTemplateData);
    TaskTemplateDeleteChoiceBox.setItems(taskTemplateData);

    ResidentChoiceBox.getSelectionModel().clearSelection();
    TaskCatalogueChoiceBox.getSelectionModel().clearSelection();
    TaskTemplateDeleteChoiceBox.getSelectionModel().clearSelection();

    updateControls();
  }


  private void updateControls()
  {
    CommunityTask selectedTask = communityTasksTable.getSelectionModel().getSelectedItem();
    TaskTemplate catalogueSelected = TaskCatalogueChoiceBox.getValue();
    TaskTemplate deleteTemplateSelected = TaskTemplateDeleteChoiceBox.getValue();
    Resident residentSelected = ResidentChoiceBox.getValue();

    boolean taskIsSelected = selectedTask != null;
    boolean isActive = taskIsSelected && selectedTask.getStatus() == TaskStatus.ACTIVE;
    boolean hasAssignedResident = taskIsSelected && isActive && selectedTask.getAssignedTo() != null;
    boolean residentIsSelected = residentSelected != null;

    assignResident.setDisable(!(taskIsSelected && isActive && residentIsSelected));

    boolean canComplete = taskIsSelected && isActive && hasAssignedResident;
    completeTaskButton.setDisable(!canComplete);

    boolean canCancel = taskIsSelected && isActive;
    deleteTaskButton.setDisable(!canCancel);

    addTaskButton.setDisable(catalogueSelected == null);

    deleteTaskTemplateButton.setDisable(deleteTemplateSelected == null);

    boolean validPoints = false;
    try {
      validPoints = Integer.parseInt(pointsTextField.getText()) > 0;
    } catch (Exception ignored) {}
    boolean canAddTemplate = titleTextField.getText() != null && !titleTextField.getText().isEmpty()
        && descriptionTextArea.getText() != null && !descriptionTextArea.getText().isEmpty()
        && validPoints;
    addTaskTemplateButton.setDisable(!canAddTemplate);
  }
}
