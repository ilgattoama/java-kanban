package manager;

import task.*;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();
    List<Epic> getEpics();
    List<Subtask> getSubtasks();

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
