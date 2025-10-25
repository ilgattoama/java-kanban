package manager;

import task.*;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private int generateId() {
        return nextId++;
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = subtask.getEpic();
        if (epic != null && epics.containsKey(epic.getId())) {
            epics.get(epic.getId()).addSubtask(subtask);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask sub : new ArrayList<>(epic.getSubtasks())) {
                subtasks.remove(sub.getId());
            }
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask sub = subtasks.remove(id);
        if (sub != null && sub.getEpic() != null) {
            Epic epic = epics.get(sub.getEpic().getId());
            if (epic != null) {
                epic.removeSubtask(sub);
            }
        }
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }
}
