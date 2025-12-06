package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import kloeverly.persistence.DataManager;
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
  }



  public void handleAddCommunityTask(ActionEvent event)
  {
  }

  public void handleAddTaskTemplate(ActionEvent event)
  {
  }

  public void handleShowAllUnassignedCommunityTasks(ActionEvent event)
  {
  }

  public void handleShowAllAssignedCommunityTasks(ActionEvent event)
  {
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
  }

  public void handleAddExchangeTask(ActionEvent event)
  {
  }

  public void handleShowAllAssignedExchangeTasks(ActionEvent event)
  {
  }

  @FXML public void handleShowAllEvents(Event event) {
    Object source = event.getSource();

    if (source instanceof Label) {
      System.out.println("Label clicked: Show all Events");
    } else if (source instanceof Button) {
      System.out.println("Button clicked: Show all Events");
    }
  }

  public void handleAddEvent(ActionEvent event)
  {
  }

  public void handleEvent(ActionEvent event)
  {
  }

  public void handleResetAllPoints(ActionEvent event)
  {
  }

  public void handleShowDashboard(ActionEvent event)
  {
  }
}
