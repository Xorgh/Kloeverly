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
    this.completeTask();
  }

  public void completeTask()
  {
    status = TaskStatus.COMPLETED;
    completed = LocalDate.now();
    //need to add points to community total
  }
  
}
