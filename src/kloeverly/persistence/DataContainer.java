package kloeverly.persistence;

import kloeverly.domain.*;

import java.io.Serializable;
import java.util.List;

public class DataContainer implements Serializable
{
  private final Community community;

  public DataContainer(String communityName)
  {
    this.community =  new Community(communityName);
  }

  public DataContainer(Community community)
  {
    this.community =  community;
  }

  public Community getCommunity()
  {
    return community;
  }

}
