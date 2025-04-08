import java.util.HashMap;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int idCounter = 1;

    public Task createTask(String title, String description, Status status) {
        Task task = new Task(idCounter++, title, description, status);
        tasks.put(task.getId(), task);
        return task;
    }
    public Epic createEpic(String title, String description, Status status) {
        Epic epic = new Epic(idCounter++, title, description, status);
        epics.put(epic.getId(), epic);
        return epic;
    }
    public Subtask createSubtask(String title, String description, Status status, Epic epic) {
        Subtask subtask = new Subtask(idCounter++, title, description, status, epic);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask); // Добавляем подзадачу к эпикам
        return subtask;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }
    public void deleteEpic(int id) {
        epics.remove(id);
    }
    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public void printTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }
    public void printEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }
    public void printSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
}



