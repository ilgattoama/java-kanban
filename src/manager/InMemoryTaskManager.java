package manager;

import task.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int idCounter = 1;

    private int generateId() {
        while (tasks.containsKey(idCounter) || epics.containsKey(idCounter) || subtasks.containsKey(idCounter)) {
            idCounter++;
        }
        return idCounter++;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.recalculateStatus();
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        Task created = new Task(generateId(), task.getName(), task.getDescription(), task.getStatus());
        tasks.put(created.getId(), created);
        return created;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic created = new Epic(generateId(), epic.getName(), epic.getDescription());
        epics.put(created.getId(), created);
        return created;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Epic epic = subtask.getEpic();
        if (epic == null || !epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Epic does not exist");
        }
        Subtask created = new Subtask(generateId(), subtask.getName(), subtask.getDescription(), subtask.getStatus(), epics.get(epic.getId()));
        subtasks.put(created.getId(), created);
        epics.get(epic.getId()).addSubtask(created);
        epics.get(epic.getId()).recalculateStatus();
        return created;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epic.recalculateStatus();
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = subtask.getEpic();
            if (epic != null) {
                epic.getSubtasks().removeIf(s -> s.getId() == subtask.getId());
                epic.addSubtask(subtask);
                epic.recalculateStatus();
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
                historyManager.remove(subtask.getId());
            }
        }
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = subtask.getEpic();
            epic.getSubtasks().removeIf(s -> s.getId() == id);
            epic.recalculateStatus();
        }
        historyManager.remove(id);
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        return epic != null ? new ArrayList<>(epic.getSubtasks()) : Collections.emptyList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
