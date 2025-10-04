package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddAndGetTask() {
        Task task = new Task(0, "Test task", "Description", Status.NEW);
        manager.addTask(task);
        List<Task> tasks = manager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals(task.getName(), tasks.get(0).getName());
    }

    @Test
    void shouldAddAndGetEpicWithSubtask() {
        Epic epic = new Epic(0, "Epic", "Epic description", Status.NEW);
        manager.addEpic(epic);
        Subtask subtask = new Subtask(0, "Sub", "Sub desc", Status.NEW, epic.getId());
        manager.addSubtask(subtask);
        assertEquals(1, manager.getAllEpics().size());
        assertEquals(1, manager.getAllSubtasks().size());
    }
}
