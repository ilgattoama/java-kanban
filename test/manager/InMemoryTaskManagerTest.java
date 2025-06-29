package test.manager;

import manager.TaskManager;
import manager.Managers;
import task.Task;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    @Test
    void historyShouldBeUnlimited() {
        TaskManager manager = Managers.getDefault();
        final int tasksCount = 15;

        for (int i = 1; i <= tasksCount; i++) {
            Task task = new Task(0, "Task " + i, "", Status.NEW);
            manager.createTask(task);
        }

        for (int i = 1; i <= tasksCount; i++) {
            manager.getTask(i);
        }

        List<Task> history = manager.getHistory();
        assertEquals(tasksCount, history.size(),
                "История должна содержать все " + tasksCount + " задач");
        
        for (int i = 1; i <= tasksCount; i++) {
            final int taskId = i;
            assertTrue(history.stream().anyMatch(t -> t.getId() == taskId),
                    "Задача с ID " + taskId + " должна быть в истории");
        }
    }

    @Test
    void addAndFindTasks() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task(0, "Task", "Description", Status.NEW);
        manager.createTask(task);
        assertEquals(task, manager.getTask(task.getId()));
    }
}