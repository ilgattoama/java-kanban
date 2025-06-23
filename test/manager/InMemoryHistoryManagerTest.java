package manager;

import org.junit.jupiter.api.*;
import tasks.Task;
import tasks.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addTaskToHistory() {
        Task task = new Task(1, "Помыть посуду", "", Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    void removeTaskFromHistory() {
        Task task1 = new Task(1, "Задача 1", "", Status.NEW);
        Task task2 = new Task(2, "Задача 2", "", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }

    @Test
    void keepInsertionOrder() {
        Task task1 = new Task(1, "Первая", "", Status.NEW);
        Task task2 = new Task(2, "Вторая", "", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    void replaceDuplicates() {
        Task task1 = new Task(1, "Старая версия", "", Status.NEW);
        Task task2 = new Task(1, "Новая версия", "", Status.DONE);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }
}
