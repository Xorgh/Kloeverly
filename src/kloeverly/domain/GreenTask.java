package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class GreenTask extends Task implements Serializable
{
  private final int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private LocalDate completed;
  private Resident completedBy;
  private Boolean isAnonymous = true;

  public GreenTask(String title, String description, int pointValue, Resident completedBy)
  {
    ID = Task.getNextId();
    this.title = title;
    this.description = description;
    this.pointValue = pointValue;
    status = TaskStatus.ACTIVE;
    created = LocalDate.now();    
    this.completedBy = completedBy;
  }

  public void completeTask(Community community)
  {
    status = TaskStatus.COMPLETED;
    completed = LocalDate.now();
    if (community != null)
    {
      community.addGreenPoints(pointValue);
    }
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

  public Boolean getAnonymous()
  {
    return isAnonymous;
  }
}
