package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

import java.util.*;

public class InMemoryTaskManager implements TaskManager
{
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int idCounter = 1;

    @Override
    public List<Task> getTasks()
    {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics()
    {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks()
    {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Map<Integer, Task> getAllTasks()
    {
        Map<Integer, Task> всеЗадачи = new HashMap<>();
        всеЗадачи.putAll(tasks);
        всеЗадачи.putAll(epics);
        всеЗадачи.putAll(subtasks);
        return всеЗадачи;
    }

    @Override
    public void addTask(Task task)
    {
        if (task.getId() == 0)
        {
            task.setId(generateId());
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic)
    {
        if (epic.getId() == 0)
        {
            epic.setId(generateId());
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask)
    {
        if (subtask.getId() == 0)
        {
            subtask.setId(generateId());
        }
        if (!epics.containsKey(subtask.getEpicId()))
        {
            throw new IllegalArgumentException("Эпик для подзадачи не найден");
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask);
    }

    @Override
    public Task getTask(int id)
    {
        Task task = tasks.get(id);
        if (task != null)
        {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id)
    {
        Epic epic = epics.get(id);
        if (epic != null)
        {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id)
    {
        Subtask subtask = subtasks.get(id);
        if (subtask != null)
        {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void updateTask(Task task)
    {
        if (!tasks.containsKey(task.getId()))
        {
            throw new IllegalArgumentException("Задача не найдена");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic)
    {
        if (!epics.containsKey(epic.getId()))
        {
            throw new IllegalArgumentException("Эпик не найден");
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask)
    {
        if (!subtasks.containsKey(subtask.getId()))
        {
            throw new IllegalArgumentException("Подзадача не найдена");
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.updateSubtask(subtask);
    }

    @Override
    public void removeTask(int id)
    {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpic(int id)
    {
        Epic epic = epics.remove(id);
        if (epic != null)
        {
            for (Subtask subtask : epic.getSubtasks())
            {
                subtasks.remove(subtask.getId());
                historyManager.remove(subtask.getId());
            }
            historyManager.remove(id);
        }
    }

    @Override
    public void removeSubtask(int id)
    {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null)
        {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null)
            {
                epic.removeSubtask(id);
            }
            historyManager.remove(id);
        }
    }

    @Override
    public void clearTasks()
    {
        for (Integer id : tasks.keySet())
        {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void clearEpics()
    {
        for (Epic epic : epics.values())
        {
            for (Subtask subtask : epic.getSubtasks())
            {
                historyManager.remove(subtask.getId());
                subtasks.remove(subtask.getId());
            }
            historyManager.remove(epic.getId());
        }
        epics.clear();
    }

    @Override
    public void clearSubtasks()
    {
        for (Integer id : subtasks.keySet())
        {
            historyManager.remove(id);
        }
        subtasks.clear();
        for (Epic epic : epics.values())
        {
            epic.clearSubtasks();
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int epicId)
    {
        Epic epic = epics.get(epicId);
        if (epic != null)
        {
            return epic.getSubtasks();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Task> getPrioritizedTasks()
    {
        List<Task> всеЗадачи = new ArrayList<>();
        всеЗадачи.addAll(tasks.values());
        всеЗадачи.addAll(epics.values());
        всеЗадачи.addAll(subtasks.values());
        всеЗадачи.sort(Comparator.comparing(Task::getStartTime,
                Comparator.nullsLast(Comparator.naturalOrder())));
        return всеЗадачи;
    }

    @Override
    public List<Task> getHistory()
    {
        return historyManager.getHistory();
    }

    private int generateId()
    {
        return idCounter++;
    }
}
