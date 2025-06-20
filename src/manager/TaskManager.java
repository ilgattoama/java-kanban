package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

import java.util.List;
import java.util.Map;

public interface TaskManager
{
    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    Map<Integer, Task> getAllTasks();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubtask(int id);

    void clearTasks();

    void clearEpics();

    void clearSubtasks();

    List<Subtask> getSubtasksByEpic(int epicId);

    List<Task> getPrioritizedTasks();

    List<Task> getHistory();
}
