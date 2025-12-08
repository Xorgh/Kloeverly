package kloeverly.persistence;

import kloeverly.domain.*;

import java.util.List;

public interface DataManager
{
  void addCommunity(Community community);
  Community getCommunity();

  void addResident(Resident resident);
  List<Resident> getAllResidents();
  List<Resident> getActiveResidents();

  void addTask(Task task);
  List<Task> getAllTasks();
  List<GreenTask> getAllGreenTasks();
  List<CommunityTask> getAllCommunityTasks();
  List<ExchangeTask> getAllExchangeTasks();

  void addCommunityEvent(CommunityEvent event);
  List<CommunityEvent> getAllCommunityEvents();

  void addTaskTemplate(TaskTemplate taskTemplate);
  List<TaskTemplate> getAllTaskTemplates();

  void save();

  List<CommunityTask> getAllValidCommunityTasks();
  List<ExchangeTask> getAllValidExchangeTasks();
  List<CommunityEvent> getAllValidCommunityEvents();
}
