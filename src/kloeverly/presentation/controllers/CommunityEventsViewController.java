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
    completedDate.setCellValueFactory(new PropertyValueFactory<CommunityEvent, LocalDate>("completed"));

    communityEventsTable.setItems(tableData);
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


  private void refreshView()
  {
    tableData.setAll(dataManager.getAllCommunityEvents());
    communityEventsTable.refresh();

    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();
    greenPointsLabel.setText(Integer.toString(greenPoints));

    updateControls();
  }


  private void updateControls()
  {
    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();
    String unlockThresholdString = unlockThresholdTextField.getText();
    int newThreshold;

    CommunityEvent selectedEvent = communityEventsTable.getSelectionModel().getSelectedItem();

    boolean eventIsSelected = selectedEvent != null;
    boolean isCancelled = eventIsSelected && selectedEvent.getStatus() == EventStatus.CANCELLED;
    boolean unlockThresholdMet = eventIsSelected && !isCancelled && greenPoints >= selectedEvent.getUnlockThreshold();

    completeEventButton.setDisable(!unlockThresholdMet);

    boolean canCancel = eventIsSelected && !isCancelled;
    deleteEventButton.setDisable(!canCancel);

    try {
      newThreshold = Integer.parseInt(unlockThresholdString);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Ugyldigt tal i milepæl: " + unlockThresholdString, e);
    }

    if (newThreshold <= greenPoints) {
      throw new IllegalArgumentException("Du kan ikke sætte en milepæl mindre end nuværende fællesskabspulje.");
    }

    boolean canAddEvent = titleTextField.getText() != null && !titleTextField.getText().isEmpty()
        && descriptionTextArea.getText() != null && !descriptionTextArea.getText().isEmpty();
    addNewCommunityEventButton.setDisable(!canAddEvent);
  }

  @Override public void setArgument(String argument)
  {

  }


}
