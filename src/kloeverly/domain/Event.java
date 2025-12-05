package kloeverly.domain;

public class Event
{
  private String title;
  private String description;
  private int unlockThreshold;
  private EventStatus status;

  public Event(String title, String description, int unlockThreshold,
      EventStatus status)
  {
    this.title = title;
    this.description = description;
    this.unlockThreshold = unlockThreshold;
    this.status = status;
  }

}
