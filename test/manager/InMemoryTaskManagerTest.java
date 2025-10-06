package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private InMemoryTaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddAndGetTask() {
        Task task = new Task(0, "Test Task", "Description", Status.NEW);
        manager.addTask(task);

        List<Task> tasks = manager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getName());

        Task fetched = manager.getTask(task.getId());
        assertNotNull(fetched);
        assertEquals(task, fetched);
    }

    @Test
    void shouldAddEpicAndSubtasksAndUpdateStatus() {
        Epic epic = new Epic(0, "Epic", "Epic description");
        manager.addEpic(epic);

        Subtask sub1 = new Subtask(0, "Sub1", "Sub desc 1", Status.NEW, epic);
        Subtask sub2 = new Subtask(0, "Sub2", "Sub desc 2", Status.NEW, epic);

        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        assertEquals(2, manager.getAllSubtasks().size());
        assertEquals(epic.getId(), sub1.getEpic().getId());

        Epic fetchedEpic = manager.getEpic(epic.getId());
        assertNotNull(fetchedEpic);
        assertEquals(Status.NEW, fetchedEpic.getStatus());

        sub1.setStatus(Status.DONE);
        manager.deleteSubtaskById(sub2.getId()); // удаляем вторую подзадачу
        assertEquals(1, fetchedEpic.getSubtasks().size());
    }

    @Test
    void shouldDeleteTaskById() {
        Task task = new Task(0, "Delete Test", "Description", Status.NEW);
        manager.addTask(task);
        int id = task.getId();

        manager.deleteTaskById(id);
        assertTrue(manager.getAllTasks().isEmpty());
        assertNull(manager.getTask(id));
    }

    @Test
    void shouldDeleteEpicAndItsSubtasks() {
        Epic epic = new Epic(0, "Epic", "Desc");
        manager.addEpic(epic);

        Subtask sub1 = new Subtask(0, "Sub", "Sub desc", Status.NEW, epic);
        manager.addSubtask(sub1);

        assertEquals(1, manager.getAllEpics().size());
        assertEquals(1, manager.getAllSubtasks().size());

        manager.deleteEpicById(epic.getId());

        assertTrue(manager.getAllEpics().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void shouldDeleteSubtaskByIdAndUpdateEpicStatus() {
        Epic epic = new Epic(0, "Epic", "Desc");
        manager.addEpic(epic);

        Subtask sub1 = new Subtask(0, "Sub1", "Desc1", Status.DONE, epic);
        Subtask sub2 = new Subtask(0, "Sub2", "Desc2", Status.NEW, epic);

        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        assertEquals(2, epic.getSubtasks().size());

        manager.deleteSubtaskById(sub1.getId());
        assertEquals(1, epic.getSubtasks().size());
    }

    @Test
    void shouldReturnEmptyListsWhenNoTasks() {
        assertTrue(manager.getAllTasks().isEmpty());
        assertTrue(manager.getAllEpics().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }
}
