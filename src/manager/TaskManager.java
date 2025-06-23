package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

import java.util.List;

public interface TaskManager {

    List<Task> getTasks();
    List<Epic> getEpics();
    List<Subtask> getSubtasks();

    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);

    void createTask(Task task);
    void createEpic(Epic epic);
    void createSubtask(Subtask subtask);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);

    List<Task> getHistory();
}