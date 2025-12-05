package kloeverly.persistence;

import kloeverly.domain.*;

import java.io.*;
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
          return (DataContainer) obj;
        }
      }
      catch (Exception ignored)
      {
        // Fall through to create new
      }
    }
    return new DataContainer(communityName);
  }

  private void save()
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