package kloeverly.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class CommunityEvent implements Serializable
{
  private static int nextId = 1;
  private final int ID;
  private String title;
  private String description;
  private int unlockThreshold;
  private EventStatus status;
  private LocalDate completedDate;

  public CommunityEvent(String title, String description, int unlockThreshold)
  {
    ID = CommunityEvent.getNextId();
    this.title = title;
    this.description = description;
    this.unlockThreshold = unlockThreshold;
    status = EventStatus.ACTIVE;
  }

  public static int getNextId()
  {
    int oldId = CommunityEvent.nextId;
    nextId++;
    return oldId;
  }

  public static void setNextId(int nextId)
  {
    CommunityEvent.nextId = nextId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getUnlockThreshold()
  {
    return unlockThreshold;
  }

  public void setUnlockThreshold(int unlockThreshold)
  {
    this.unlockThreshold = unlockThreshold;
  }

  public EventStatus getStatus()
  {
    return status;
  }

  public void setStatus(EventStatus status)
  {
    this.status = status;
  }

  public int getID()
  {
    return ID;
  }

  public LocalDate getCompletedDate()
  {
    return completedDate;
  }

  public void completeEvent()
  {
    this.status = EventStatus.COMPLETED;
    this.completedDate = LocalDate.now();
  }

  public void deleteEvent()
  {
    this.status = EventStatus.CANCELLED;
    this.completedDate = LocalDate.now();
  }

  @Override public String toString()
  {
    return title + " - [" + unlockThreshold + "]";
  }
}
