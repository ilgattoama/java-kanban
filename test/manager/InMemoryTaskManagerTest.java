package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    @BeforeEach
    void setup() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldCreateAndRetrieveTasksById() {
        Task task = manager.createTask(new Task(0, "Task", "Desc", Status.NEW));
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Desc"));
        Subtask sub = manager.createSubtask(new Subtask(0, "Sub", "Desc", Status.NEW, epic));

        assertEquals(task, manager.getTaskById(task.getId()));
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(sub, manager.getSubtaskById(sub.getId()));
    }

    @Test
    void tasksWithGivenAndGeneratedIdShouldNotConflict() {
        Task t1 = manager.createTask(new Task(999, "Manual", "Desc", Status.NEW));
        Task t2 = manager.createTask(new Task(0, "Generated", "Desc", Status.NEW));
        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void addingTaskShouldNotChangeItsFields() {
        Task task = new Task(0, "Immutable", "Test", Status.NEW);
        Task added = manager.createTask(task);
        assertEquals("Immutable", added.getName());
        assertEquals("Test", added.getDescription());
        assertEquals(Status.NEW, added.getStatus());
    }

    @Test
    void subtaskCannotBeItsOwnEpic() {
        Epic epic = manager.createEpic(new Epic(0, "Epic", "Desc"));
        Subtask subtask = new Subtask(0, "Sub", "Desc", Status.NEW, epic);
        assertNotEquals(subtask.getId(), subtask.getEpic().getId());
    }
}
