package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();

    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();

    Task getTaskById(int id);
    Epic getEpicById(int id);
    Subtask getSubtaskById(int id);

    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubtask(Subtask subtask);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);

    List<Subtask> getSubtasksByEpicId(int epicId);
    List<Task> getHistory();
}
