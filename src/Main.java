import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация InMemoryTaskManager");
        TaskManager memoryManager = Managers.getDefault();
        demonstrateTaskManager(memoryManager, "InMemory");

        System.out.println("Демонстрация FileBackedTaskManager");
        File file = new File("tasks_data.csv");
        TaskManager fileManager = new FileBackedTaskManager(file);
        demonstrateTaskManager(fileManager, "FileBacked");

        System.out.println("Демонстрация сохранения и загрузки");
        demonstrateSaveAndLoad(file);
    }

    private static void demonstrateTaskManager(TaskManager manager, String managerType) {
        System.out.println("Менеджер: " + managerType);

        Task task1 = new Task(0, "Task 1 " + managerType, "Description", Status.NEW);
        Task task2 = new Task(0, "Task 2 " + managerType, "Description", Status.DONE);

        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic = new Epic(0, "Epic " + managerType, "Epic description");
        manager.createEpic(epic);

        Subtask subtask1 = new Subtask(0, "Subtask 1", "Desc", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask(0, "Subtask 2", "Desc", Status.DONE, epic.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        System.out.println("Создано задач: " + manager.getTasks().size());
        System.out.println("Создано эпиков: " + manager.getEpics().size());
        System.out.println("Создано подзадач: " + manager.getSubtasks().size());
    }

    private static void demonstrateSaveAndLoad(File file) {
        System.out.println("Создаем и сохраняем задачи...");

        FileBackedTaskManager originalManager = new FileBackedTaskManager(file);

        Task task = new Task(0, "Сохраненная задача", "Описание", Status.IN_PROGRESS);
        originalManager.createTask(task);

        Epic epic = new Epic(0, "Сохраненный эпик", "Описание эпика");
        originalManager.createEpic(epic);

        Subtask subtask = new Subtask(0, "Сохраненная подзадача", "Описание", Status.NEW, epic.getId());
        originalManager.createSubtask(subtask);

        System.out.println("Оригинальный менеджер:");
        printManagerContents(originalManager);

        System.out.println("Загружаем из файла...");
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        System.out.println("Загруженный менеджер:");
        printManagerContents(loadedManager);

        System.out.println("Проверка целостности данных:");
        System.out.println("Задачи совпадают: " +
                originalManager.getTasks().equals(loadedManager.getTasks()));
        System.out.println("Эпики совпадают: " +
                originalManager.getEpics().equals(loadedManager.getEpics()));
        System.out.println("Подзадачи совпадают: " +
                originalManager.getSubtasks().equals(loadedManager.getSubtasks()));

        System.out.println("Содержимое файла " + file.getName() + ":");
        try {
            List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
            for (String line : lines) {
                System.out.println("  " + line);
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void printManagerContents(TaskManager manager) {
        System.out.println("  Задачи: " + manager.getTasks().size());
        for (Task task : manager.getTasks()) {
            System.out.println("    - " + task.getName() + " (ID: " + task.getId() + ")");
        }

        System.out.println("  Эпики: " + manager.getEpics().size());
        for (Epic epic : manager.getEpics()) {
            System.out.println("    - " + epic.getName() + " (ID: " + epic.getId() +
                    ", подзадач: " + epic.getSubtaskIds().size() + ")");
        }

        System.out.println("  Подзадачи: " + manager.getSubtasks().size());
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println("    - " + subtask.getName() + " (ID: " + subtask.getId() +
                    ", эпик: " + subtask.getEpicId() + ")");
        }
    }

    private static void printHistory(TaskManager manager) {
        List<Task> history = manager.getHistory();
        if (history.isEmpty()) {
            System.out.println("История пуста");
            return;
        }

        System.out.println("Текущая история (" + history.size() + " элементов):");
        for (Task task : history) {
            String type = task instanceof Epic ? "Эпик" :
                    task instanceof Subtask ? "Подзадача" : "Задача";
            System.out.println("  [" + type + "] ID: " + task.getId() +
                    ", Название: " + task.getName() +
                    ", Статус: " + task.getStatus());
        }
    }
}