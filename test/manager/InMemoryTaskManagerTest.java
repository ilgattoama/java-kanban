package test.manager;

import manager.TaskManager;
import manager.Managers;
import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void addAndFindTasks() {
        TaskManager manager = Managers.getDefault();

        Task task = new Task(0, "Задача", "Описание", Status.NEW);
        manager.createTask(task);

        Epic epic = new Epic(0, "Эпик", "Описание");
        manager.createEpic(epic);

        Subtask sub = new Subtask(0, "Подзадача", "Описание", Status.NEW, epic.getId());
        manager.createSubtask(sub);

        assertEquals(task, manager.getTask(task.getId()));
        assertEquals(epic, manager.getEpic(epic.getId()));
        assertEquals(sub, manager.getSubtask(sub.getId()));
    }

    @Test
    void historySizeLimited() {
        TaskManager manager = Managers.getDefault();

        for (int i = 0; i < 15; i++) {
            Task task = new Task(i, "Задача " + i, "", Status.NEW);
            manager.createTask(task);
            manager.getTask(task.getId());
        }

        assertEquals(10, manager.getHistory().size(), "История должна ограничиваться 10 элементами");
    }
}