package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
    }

    @Test
    void createAndRetrieveTasksById() {
        Task task = new Task(0, "Task", "Desc", Status.NEW);
        manager.createTask(task);

        Epic epic = new Epic(0, "Epic", "Desc");
        manager.createEpic(epic);

        Subtask sub = new Subtask(0, "Sub", "Desc", Status.NEW, epic.getId());
        manager.createSubtask(sub);

        assertEquals(task, manager.getTaskById(task.getId()));
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(sub, manager.getSubtaskById(sub.getId()));
    }

    @Test
    void taskIdGenerationShouldNotConflictWithManualId() {
        Task t1 = new Task(999, "Manual", "Desc", Status.NEW);
        manager.createTask(t1);

        Task t2 = new Task(0, "Generated", "Desc", Status.NEW);
        manager.createTask(t2);

        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void addedTaskShouldRemainUnchanged() {
        Task task = new Task(0, "Immutable", "Test", Status.NEW);
        manager.createTask(task);

        assertEquals("Immutable", task.getName());
        assertEquals("Test", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    void subtaskShouldNotHaveSameIdAsEpic() {
        Epic epic = new Epic(0, "Epic", "Desc");
        manager.createEpic(epic);

        Subtask subtask = new Subtask(0, "Sub", "Desc", Status.NEW, epic.getId());
        manager.createSubtask(subtask);

        assertNotEquals(subtask.getId(), epic.getId());
    }
}
