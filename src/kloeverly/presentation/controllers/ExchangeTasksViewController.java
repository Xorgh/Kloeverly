package kloeverly.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ExchangeTasksViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  @FXML private TableView<ExchangeTask> exchangeTasksTable;

  @FXML private TableColumn<ExchangeTask, Integer> ID;
  @FXML private TableColumn<ExchangeTask, String> title;
  @FXML private TableColumn<ExchangeTask, String> description;
  @FXML private TableColumn<ExchangeTask, Integer> pointValue;
  @FXML private TableColumn<ExchangeTask, TaskStatus> status;
  @FXML private TableColumn<ExchangeTask, LocalDate> created;
  @FXML private TableColumn<ExchangeTask, Resident> owner;
  @FXML private TableColumn<ExchangeTask, LocalDate> completed;
  @FXML private TableColumn<ExchangeTask, Resident> completedBy;

  @FXML private ChoiceBox<Resident> taskOwnerChoiceBox;

  @FXML private ChoiceBox<String> buyingOrSellingChoiceBox;
  @FXML private TextField titleTextField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TextField pointsTextField;
  @FXML private Button addTaskButton;

  @FXML private ChoiceBox<Resident> completedByChoiceBox;
  @FXML private Button completeTaskButton;

  @FXML private Button deleteTaskButton;

  private final ObservableList<ExchangeTask> tableData = FXCollections.observableArrayList();
  private final ObservableList<Resident> residentData = FXCollections.observableArrayList();
  private final ObservableList<String> buyingOrSellingData = FXCollections.observableArrayList("Køber", "Sælger");

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    title.setCellValueFactory(new PropertyValueFactory<>("title"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    pointValue.setCellValueFactory(new PropertyValueFactory<>("pointValue"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    created.setCellValueFactory(new PropertyValueFactory<>("created"));
    owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
    completed.setCellValueFactory(new PropertyValueFactory<>("completed"));
    completedBy.setCellValueFactory(new PropertyValueFactory<>("completedBy"));

    exchangeTasksTable.setItems(tableData);
    taskOwnerChoiceBox.setItems(residentData);
    completedByChoiceBox.setItems(residentData);
    buyingOrSellingChoiceBox.setItems(buyingOrSellingData);

    exchangeTasksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> updateControls());
    taskOwnerChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    completedByChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    buyingOrSellingChoiceBox.valueProperty().addListener((obs, oldV, newV) -> updateControls());
    titleTextField.textProperty().addListener((obs, oldV, newV) -> updateControls());
    descriptionTextArea.textProperty().addListener((obs, oldV, newV) -> updateControls());
    pointsTextField.textProperty().addListener((obs, oldV, newV) -> updateControls());

    updateControls();
  }


  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllExchangeTasks());
    residentData.setAll(dataManager.getAllResidents());
    updateControls();
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

  private ExchangeTask getSelectedTask()
  {
    ExchangeTask taskSelected = exchangeTasksTable.getSelectionModel().getSelectedItem();
    if (taskSelected == null) {
      System.out.println("No task selected.");
//      Throw error??
      return null;
    }
    return taskSelected;
  }

  @FXML void handleAddExchangeTask(ActionEvent event)
  {
    Resident owner = taskOwnerChoiceBox.getValue();
    String buyingOrSelling = buyingOrSellingChoiceBox.getValue();
    String taskTitle = titleTextField.getText();
    String taskDescription = descriptionTextArea.getText();
    int taskPointValue = Integer.parseInt(pointsTextField.getText());

    if (buyingOrSelling.equals("Sælger"))
    {
      taskTitle = "[Sælger] - " + taskTitle;
    }
    else
    {
      taskTitle = "[Køber] - " + taskTitle;
    }

    ExchangeTask newTask = new ExchangeTask(taskTitle, taskDescription, taskPointValue, owner);

    dataManager.addTask(newTask);
    dataManager.save();

    refreshView();
  }

  @FXML void handleCompleteTask(ActionEvent event)
  {
    ExchangeTask task = getSelectedTask();
    Resident executor = completedByChoiceBox.getValue();

    //    Test
    System.out.println("task = " + task);

    if (task == null)
    {
      System.out.println("No task selected, cannot complete.");
      return;
    }
    if (executor == null)
    {
      System.out.println("No executor selected, cannot complete.");
      return;
    }
    task.completeTask(executor);
    dataManager.save();
    refreshView();
  }

  @Override public void setArgument(String argument)
  {

  }

  @FXML void handleCancelTask(ActionEvent event)
  {
    ExchangeTask task = getSelectedTask();
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


  private void refreshView()
  {
    tableData.setAll(dataManager.getAllExchangeTasks());
    exchangeTasksTable.refresh();

    residentData.setAll(dataManager.getAllResidents());

    taskOwnerChoiceBox.setItems(residentData);
    completedByChoiceBox.setItems(residentData);
    taskOwnerChoiceBox.getSelectionModel().clearSelection();
    buyingOrSellingChoiceBox.getSelectionModel().clearSelection();
    completedByChoiceBox.getSelectionModel().clearSelection();

    updateControls();
  }


  private void updateControls()
  {
    ExchangeTask selectedTask = exchangeTasksTable.getSelectionModel().getSelectedItem();
    Resident ownerSelected = taskOwnerChoiceBox.getValue();
    String buyingOrSellingSelected = buyingOrSellingChoiceBox.getValue();

    Resident completedBySelected = completedByChoiceBox.getValue();


    boolean taskIsSelected = selectedTask != null;
    boolean isActive = taskIsSelected && selectedTask.getStatus() == TaskStatus.ACTIVE;
    boolean executorIsSelected = completedBySelected != null;

    boolean canComplete = taskIsSelected && isActive && executorIsSelected;
    completeTaskButton.setDisable(!canComplete);

    boolean canCancel = taskIsSelected && isActive;
    deleteTaskButton.setDisable(!canCancel);

    boolean validPoints = false;
    try {
      validPoints = Integer.parseInt(pointsTextField.getText()) > 0;
    } catch (Exception ignored) {}
    boolean canAddTemplate = titleTextField.getText() != null && !titleTextField.getText().isEmpty()
        && descriptionTextArea.getText() != null && !descriptionTextArea.getText().isEmpty()
        && validPoints;
    addTaskButton.setDisable(!canAddTemplate);
  }

}
