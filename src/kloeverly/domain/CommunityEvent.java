package kloeverly.domain;

import java.io.Serializable;

public class CommunityEvent implements Serializable
{
  private String title;
  private String description;
  private int unlockThreshold;
  private EventStatus status;

  public CommunityEvent(String title, String description, int unlockThreshold)
  {
    this.title = title;
    this.description = description;
    this.unlockThreshold = unlockThreshold;
    status = EventStatus.ACTIVE;
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
}
