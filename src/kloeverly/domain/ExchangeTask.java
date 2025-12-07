package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ExchangeTask extends Task implements Serializable
{
  private final int ID;
  private String title;
  private String offeredItem;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private Resident owner;
  private LocalDate completed;
  private Resident completedBy;

  public ExchangeTask(String title, String offeredItem, String description, int pointValue, Resident owner)
  {
    if (owner.getPersonalPointBalance() < pointValue)
    {
      throw new IllegalArgumentException("Owner does not have enough points to create this exchange task.");
    }
    else
    {
      ID = Task.getNextId();
      this.title = title;
      this.offeredItem = offeredItem;
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
    if (status == TaskStatus.ACTIVE && executor.getPersonalPointBalance() >= pointValue)
    {
      status = TaskStatus.COMPLETED;
      completed = LocalDate.now();
      completedBy = executor;
      owner.setReservedBalance(owner.getReservedBalance() - pointValue);
      executor.addToPersonalPointBalance(pointValue);

    }
    else
    {
      throw new IllegalStateException(
          "Task cannot be completed. Either it is not active or the buyer does not have enough points.");
    }
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

}

