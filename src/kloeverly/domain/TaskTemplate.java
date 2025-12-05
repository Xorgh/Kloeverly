package kloeverly.domain;

public class TaskTemplate
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
