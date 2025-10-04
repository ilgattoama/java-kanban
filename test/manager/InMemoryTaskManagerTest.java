package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    @BeforeEach
    void setup() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldCreateAndRetrieveTasksById() {
        Task task = new Task(0, "Task", "Desc", Status.NEW);
        manager.addTask(task);

        Epic epic = new Epic(0, "Epic", "Desc");
        manager.addEpic(epic);

        Subtask sub = new Subtask(0, "Sub", "Desc", Status.NEW, epic);
        manager.addSubtask(sub);

        assertEquals(task, manager.getTask(task.getId()));
        assertEquals(epic, manager.getEpic(epic.getId()));
        assertEquals(sub, manager.getSubtask(sub.getId()));
    }

    @Test
    void tasksWithGivenAndGeneratedIdShouldNotConflict() {
        Task t1 = new Task(999, "Manual", "Desc", Status.NEW);
        manager.addTask(t1);

        Task t2 = new Task(0, "Generated", "Desc", Status.NEW);
        manager.addTask(t2);

        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void addingTaskShouldNotChangeItsFields() {
        Task task = new Task(0, "Immutable", "Test", Status.NEW);
        manager.addTask(task);

        assertEquals("Immutable", task.getName());
        assertEquals("Test", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    void subtaskCannotBeItsOwnEpic() {
        Epic epic = new Epic(0, "Epic", "Desc");
        manager.addEpic(epic);

        Subtask subtask = new Subtask(0, "Sub", "Desc", Status.NEW, epic);
        manager.addSubtask(subtask);

        assertNotEquals(subtask.getId(), subtask.getEpic().getId());
    }
}
