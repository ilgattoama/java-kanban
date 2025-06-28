package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private final Map<Integer, Integer> taskIndexMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) return;

        remove(task.getId());

        history.add(task);
        taskIndexMap.put(task.getId(), history.size() - 1);
    }

    @Override
    public void remove(int id) {
        Integer index = taskIndexMap.get(id);
        if (index != null) {
            history.remove(index.intValue());
            taskIndexMap.remove(id);
            for (Map.Entry<Integer, Integer> entry : taskIndexMap.entrySet()) {
                if (entry.getValue() > index) {
                    taskIndexMap.put(entry.getKey(), entry.getValue() - 1);
                }
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}