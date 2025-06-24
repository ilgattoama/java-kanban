package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private final Map<Integer, Task> taskMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        remove(task.getId());
        history.add(task);
        taskMap.put(task.getId(), task);
    }

    @Override
    public void remove(int id) {
        Task task = taskMap.remove(id);
        if (task != null) {
            history.remove(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
