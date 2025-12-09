package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.ControllerConfigurator;
import kloeverly.presentation.core.ViewManager;

public class MainViewController
{
  private DataManager dataManager;

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
  }



  @FXML private void handleViewResidents(Event event) {
    Object source = event.getSource();

    if (source instanceof Label) {
      System.out.println("Label clicked: Show all residents");
    } else if (source instanceof Button) {
      System.out.println("Button clicked: Show all residents");
    }
    ViewManager.showView("ResidentsView");
  }

  @FXML private void handleViewGreenTasks(Event event) {
    Object source = event.getSource();

    if (source instanceof Label) {
      System.out.println("Label clicked: View Green Tasks");
    } else if (source instanceof Button) {
      System.out.println("Button clicked: View Green Tasks");
    }
    ViewManager.showView("GreenTasksView");
  }

  @FXML private void handleShowAllCommunityTasks(Event event) {
    Object source = event.getSource();

    if (source instanceof Label) {
      System.out.println("Label clicked: Show all Community Tasks");
    } else if (source instanceof Button) {
      System.out.println("Button clicked: Show all Community Tasks");
    }
    ViewManager.showView("CommunityTasksView");
  }

  @FXML public void handleShowAllExchangeTasks(Event event)
  {
    Object source = event.getSource();

    if (source instanceof Label)
    {
      System.out.println("Label clicked: Show all Exchange Tasks");
    }
    else if (source instanceof Button)
    {
      System.out.println("Button clicked: Show all Exchange Tasks");
    }
    ViewManager.showView("ExchangeTasksView");
  }


  @FXML public void handleShowAllEvents(Event event) {
    Object source = event.getSource();

    if (source instanceof Label) {
      System.out.println("Label clicked: Show all Events");
    } else if (source instanceof Button) {
      System.out.println("Button clicked: Show all Events");
    }
    ViewManager.showView("CommunityEventsView");
  }

  public void handleResetAllPoints(ActionEvent event)
  {
  }

  public void handleShowDashboard(ActionEvent event)
  {
    ViewManager.showView("DashboardView");
  }

  public void handleAddTestData(ActionEvent event)
  {
    if (this.dataManager == null)
    {
     this.dataManager = ControllerConfigurator.getDataManager();
    }

    Community community = dataManager.getCommunity();

    Resident jd = new Resident("John Doe");
    Resident js = new Resident("Jane Smith");
    community.addResident(jd);
    community.addResident(js);

    jd.addToPersonalPointBalance(1000);
    js.setPointBoosted(true);
    js.addToPersonalPointBalance(1000);

    community.addCommunityEvent(new CommunityEvent("Pizza Party", "Celebrate community achievements with a pizza party!", 1500));

    community.addTaskTemplate(new TaskTemplate("Recycle Paper", "Collect and recycle paper waste.", 100));
    community.addTaskTemplate(new TaskTemplate("Plant a Tree", "Plant a tree in your community.", 200));


    CommunityTask task1 = new CommunityTask(community.getCommunityTaskCatalogue().getFirst());
    CommunityTask task2 = new CommunityTask(community.getCommunityTaskCatalogue().getLast());
    community.addTask(task1);
    community.addTask(task2);
    task1.assignTask(jd);
    task2.assignTask(js);

    community.addTask(new ExchangeTask("Lawn Mowing", "I will mow your lawn.", 150, false, jd));
    community.addTask(new ExchangeTask("Grocery Shopping", "I need someone to help with my grocery shopping.", 100, true, js));

    dataManager.save();
    ViewManager.reloadCurrentView();
  }
}
