package manager;

import org.junit.jupiter.api.Test;
import task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

   @Test
    void addShouldPreservePreviousVersionOfTask() {
       HistoryManager historyManager = new InMemoryHistoryManager();

       Task task = new Task(1, "Test", "Desc", Status.NEW);
       historyManager.add(task);

       List<Task> history = historyManager.getHistory();
       assertEquals(1, history.size());
       assertEquals(task, history.get(0));
   }
}
