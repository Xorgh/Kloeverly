package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class CommunityTask extends Task implements Serializable
{
  private final int ID;
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
    ID = Task.getNextId();
    this.title = title;
    this.description = description;
    this.pointValue = pointValue;
    status = TaskStatus.ACTIVE;
    created = LocalDate.now();
  }

  public void assignTask(Resident resident)
  {
    if (status != TaskStatus.ACTIVE)
    {
      throw new IllegalStateException("Task is not active and cannot be assigned.");
    }
    assignedTo = resident;
  }

  public void completeTask()
  {
    if (assignedTo != null)
    {
      status = TaskStatus.COMPLETED;
      completed = LocalDate.now();
      completedBy = assignedTo;
      completedBy.addToPersonalPointBalance(pointValue);
    }
  }

  public void cancelTask()
  {
    if (status != TaskStatus.ACTIVE)
    {
      throw new IllegalStateException("Only active tasks can be cancelled.");
    }
    status = TaskStatus.CANCELLED;
  }

  public Resident getAssignedTo()
  {
    return assignedTo;
  }
}
