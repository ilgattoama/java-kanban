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
    void historySizeLimited() {
        TaskManager manager = Managers.getDefault();

        for (int i = 0; i < 15; i++) {
            Task task = new Task(i, "Задача " + i, "", Status.NEW);
            manager.createTask(task);
            manager.getTask(task.getId()); // Добавляем в историю просмотров
        }

        assertEquals(10, manager.getHistory().size(), "История должна ограничиваться 10 элементами");

        for (Task task : manager.getHistory()) {
            assertTrue(task.getId() >= 5, "В истории должны быть только последние задачи");
        }
    }
}