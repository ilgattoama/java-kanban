package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final Map<Integer, Task> historyMap = new LinkedHashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        historyMap.remove(task.getId());
        historyMap.put(task.getId(), task);
        if (historyMap.size() > HISTORY_LIMIT) {
            Integer firstKey = historyMap.keySet().iterator().next();
            historyMap.remove(firstKey);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyMap.values());
    }

    @Override
    public void remove(int id) {
        historyMap.remove(id);
    }
}
