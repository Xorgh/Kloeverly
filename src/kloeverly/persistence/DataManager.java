package kloeverly.persistence;

import kloeverly.domain.*;

import java.util.ArrayList;
import java.util.List;

public interface DataManager
{
  void addCommunity(Community community);
  Community getCommunity();

  void addResident(Resident resident);
  List<Resident> getAllResidents();

  void addTask(Task task);
  List<Task> getAllTasks();

  void addEvent(CommunityEvent event);
  List<CommunityEvent> getAllEvents();

  void addTaskTemplate(TaskTemplate taskTemplate);
  List<TaskTemplate> getAllTaskTemplates();

  List<CommunityTask> getAssignedTasksByResident(Resident resident);
  List<ExchangeTask> getOwnedTasksByResident(Resident resident);
}
