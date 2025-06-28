package test.manager;

import manager.TaskManager;
import manager.Managers;
import task.Task;
import task.Status;
import java.util.List;
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

        Task[] tasks = new Task[tasksCount];
        for (int i = 0; i < tasksCount; i++) {
            tasks[i] = new Task(i, "Task " + i, "", Status.NEW);
            manager.createTask(tasks[i]);
        }

        for (int i = 0; i < tasksCount; i++) {
            manager.getTask(i);
        }

        List<Task> history = manager.getHistory();
        assertEquals(tasksCount, history.size(),
                "История должна содержать все " + tasksCount + " задач");

        for (int i = 0; i < tasksCount; i++) {
            assertTrue(history.contains(tasks[i]),
                    "Задача с ID " + i + " должна быть в истории");
        }
    }
}