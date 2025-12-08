package kloeverly.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable
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

  public void addCommunityEvent(CommunityEvent event)
  {
    events.add(event);
  }

  public void addTaskTemplate(TaskTemplate taskTemplate)
  {
    communityTaskCatalogue.add(taskTemplate);
  }

  public void removeTaskTemplate(TaskTemplate taskTemplate)
  {
    communityTaskCatalogue.remove(taskTemplate);
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

  public ArrayList<Task> getTasks()
  {
    return tasks;
  }

  public ArrayList<CommunityTask> getAssignedTasksByResident(Resident resident)
  {
    ArrayList<CommunityTask> resultArray = new ArrayList<>();

    for (Task task : tasks)
    {
      if (task instanceof CommunityTask && task.getStatus() == TaskStatus.ACTIVE)
      {
        if (((CommunityTask) task).getAssignedTo() == resident)
        {
          resultArray.add((CommunityTask) task);
        }
      }
    }
    return resultArray;
  }

  public ArrayList<ExchangeTask> getOwnedTasksByResident(Resident resident)
  {
    ArrayList<ExchangeTask> resultArray = new ArrayList<>();

    for (Task task : tasks)
    {
      if (task instanceof ExchangeTask && task.getStatus() == TaskStatus.ACTIVE)
      {
        if (((ExchangeTask) task).getOwner() == resident)
        {
          resultArray.add((ExchangeTask) task);
        }
      }
    }
    return resultArray;
  }

  public void addGreenPoints(int pointValue)
  {
    greenPointsBalance += pointValue;
  }
}
