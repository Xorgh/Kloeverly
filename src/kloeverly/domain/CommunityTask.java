package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class CommunityTask extends Task implements Serializable
{
  private int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private Resident assignedTo;
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

  public Resident getAssignedTo()
  {
    return assignedTo;
  }
}
