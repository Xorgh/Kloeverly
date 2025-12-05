package kloeverly.domain;

import java.time.LocalDate;

public class CommunityTask
{
  private int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private boolean isAnonymous;
  private LocalDate created;
  private LocalDate completed;
  private Resident completedBy;

  public CommunityTask(String title, String description, int pointValue)
  {
    this.title = title;
    this.description = description;
    this.pointValue = pointValue;
  }

  public void completeTask()
  {

  }

  public void deleteTask()
  {

  }
}
