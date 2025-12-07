package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Task implements Serializable
{
  private static int nextId = 1;
  private int ID;
  private String title;
  private String description;
  private int pointValue;
  private TaskStatus status;
  private LocalDate created;
  private LocalDate completed;
  private Resident completedBy;
  private boolean isAnonymous;

  public static int getNextId()
  {
    int oldId = Task.nextId;
    nextId++;
    return oldId;
  }

  public static void setNextId(int nextId)
  {
    Task.nextId = nextId;
  }

  public TaskStatus getStatus()
  {
    return status;
  }

  public int getID()
  {
    return ID;
  }

}
