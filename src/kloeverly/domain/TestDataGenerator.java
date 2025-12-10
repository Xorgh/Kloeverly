package kloeverly.domain;

public class TestDataGenerator
{
  public static void populateTestData(Community community)
  {
    Resident gb = new Resident("Grønne Bob");
    Resident dv = new Resident("Don Vegan");
    community.addResident(gb);
    community.addResident(dv);

    gb.addToPersonalPointBalance(1000);
    dv.setPointBoosted(true);
    dv.addToPersonalPointBalance(1000);

    GreenTask greenTask = new GreenTask("Affaldssortering",
        "Jeg sorterede alt mit affald i sidste uge.", 1000, gb);
    community.addTask(greenTask);
    greenTask.completeTask(community);

    community.addCommunityEvent(new CommunityEvent("Pizza Party",
        "Lad os fejre at have nået vores grønne mål med et pizza party!", 1500));

    community.addTaskTemplate(new TaskTemplate("Madlavning",
        "Lav mad til fællesspisning", 100));
    community.addTaskTemplate(new TaskTemplate("Havearbejde",
        "Slå græs på fællesarealet.", 200));

    CommunityTask task1 = new CommunityTask(community.getCommunityTaskCatalogue().getFirst());
    CommunityTask task2 = new CommunityTask(community.getCommunityTaskCatalogue().getLast());
    community.addTask(task1);
    community.addTask(task2);
    task1.assignTask(gb);
    task2.assignTask(dv);

    community.addTask(new ExchangeTask("Græsslåning",
        "Jeg slår dit græs.", 150, false, gb));
    community.addTask(new ExchangeTask("Dagligvareindkøb",
        "Jeg har brug for hjælp til at handle ind.", 100, true, dv));
  }
}