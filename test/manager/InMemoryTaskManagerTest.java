package test.manager;

import manager.TaskManager;
import manager.Managers;
import task.Task;
import task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    @Test
    void addAndFindTasks() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task(0, "Task", "Description", Status.NEW);
        manager.createTask(task);
        assertEquals(task, manager.getTask(task.getId()));
    }

    @Test
    void historyShouldBeUnlimited() {
        TaskManager manager = Managers.getDefault();
        final int tasksCount = 15;
        
        for (int i = 0; i < tasksCount; i++) {
            Task task = new Task(i, "Task " + i, "", Status.NEW);
            manager.createTask(task);
        }

        for (int i = 0; i < tasksCount; i++) {
            manager.getTask(i);
        }

        assertEquals(tasksCount, manager.getHistory().size(),
                "История должна содержать все " + tasksCount + " задач");

        for (int i = 0; i < tasksCount; i++) {
            assertNotNull(manager.getTask(i), "Задача " + i + " не найдена");
            assertTrue(manager.getHistory().contains(manager.getTask(i)),
                    "Задача " + i + " отсутствует в истории");
        }
    }
}