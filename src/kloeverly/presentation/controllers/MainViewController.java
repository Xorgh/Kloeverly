package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.ControllerConfigurator;
import kloeverly.presentation.core.DashboardUtil;
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
    dataManager.getCommunity().resetAllResidentsPoints();
    dataManager.save();
    ViewManager.reloadCurrentView();
  }

  public void handleShowDashboard(Event event)
  {
    ViewManager.showView("DashboardView");
  }

  public void handleShowExternalDashboard(Event event)
  {
    DashboardUtil.openDashboardWindow();
  }

  public void handleAddTestData(ActionEvent event)
  {
    if (this.dataManager == null)
    {
     this.dataManager = ControllerConfigurator.getDataManager();
    }

    Community community = dataManager.getCommunity();

    Resident gb = new Resident("Grønne Bob");
    Resident dv = new Resident("Don Vegan");
    community.addResident(gb);
    community.addResident(dv);

    gb.addToPersonalPointBalance(1000);
    dv.setPointBoosted(true);
    dv.addToPersonalPointBalance(1000);

    GreenTask greenTask = new GreenTask( "Affaldssortering", "Jeg sorterede alt mit affald i sidste uge.", 1000, gb);
    community.addTask(greenTask);
    greenTask.completeTask(community);

    community.addCommunityEvent(new CommunityEvent("Pizza Party", "Lad os fejre at have nået vores grønne mål med et pizza party!", 1500));

    community.addTaskTemplate(new TaskTemplate("Madlavning", "Lav mad til fællesspisning", 100));
    community.addTaskTemplate(new TaskTemplate("Havearbejde", "Slå græs på fællesarealet.", 200));


    CommunityTask task1 = new CommunityTask(community.getCommunityTaskCatalogue().getFirst());
    CommunityTask task2 = new CommunityTask(community.getCommunityTaskCatalogue().getLast());
    community.addTask(task1);
    community.addTask(task2);
    task1.assignTask(gb);
    task2.assignTask(dv);

    community.addTask(new ExchangeTask("Græsslåning", "Jeg slår dit græs.", 150, false, gb));
    community.addTask(new ExchangeTask("Dagligvareindkøb", "Jeg har brug for hjælp til at handle ind.", 100, true, dv));

    dataManager.save();
    ViewManager.reloadCurrentView();
  }
}
