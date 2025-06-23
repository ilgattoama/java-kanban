package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
    }

    @Test
    void createAndRetrieveTasksById() {
        Task task = manager.createTask(new Task(0, "Task", "Desc", Status.NEW));
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Desc"));
        Subtask sub = manager.createSubtask(new Subtask(0, "Sub", "Desc", Status.NEW, epic));

        assertEquals(task, manager.getTaskById(task.getId()));
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(sub, manager.getSubtaskById(sub.getId()));
    }

    @Test
    void preventIdConflictBetweenManualAndGenerated() {
        Task t1 = manager.createTask(new Task(999, "Manual", "Desc", Status.NEW));
        Task t2 = manager.createTask(new Task(0, "Generated", "Desc", Status.NEW));
        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void taskFieldsShouldNotChangeAfterAdding() {
        Task task = new Task(0, "Immutable", "Test", Status.NEW);
        Task added = manager.createTask(task);
        assertEquals("Immutable", added.getName());
        assertEquals("Test", added.getDescription());
        assertEquals(Status.NEW, added.getStatus());
    }

    @Test
    void subtaskShouldNotBeItsOwnEpic() {
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Desc"));
        Subtask subtask = new Subtask(0, "Sub", "Desc", Status.NEW, epic);
        assertNotEquals(subtask.getId(), subtask.getEpic().getId());
    }
}
