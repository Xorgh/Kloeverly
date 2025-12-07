package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ExchangeTask extends Task implements Serializable
{
  private final int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private Resident owner;
  private LocalDate completed;
  private Resident completedBy;

  public ExchangeTask(String title, String description, int pointValue, Resident owner)
  {
    if (owner.getPersonalPointBalance() < pointValue)
    {
      throw new IllegalArgumentException("Owner does not have enough points to create this exchange task.");
    }
    else
    {
      ID = Task.getNextId();
      this.title = title;
      this.description = description;
      this.pointValue = pointValue;
      this.owner = owner;
      status = TaskStatus.ACTIVE;
      created = LocalDate.now();
      owner.reserveBalance(pointValue);
    }
  }

  public void completeTask(Resident executor)
  {
    if (status != TaskStatus.ACTIVE)
    {
      throw new IllegalStateException("Task is not active and cannot be completed.");
    }
    if (executor == owner)
    {
      throw new IllegalStateException("Owner cannot complete their own exchange task.");
    }
    if (executor.getPersonalPointBalance() < pointValue)
    {
      throw new IllegalStateException("Executor does not have enough points to complete this exchange task.");
    }
    status = TaskStatus.COMPLETED;
    completed = LocalDate.now();
    completedBy = executor;
    owner.setReservedBalance(owner.getReservedBalance() - pointValue);
    executor.addToPersonalPointBalance(pointValue);
    //TODO: Implement BUYING AND SELLING LOGIC HERE
  }

  public void cancelTask()
  {
    if (status != TaskStatus.ACTIVE)
    {
      throw new IllegalStateException("Only active tasks can be cancelled.");
    }
    status = TaskStatus.CANCELLED;
    owner.releaseReservedBalance(pointValue);
  }

  public Resident getOwner()
  {
    return owner;
  }

  @Override public int getID()
  {
    return ID;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription()
  {
    return description;
  }

  public int getPointValue()
  {
    return pointValue;
  }

  @Override public TaskStatus getStatus()
  {
    return status;
  }

  public LocalDate getCreated()
  {
    return created;
  }

  public LocalDate getCompleted()
  {
    return completed;
  }

  public Resident getCompletedBy()
  {
    return completedBy;
  }
}

