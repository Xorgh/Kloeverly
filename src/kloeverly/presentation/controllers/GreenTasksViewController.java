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
import javafx.util.StringConverter;
import jdk.management.jfr.RecordingInfo;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class GreenTasksViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  @FXML private TableView<GreenTask> greenTasksTable;

  @FXML private TableColumn<GreenTask, Integer> ID;

  @FXML private TableColumn<GreenTask, String> title;

  @FXML private TableColumn<GreenTask, String> description;

  @FXML private TableColumn<GreenTask, Integer> pointValue;

  @FXML private TableColumn<GreenTask, Resident> completedBy;

  @FXML private TableColumn<GreenTask, LocalDate> completedDate;

  @FXML private Button addNewGreenTaskButton;

  @FXML private TextArea descriptionTextArea;

  @FXML private TextField titleTextField;

  @FXML private TextField pointsTextField;

  @FXML private ChoiceBox<Resident> residentChoiceBox;

  @FXML private Label greenPointsLabel;

  private final ObservableList<GreenTask> tableData = FXCollections.observableArrayList();
  private final ObservableList<Resident> residentData = FXCollections.observableArrayList();


  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<GreenTask, Integer>("ID"));
    title.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("title"));
    description.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("description"));
    pointValue.setCellValueFactory(new PropertyValueFactory<GreenTask, Integer>("pointValue)"));
    completedBy.setCellValueFactory(new PropertyValueFactory<GreenTask, Resident>("completedBy"));
    completedDate.setCellValueFactory(new PropertyValueFactory<GreenTask, LocalDate>("completed"));

    greenTasksTable.setItems(tableData);

    residentChoiceBox.setItems(residentData);
    residentChoiceBox.setConverter(new StringConverter<Resident>()
    {
      @Override public String toString(Resident resident)
      {
        return resident == null ? "" : resident.getName() + " [" + resident.getID() + "]";
      }

      @Override public Resident fromString(String string)
      {
        return null;
      }
    });
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllGreenTasks());
    residentData.setAll(dataManager.getAllResidents());
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
  }

  @Override public void setArgument(String argument)
  {

  }
}
