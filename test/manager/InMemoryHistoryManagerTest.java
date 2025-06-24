package test.manager;

import manager.HistoryManager;
import manager.Managers;
import task.Task;
import task.Status;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void add() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task(1, "Помыть посуду", "", Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void addDuplicate() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task(1, "Задача 1", "", Status.NEW);
        Task task2 = new Task(1, "Задача 1", "", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Дубликат не добавился.");
    }

    @Test
    void remove() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task(1, "Первая", "", Status.NEW);
        Task task2 = new Task(2, "Вторая", "", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Задача не удалилась.");
        assertEquals(2, history.get(0).getId(), "Удалена не та задача.");
    }

    @Test
    void updateTask() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task(1, "Старая версия", "", Status.NEW);
        Task task2 = new Task(1, "Новая версия", "", Status.DONE);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Обновленная задача добавилась как новая.");
        assertEquals("Новая версия", history.get(0).getName(), "Задача не обновилась.");
    }
}