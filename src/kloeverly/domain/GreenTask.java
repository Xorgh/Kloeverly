package kloeverly.domain;

import java.time.LocalDate;

public class GreenTask
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

  public GreenTask(int ID, String title, String description, int pointValue,
      TaskStatus status, boolean isAnonymous, LocalDate created,
      LocalDate completed, Resident completedBy)
  {
    this.ID = ID;
    this.title = title;
    this.description = description;
    this.pointValue = pointValue;
    this.status = status;
    this.isAnonymous = isAnonymous;
    this.created = created;
    this.completed = completed;
    this.completedBy = completedBy;
  }
}
