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
            manager.getTask(task.getId());

        assertEquals(tasksCount, manager.getHistory().size(),
                "История должна содержать все просмотренные задачи без ограничений");

        assertTrue(manager.getHistory().contains(manager.getTask(0)),
                "История должна сохранять старые задачи");
        assertTrue(manager.getHistory().contains(manager.getTask(tasksCount - 1)),
                "История должна сохранять новые задачи");
    }
}