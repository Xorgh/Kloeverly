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

public class CommunityEventsViewController implements Initializable, AcceptsStringArgument
{
  private DataManager dataManager;

  @FXML private TableView<CommunityEvent> communityEventsTable;

  @FXML private TableColumn<CommunityEvent, Integer> ID;
  @FXML private TableColumn<CommunityEvent, String> title;
  @FXML private TableColumn<CommunityEvent, String> description;
  @FXML private TableColumn<CommunityEvent, Integer> unlockThreshold;
  @FXML private TableColumn<CommunityEvent, EventStatus> status;
  @FXML private TableColumn<CommunityEvent, LocalDate> completedDate;

  @FXML private TextField titleTextField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TextField unlockThresholdTextField;
  @FXML private Button addNewCommunityEventButton;

  @FXML private Label greenPointsLabel;

  @FXML private Button completeEventButton;
  @FXML private Button deleteEventButton;

  private final ObservableList<CommunityEvent> tableData = FXCollections.observableArrayList();

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    ID.setCellValueFactory(new PropertyValueFactory<CommunityEvent, Integer>("ID"));
    title.setCellValueFactory(new PropertyValueFactory<CommunityEvent, String>("title"));
    description.setCellValueFactory(new PropertyValueFactory<CommunityEvent, String>("description"));
    unlockThreshold.setCellValueFactory(new PropertyValueFactory<CommunityEvent, Integer>("unlockThreshold"));
    status.setCellValueFactory(new PropertyValueFactory<CommunityEvent, EventStatus>("status"));
    completedDate.setCellValueFactory(new PropertyValueFactory<CommunityEvent, LocalDate>("completedDate"));

    communityEventsTable.setItems(tableData);

    communityEventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateControls());
    titleTextField.textProperty().addListener((obs, oldText, newText) -> updateControls());
    descriptionTextArea.textProperty().addListener((obs, oldText, newText) -> updateControls());
    unlockThresholdTextField.textProperty().addListener((obs, oldText, newText) -> updateControls());

    updateControls();
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tableData.setAll(dataManager.getAllCommunityEvents());
    refreshView();
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
    String text = unlockThresholdTextField.getText();
  }

  @FXML void handleAddNewCommunityEvent(ActionEvent event)
  {
    String eventTitle = titleTextField.getText();
    String eventDescription = descriptionTextArea.getText();
    int unlockThreshold = Integer.parseInt(unlockThresholdTextField.getText());

    CommunityEvent newEvent = new CommunityEvent(eventTitle, eventDescription, unlockThreshold);
    dataManager.addCommunityEvent(newEvent);

    dataManager.save();
    refreshView();

    titleTextField.clear();
    descriptionTextArea.clear();
    unlockThresholdTextField.clear();
  }


  private CommunityEvent getSelectedEvent()
  {
    CommunityEvent eventSelected = communityEventsTable.getSelectionModel().getSelectedItem();
    if (eventSelected == null) {
      System.out.println("No event selected.");
      //      Throw error??
      return null;
    }
    return eventSelected;
  }

  private void refreshView()
  {
    if (dataManager == null) return;

    dataManager.getCommunity().updateEventsStatus();
    dataManager.save();

    tableData.setAll(dataManager.getAllCommunityEvents());
    communityEventsTable.refresh();

    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();
    greenPointsLabel.setText(Integer.toString(greenPoints));

    updateControls();
  }

  private void updateControls()
  {
    if (dataManager == null)
    {
      if (completeEventButton != null) completeEventButton.setDisable(true);
      if (deleteEventButton != null) deleteEventButton.setDisable(true);
      if (addNewCommunityEventButton != null) addNewCommunityEventButton.setDisable(true);
      if (greenPointsLabel != null) greenPointsLabel.setText("0");
      return;
    }

    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();

    CommunityEvent selectedEvent = communityEventsTable.getSelectionModel().getSelectedItem();

    boolean eventIsSelected = selectedEvent != null;
    boolean isCancelled = eventIsSelected && selectedEvent.getStatus() == EventStatus.CANCELLED;
    boolean unlockThresholdMet = eventIsSelected && !isCancelled && greenPoints >= selectedEvent.getUnlockThreshold();

    completeEventButton.setDisable(!unlockThresholdMet);

    boolean canCancel = eventIsSelected && !isCancelled;
    deleteEventButton.setDisable(!canCancel);

    String unlockThresholdString = unlockThresholdTextField.getText();
    boolean validThreshold = false;
    if (unlockThresholdString != null && !unlockThresholdString.isEmpty())
    {
      try
      {
        int newThreshold = Integer.parseInt(unlockThresholdString);
        validThreshold = newThreshold > greenPoints;
      }
      catch (NumberFormatException ignored)
      {
      }
    }

    boolean hasTitle = titleTextField.getText() != null && !titleTextField.getText().isEmpty();
    boolean hasDescription = descriptionTextArea.getText() != null && !descriptionTextArea.getText().isEmpty();

    boolean canAddEvent = hasTitle && hasDescription && validThreshold;
    addNewCommunityEventButton.setDisable(!canAddEvent);
  }

  @Override public void setArgument(String argument)
  {

  }

  public void handleCompleteEvent(ActionEvent event)
  {
    CommunityEvent selectedEvent = getSelectedEvent();
    if (selectedEvent == null)
    {
      System.out.println("No event selected, cannot complete.");
      return;
    }
    selectedEvent.completeEvent();
    dataManager.save();
    refreshView();
  }

  public void handleDeleteEvent(ActionEvent event)
  {
    CommunityEvent selectedEvent = getSelectedEvent();
    if (selectedEvent == null)
    {
      System.out.println("No event selected, cannot delete.");
      return;
    }
    selectedEvent.deleteEvent();
    dataManager.save();
    refreshView();
  }
}
