package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final LinkedList<Task> history = new LinkedList<>();
    private final Set<Integer> historyIds = new HashSet<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        remove(task.getId());
        if (history.size() >= HISTORY_LIMIT) {
            Task removed = history.removeFirst();
            historyIds.remove(removed.getId());
        }
        history.addLast(task);
        historyIds.add(task.getId());
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void remove(int id) {
        Iterator<Task> iterator = history.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                historyIds.remove(id);
                break;
            }
        }
    }
}
