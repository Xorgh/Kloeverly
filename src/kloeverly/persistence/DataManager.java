package kloeverly.persistence;

import kloeverly.domain.*;

import java.util.List;

public interface DataManager
{
  void addCommunity(Community community);
  Community getCommunity();

  void addResident(Resident resident);
  List<Resident> getAllResidents();

  void addTask(Task task);
  List<Task> getAllTasks();
  List<GreenTask> getAllGreenTasks();
  List<CommunityTask> getAllCommunityTasks();
  List<ExchangeTask> getAllExchangeTasks();

  void addCommunityEvent(CommunityEvent event);
  List<CommunityEvent> getAllCommunityEvents();

  void addTaskTemplate(TaskTemplate taskTemplate);
  List<TaskTemplate> getAllTaskTemplates();

  List<CommunityTask> getAssignedTasksByResident(Resident resident);
  List<ExchangeTask> getOwnedTasksByResident(Resident resident);

  void save();
}
