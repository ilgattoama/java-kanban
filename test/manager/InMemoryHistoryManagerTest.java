package test.manager;

import manager.HistoryManager;
import manager.Managers;
import task.Task;
import task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void add() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "Задача", "Описание", Status.NEW);
        manager.add(task);
        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void remove() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "Задача", "Описание", Status.NEW);
        manager.add(task);
        manager.remove(0);
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void updateTask() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task1 = new Task(0, "Задача", "Описание", Status.NEW);
        Task task2 = new Task(0, "Обновленная", "Описание", Status.DONE);
        manager.add(task1);
        manager.add(task2);
        assertEquals(1, manager.getHistory().size());
        assertEquals("Обновленная", manager.getHistory().get(0).getName());
    }

    @Test
    void addDuplicate() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "Задача", "Описание", Status.NEW);
        manager.add(task);
        manager.add(task);
        assertEquals(1, manager.getHistory().size());
    }
}