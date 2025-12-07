package kloeverly.persistence;

import kloeverly.domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileDataManager implements DataManager
{
  private final File file;
  private DataContainer container;

  public FileDataManager(String filePath, String communityName)
  {
    this.file = new File(Objects.requireNonNull(filePath));
    this.container = loadOrCreate(communityName);
  }

  private DataContainer loadOrCreate(String communityName)
  {
    if (file.exists())
    {
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
      {
        Object obj = in.readObject();
        if (obj instanceof DataContainer)
        {
          DataContainer dataContainer = (DataContainer) obj;
          System.out.println("Data loaded successfully.");
          resetIdCounters(dataContainer);
          return dataContainer;
        }
      }
      catch (Exception e)
      {
        System.out.println(e + "\nFailed to load data, creating new data container.");
      }
    }
    return new DataContainer(communityName);
  }

  private void resetIdCounters(DataContainer dataContainer)
  {
    Community community = dataContainer.getCommunity();

    // Check and set next Resident ID
    int maxResidentId = 0;
    for (Resident resident : community.getResidents())
    {
      if (resident.getID() > maxResidentId)
      {
        maxResidentId = resident.getID();
      }
    }
    Resident.setNextId(maxResidentId + 1);
    System.out.println("maxResidentId = " + maxResidentId);

    // Check and set next Task ID
    int maxTaskId = 0;
    for (Task task : community.getTasks())
    {
      if (task.getID() > maxTaskId)
      {
        maxTaskId = task.getID();
      }
    }
    Task.setNextId(maxTaskId + 1);
    System.out.println("maxTaskId = " + maxTaskId);

    // Check and set next TaskTemplate ID
    int maxTaskTemplateId = 0;
    for (TaskTemplate taskTemplate : community.getCommunityTaskCatalogue())
    {
      if (taskTemplate.getID() > maxTaskTemplateId)
      {
        maxTaskTemplateId = taskTemplate.getID();
      }
    }
    TaskTemplate.setNextId(maxTaskTemplateId + 1);
    System.out.println("maxTaskTemplateId = " + maxTaskTemplateId);

    // Check and set next Event ID
    int maxEventId = 0;
    for (CommunityEvent event : community.getEvents())
    {
      if (event.getID() > maxEventId)
      {
        maxEventId = event.getID();
      }
    }
    CommunityEvent.setNextId(maxEventId + 1);
    System.out.println("maxEventId = " + maxEventId);
  }

  @Override public void save()
  {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
    {
      out.writeObject(container);
    }
    catch (IOException e)
    {
      throw new UncheckedIOException("Failed to save data", e);
    }
  }

  @Override public void addCommunity(Community community)
  {
    throw new IllegalStateException("Community is owned by DataContainer and created at startup.");
  }

  @Override public Community getCommunity()
  {
    return container.getCommunity();
  }

  @Override public void addResident(Resident resident)
  {
    container.getCommunity().addResident(resident);
    save();
  }

  @Override public List<Resident> getAllResidents()
  {
    return new ArrayList<>(container.getCommunity().getResidents());
  }

  @Override public void addTask(Task task)
  {
    container.getCommunity().addTask(task);
    save();
  }

  @Override public List<Task> getAllTasks()
  {
    return new ArrayList<>(container.getCommunity().getTasks());
  }

  @Override public List<GreenTask> getAllGreenTasks()
  {
    ArrayList<GreenTask> resultArray = new ArrayList<>();

    for (Task task : container.getCommunity().getTasks())
    {
      if (task instanceof GreenTask)
      {
        resultArray.add((GreenTask) task);
      }
    }
    return resultArray;
  }

  @Override public List<CommunityTask> getAllCommunityTasks()
  {
    ArrayList<CommunityTask> resultArray = new ArrayList<>();

    for (Task task : container.getCommunity().getTasks())
    {
      if (task instanceof CommunityTask)
      {
        resultArray.add((CommunityTask) task);
      }
    }
    return resultArray;
  }

  @Override public List<ExchangeTask> getAllExchangeTasks()
  {
    ArrayList<ExchangeTask> resultArray = new ArrayList<>();

    for (Task task : container.getCommunity().getTasks())
    {
      if (task instanceof ExchangeTask)
      {
        resultArray.add((ExchangeTask) task);
      }
    }
    return resultArray;
  }

  @Override public void addEvent(CommunityEvent event)
  {
    container.getCommunity().addEvent(event);
    save();
  }

  @Override public List<CommunityEvent> getAllEvents()
  {
    return new ArrayList<>(container.getCommunity().getEvents());
  }

  @Override public void addTaskTemplate(TaskTemplate taskTemplate)
  {
    container.getCommunity().addTaskTemplate(taskTemplate);
    save();
  }

  @Override public List<TaskTemplate> getAllTaskTemplates()
  {
    return new ArrayList<>(container.getCommunity().getCommunityTaskCatalogue());
  }

  @Override public List<CommunityTask> getAssignedTasksByResident(Resident resident)
  {
    return new ArrayList<>(container.getCommunity().getAssignedTasksByResident(resident));
  }

  @Override public List<ExchangeTask> getOwnedTasksByResident(Resident resident)
  {
    return new ArrayList<>(container.getCommunity().getOwnedTasksByResident(resident));
  }
}