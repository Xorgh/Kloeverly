package kloeverly.domain;

import java.util.ArrayList;

public class Community
{
  private String name;
  private int greenPointsBalance;
  private ArrayList<Resident> residents;
  private ArrayList<Task> tasks;
  private ArrayList<CommunityEvent> events;
  private ArrayList<TaskTemplate> communityTaskCatalogue;

  public Community(String name)
  {
    this.name = name;
    greenPointsBalance = 0;
    residents = new ArrayList<>();
    tasks = new ArrayList<>();
    events = new ArrayList<>();
    communityTaskCatalogue = new ArrayList<>();
  }

  public void addResident(Resident resident)
  {
    residents.add(resident);
  }

  public void addTask(Task task)
  {
    tasks.add(task);
  }

  public void addEvent(CommunityEvent event)
  {
    events.add(event);
  }

  public void addTaskTemplate(TaskTemplate taskTemplate)
  {
    communityTaskCatalogue.add(taskTemplate);
  }

  public String getName()
  {
    return name;
  }

  public int getGreenPointsBalance()
  {
    return greenPointsBalance;
  }

  public void setGreenPointsBalance(int newBalance)
  {
    greenPointsBalance = newBalance;
  }

  public ArrayList<Resident> getResidents()
  {
    return residents;
  }

  public ArrayList<CommunityEvent> getEvents()
  {
    return events;
  }

  public ArrayList<TaskTemplate> getCommunityTaskCatalogue()
  {
    return communityTaskCatalogue;
  }
}
