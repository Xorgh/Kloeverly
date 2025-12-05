package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ExchangeTask extends Task implements Serializable
{
  private int ID;
  private String title;
  private String offeredItem;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private Resident owner;
  private LocalDate completed;
  private Resident completedBy;

  public ExchangeTask(String title, String description, int pointValue,
      Resident completedBy)
  {
    this.title = title;
    this.description = description;
    this.pointValue = pointValue;
    this.completedBy = completedBy;
  }

  private void reserveBalance() {

  }

  private void releaseBalance() {

  }

  public void completeTask() {

  }

  public void deleteTask() {

  }

  public Resident getOwner()
  {
    return owner;
  }
}
