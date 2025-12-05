package kloeverly.domain;

import java.time.LocalDate;

public class ExchangeTask
{
  private int ID;
  private String title;
  private String offeredItem:
  private String description;
  private int pointValue;
  private TaskStatus status;
  private boolean isAnonymous;
  private LocalDate created;
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
}
