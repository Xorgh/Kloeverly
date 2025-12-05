package kloeverly.domain;

import java.io.Serializable;

public class TaskTemplate implements Serializable
{
  private String title;
  private String description;
  private int pointsValue;

  public TaskTemplate(String title, String description, int pointsValue)
  {
    this.title = title;
    this.description = description;
    this.pointsValue = pointsValue;
  }
}