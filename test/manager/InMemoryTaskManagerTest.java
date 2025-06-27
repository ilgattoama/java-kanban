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

        Task task = new Task(0, "Задача", "Описание", Status.NEW);
        manager.createTask(task);

        assertEquals(task, manager.getTask(task.getId()));
    }

    @Test
    void historyShouldBeUnlimited() {
        TaskManager manager = Managers.getDefault();
        final int tasksCount = 15;

        for (int i = 0; i < tasksCount; i++) {
            Task task = new Task(i, "Задача " + i, "", Status.NEW);
            manager.createTask(task);
        }

        for (int i = 0; i < tasksCount; i++) {
            manager.getTask(i);
        }

        assertEquals(tasksCount, manager.getHistory().size(),
                "История должна содержать все просмотренные задачи без ограничений");

        assertEquals(tasksCount - 1, manager.getHistory().get(tasksCount - 1).getId(),
                "Последней в истории должна быть последняя просмотренная задача");

        assertEquals(0, manager.getHistory().get(0).getId(),
                "Первая просмотренная задача должна остаться в истории");
    }
}