package kloeverly.domain;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class Community
{
  private String name;
  private int greenPointBalance;
  private ArrayList<Resident> residents;
  private ArrayList<Task> allTasks;
  private ArrayList<Event> events;
  private ArrayList<TaskTemplate> communityTaskCatalogue;

  public Community(String name, int greenPointBalance,
      ArrayList<Resident> residents, ArrayList<Task> allTasks,
      ArrayList<Event> events, ArrayList<TaskTemplate> communityTaskCatalogue)
  {
    this.name = name;
    this.greenPointBalance = greenPointBalance;
    this.residents = residents;
    this.allTasks = allTasks;
    this.events = events;
    this.communityTaskCatalogue = communityTaskCatalogue;
  }
}
