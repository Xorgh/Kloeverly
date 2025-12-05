package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Task implements Serializable
{
  private static int lastId = 0;
  private int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private LocalDate completed;
  private Resident completedBy;
  private boolean isAnonymous;

  public static int getLastId()
  {
    return lastId;
  }

  public static void setLastId()
  {
    Task.lastId ++;
  }

  public TaskStatus getStatus()
  {
    return status;
  }
}
