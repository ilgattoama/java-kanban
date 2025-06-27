package manager;

import task.Task;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Task> historyMap = new LinkedHashMap<Integer, Task>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Task> eldest) {
            return false;
        }
    };

    @Override
    public void add(Task task) {
        if (task == null || task.getId() == null) return;

        if (historyMap.containsKey(task.getId())) {
            historyMap.remove(task.getId());
        }

        historyMap.put(task.getId(), task);
    }

    @Override
    public void remove(int id) {
        historyMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyMap.values());
    }
}