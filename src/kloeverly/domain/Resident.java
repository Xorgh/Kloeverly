package kloeverly.domain;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class Resident
{
  private static int lastId = 0;
  private final int ID;
  private String name;
  private int personalPointBalance;
  private int reservedBalance;
  private boolean pointBoosted;
  private ArrayList<Task> assignedTasks;
  private ArrayList<Task> ownedTasks;

  public Resident(int id, String name)
  {
    ID = getLastId() + 1;
    setLastId();
    this.name = name;
    personalPointBalance = 0;
    reservedBalance = 0;
    pointBoosted = false;
    assignedTasks = new ArrayList<>();
    ownedTasks = new ArrayList<>();
  }

  public static int getLastId()
  {
    return lastId;
  }

  public static void setLastId()
  {
    Resident.lastId ++;
  }


}
