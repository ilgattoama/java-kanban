package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskEqualityTest {

    @Test
    void tasksWithSameIdAreEqual() {
        Task task1 = new Task(1, "Task", "Desc", Status.NEW);
        Task task2 = new Task(1, "Another", "Different", Status.DONE);
        assertEquals(task1, task2);
    }

    @Test
    void epicsWithSameIdAreEqual() {
        Epic epic1 = new Epic(2, "Epic", "Desc");
        Epic epic2 = new Epic(2, "Different", "Another");
        assertEquals(epic1, epic2);
    }

    @Test
    void subtasksWithSameIdAreEqual() {
        Epic epic = new Epic(3, "Epic", "Desc");
        Subtask s1 = new Subtask(4, "Sub", "Desc", Status.NEW, epic.getId());
        Subtask s2 = new Subtask(4, "Different", "Other", Status.DONE, epic.getId());
        assertEquals(s1, s2);
    }
}
