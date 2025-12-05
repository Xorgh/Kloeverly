package kloeverly.domain;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class Resident
{
  private static int lastId =0;
  private int ID;
  private String name;
  private int personalPointBalance;
  private int reservedBalance;
  private boolean pointBoosted;
  private ArrayList<Task> assignedTasks;
  private ArrayList<Task> ownedTasks;

  public Resident(int lastId, int ID, String name, int personalPointBalance,
      int reservedBalance, boolean pointBoosted, ArrayList<Task> assignedTasks,
      ArrayList<Task> ownedTasks)
  {
    this.lastId = ++lastId;
    this.ID = ID;
    this.name = name;
    this.personalPointBalance = personalPointBalance;
    this.reservedBalance = reservedBalance;
    this.pointBoosted = pointBoosted;
    this.assignedTasks = assignedTasks;
    this.ownedTasks = ownedTasks;
  }

  public static int getLastId()
  {
    return lastId;
  }

  public static void setLastId(int lastId)
  {
    Resident.lastId = lastId;
  }
}
