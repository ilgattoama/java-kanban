package manager;

import task.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

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
            for (Task task : getAllTasks()) writer.write(toString(task) + "\n");
            for (Epic epic : getAllEpics()) writer.write(toString(epic) + "\n");
            for (Subtask sub : getAllSubtasks()) writer.write(toString(sub) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении в файл: " + file.getName(), e);
        }
    }

    private String toString(Task task) {
        String epicId = "";
        if (task instanceof Subtask sub) {
            epicId = String.valueOf(sub.getEpic().getId());
        }
        return String.join(",",
                String.valueOf(task.getId()),
                task.getType().name(),
                task.getName(),
                task.getStatus().name(),
                task.getDescription(),
                epicId
        );
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            Map<Integer, Epic> epicsMap = new HashMap<>();
            
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] fields = line.split(",");
                TaskType type = TaskType.valueOf(fields[1]);
                if (type == TaskType.EPIC) {
                    Epic epic = new Epic(Integer.parseInt(fields[0]), fields[2], fields[4]);
                    manager.addEpic(epic);
                    epicsMap.put(epic.getId(), epic);
                }
            }

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                Task task = fromString(line, epicsMap);
                if (task != null) {
                    if (task.getType() == TaskType.TASK) manager.addTask(task);
                    else if (task.getType() == TaskType.SUBTASK) manager.addSubtask((Subtask) task);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла: " + file.getName(), e);
        }
        return manager;
    }

    private static Task fromString(String value, Map<Integer, Epic> epicsMap) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String
