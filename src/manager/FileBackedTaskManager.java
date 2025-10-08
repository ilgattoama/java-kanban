package manager;

import task.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : getAllTasks()) {
                writer.write(taskToString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(taskToString(epic) + "\n");
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(taskToString(subtask) + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных в файл: " + file.getName(), e);
        }
    }

    private String taskToString(Task task) {
        String epicId = "";
        if (task instanceof Subtask subtask) {
            epicId = String.valueOf(subtask.getEpicId());
        }

        return task.getId() + "," +
                task.getType() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                epicId;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.isEmpty()) continue;

                Task task = taskFromString(line);

                if (task instanceof Epic epic) {
                    manager.addEpic(epic);
                } else if (task instanceof Subtask subtask) {
                    manager.addSubtask(subtask);
                } else {
                    manager.addTask(task);
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла: " + file.getName(), e);
        }

        return manager;
    }

    private static Task taskFromString(String line) {
        String[] parts = line.split(",");

        int id = Integer.parseInt(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];

        switch (type) {
            case TASK:
                return new Task(id, name, description, status);
            case EPIC:
                return new Epic(id, name, description);
            case SUBTASK:
                int epicId = Integer.parseInt(parts[5]);
                return new Subtask(id, name, description, status, epicId);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    public static void main(String[] args) {
        File file = new File("tasks.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        Task task = new Task(1, "Сходить в магазин", "Купить хлеб", Status.NEW);
        manager.addTask(task);

        Epic epic = new Epic(2, "Переезд", "Собрать вещи");
        manager.addEpic(epic);

        Subtask subtask = new Subtask(3, "Упаковать вещи", "Коробки и скотч", Status.IN_PROGRESS, epic.getId());
        manager.addSubtask(subtask);

        System.out.println("Данные сохранены в файл: " + file.getAbsolutePath());

        FileBackedTaskManager restored = FileBackedTaskManager.loadFromFile(file);
        System.out.println("Загруженные задачи: " + restored.getAllTasks().size());
        System.out.println("Загруженные эпики: " + restored.getAllEpics().size());
        System.out.println("Загруженные подзадачи: " + restored.getAllSubtasks().size());
    }
}
