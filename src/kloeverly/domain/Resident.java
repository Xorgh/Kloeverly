package kloeverly.domain;

import javafx.concurrent.Task;

import java.io.Serializable;
import java.util.ArrayList;


public class Resident implements Serializable
{
  private static int nextId = 1;
  private final int ID;
  private String name;
  private int personalPointBalance;
  private int reservedBalance;
  private boolean pointBoosted;
  private ResidentStatus status;


  public Resident(String name)
  {
    ID = Resident.getNextId();
    setNextId(ID + 1);
    this.name = name;
    personalPointBalance = 0;
    reservedBalance = 0;
    pointBoosted = false;
    status = ResidentStatus.ACTIVE;

  }

  public static int getNextId()
  {
    int oldId = Resident.nextId;
    nextId++;
    return oldId;
  }

  public static void setNextId(int nextId)
  {
    Resident.nextId = nextId;
  }

  public int getID()
  {
    return ID;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getPersonalPointBalance()
  {
    return personalPointBalance;
  }

  public void setPersonalPointBalance(int personalPointBalance)
  {
    this.personalPointBalance = personalPointBalance;
  }

  public void addToPersonalPointBalance(int amount)
  {
    if (pointBoosted)
    {
      amount *= 1.25;
    }
    this.personalPointBalance += amount;
  }

  public int getReservedBalance()
  {
    return reservedBalance;
  }

  public void reserveBalance(int amount)
  {
    reservedBalance += amount;
    personalPointBalance -= amount;
  }

  public void releaseReservedBalance(int amount)
  {
    reservedBalance -= amount;
    personalPointBalance += amount;
  }

  public void setReservedBalance(int reservedBalance)
  {
    this.reservedBalance = reservedBalance;
  }

  public boolean isPointBoosted()
  {
    return pointBoosted;
  }

  public void setPointBoosted(boolean pointBoosted)
  {
    this.pointBoosted = pointBoosted;
  }

  public void setStatus(ResidentStatus newStatus)
  {
    status = newStatus;
  }


  public ResidentStatus getStatus()
  {
    return status;
  }

  @Override public String toString()
  {
    return "[" + ID + "] " + name;
  }
}
