package kloeverly.domain;

import java.io.Serializable;

public class TaskTemplate implements Serializable
{
  private static int nextId = 0;
  private final int ID;
  private String title;
  private String description;
  private int pointsValue;

  public TaskTemplate(String title, String description, int pointsValue)
  {
    ID = TaskTemplate.getNextId();
    this.title = title;
    this.description = description;
    this.pointsValue = pointsValue;
  }

  public static int getNextId()
  {
    int oldId = TaskTemplate.nextId;
    nextId++;
    return oldId;
  }

  public static void setNextId(int nextId)
  {
    TaskTemplate.nextId = nextId;
  }

  public int getID()
  {
    return ID;
  }
}