package kloeverly.presentation.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable
{

  private DataManager dataManager;
  private Timeline refreshDashboardTimeline;

  @FXML private Label greenPointsLabel;

  @FXML private TableView<GreenTask> greenTasksTable;
  @FXML private TableColumn<GreenTask, String> greenTasksTitle;
  @FXML private TableColumn<GreenTask, String> greenTasksDescription;
  @FXML private TableColumn<GreenTask, Integer> greenTasksPointValue;
  @FXML private TableColumn<GreenTask, LocalDate> greenTasksCompletedDate;

  @FXML private TableView<CommunityTask> communityTasksTable;
  @FXML private TableColumn<CommunityTask, String> communityTasksTitle;
  @FXML private TableColumn<CommunityTask, String> communityTasksDescription;
  @FXML private TableColumn<CommunityTask, Integer> communityTasksPointValue;
  @FXML private TableColumn<CommunityTask, LocalDate> communityTasksCreated;
  @FXML private TableColumn<CommunityTask, Resident> communityTasksAssignedTo;

  @FXML private TableView<Resident> residentsTable;
  @FXML private TableColumn<Resident, String> name;
  @FXML private TableColumn<Resident, Integer> personalPointBalance;
  @FXML private TableColumn<Resident, Integer> reservedBalance;
  @FXML private TableColumn<Resident, Boolean> pointBoosted;

  @FXML private TableView<ExchangeTask> exchangeTasksTable;
  @FXML private TableColumn<ExchangeTask, String> exchangeTasksTitle;
  @FXML private TableColumn<ExchangeTask, String> exchangeTasksDescription;
  @FXML private TableColumn<ExchangeTask, Integer> exchangeTasksPointValue;
  @FXML private TableColumn<ExchangeTask, LocalDate> exchangeTasksCreated;
  @FXML private TableColumn<ExchangeTask, Resident> exchangeTasksOwner;

  @FXML private ListView<CommunityEvent> eventsListView;

  private final ObservableList<GreenTask> greenTasksData = FXCollections.observableArrayList();
  private final ObservableList<CommunityTask> communityTasksData = FXCollections.observableArrayList();
  private final ObservableList<Resident> residentsData = FXCollections.observableArrayList();
  private final ObservableList<ExchangeTask> exchangeTasksData = FXCollections.observableArrayList();
  private final ObservableList<CommunityEvent> communityEventsData = FXCollections.observableArrayList();

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    initializeGreenTasksTable();
    initializeCommunityTasksTable();
    initializeExchangeTasksTable();
    initializeEventsListView();
    initialiseResidentsTable();
  }

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    greenTasksData.setAll(dataManager.getAllGreenTasks());
    communityTasksData.setAll(dataManager.getAllValidCommunityTasks());
    exchangeTasksData.setAll(dataManager.getAllValidExchangeTasks());
    communityEventsData.setAll(dataManager.getAllValidCommunityEvents());
    residentsData.setAll(dataManager.getActiveResidents());
    refreshView();
    startAutoRefresh();
  }

  private void startAutoRefresh()
  {
    refreshDashboardTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> refreshView()));
    refreshDashboardTimeline.setCycleCount(Animation.INDEFINITE);
    refreshDashboardTimeline.play();
  }

  private void initializeGreenTasksTable()
  {
    greenTasksTitle.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("title"));
    greenTasksDescription.setCellValueFactory(new PropertyValueFactory<GreenTask, String>("description"));
    greenTasksPointValue.setCellValueFactory(new PropertyValueFactory<GreenTask, Integer>("pointValue"));
    greenTasksCompletedDate.setCellValueFactory(new PropertyValueFactory<GreenTask, LocalDate>("completed"));

    greenTasksTable.setItems(greenTasksData);
  }

  private void initializeCommunityTasksTable()
  {
    communityTasksTitle.setCellValueFactory(new PropertyValueFactory<CommunityTask, String>("title"));
    communityTasksDescription.setCellValueFactory(new PropertyValueFactory<CommunityTask, String>("description"));
    communityTasksPointValue.setCellValueFactory(new PropertyValueFactory<CommunityTask, Integer>("pointValue"));
    communityTasksCreated.setCellValueFactory(new PropertyValueFactory<CommunityTask, LocalDate>("created"));
    communityTasksAssignedTo.setCellValueFactory(new PropertyValueFactory<CommunityTask, Resident>("assignedTo"));

    communityTasksTable.setItems(communityTasksData);
  }

  private void initializeExchangeTasksTable()
  {
    exchangeTasksTitle.setCellValueFactory(new PropertyValueFactory<ExchangeTask, String>("title"));
    exchangeTasksDescription.setCellValueFactory(new PropertyValueFactory<ExchangeTask, String>("description"));
    exchangeTasksPointValue.setCellValueFactory(new PropertyValueFactory<ExchangeTask, Integer>("pointValue"));
    exchangeTasksCreated.setCellValueFactory(new PropertyValueFactory<ExchangeTask, LocalDate>("created"));
    exchangeTasksOwner.setCellValueFactory(new PropertyValueFactory<ExchangeTask, Resident>("owner"));

    exchangeTasksTable.setItems(exchangeTasksData);
  }

  private void initializeEventsListView()
  {
    eventsListView.setItems(communityEventsData);
  }

  private void initialiseResidentsTable()
  {
    name.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
    personalPointBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("personalPointBalance"));
    reservedBalance.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("reservedBalance"));
    pointBoosted.setCellValueFactory(new PropertyValueFactory<Resident, Boolean>("pointBoosted"));

    residentsTable.setItems(residentsData);
  }

  private void refreshView()
  {
    if (dataManager == null)
      return;

    updateGreenPointsLabel();
    greenTasksData.setAll(dataManager.getAllGreenTasks());
    greenTasksTable.refresh();

    communityTasksData.setAll(dataManager.getAllValidCommunityTasks());
    communityTasksTable.refresh();

    exchangeTasksData.setAll(dataManager.getAllValidExchangeTasks());
    exchangeTasksTable.refresh();

    communityEventsData.setAll(dataManager.getAllValidCommunityEvents());
    eventsListView.refresh();

    residentsData.setAll(dataManager.getActiveResidents());
    residentsTable.refresh();
  }

  private void updateGreenPointsLabel()
  {
    int greenPoints = dataManager.getCommunity().getGreenPointsBalance();
    greenPointsLabel.setText(Integer.toString(greenPoints));
  }
}
