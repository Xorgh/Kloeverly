package kloeverly.domain;

import java.time.LocalDate;

public class Task
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

  public static void setLastId(int lastId)
  {
    Task.lastId = lastId;
  }
}
