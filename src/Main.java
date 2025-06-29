import manager.TaskManager;
import manager.Managers;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        System.out.println("Создаем задачи:");
        Task task1 = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(0, "Task 2", "Description 2", Status.NEW);
        manager.createTask(task1);
        manager.createTask(task2);
        System.out.println("Созданы задачи с ID: " + task1.getId() + ", " + task2.getId());

        System.out.println("Создаем эпик с подзадачами:");
        Epic epic1 = new Epic(0, "Epic 1", "Epic description");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask(0, "Subtask 1", "Desc 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(0, "Subtask 2", "Desc 2", Status.IN_PROGRESS, epic1.getId());
        Subtask subtask3 = new Subtask(0, "Subtask 3", "Desc 3", Status.DONE, epic1.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);
        System.out.println("Создан эпик с ID: " + epic1.getId() + " и подзадачами: "
                + subtask1.getId() + ", " + subtask2.getId() + ", " + subtask3.getId());

        System.out.println("Создаем пустой эпик:");
        Epic epic2 = new Epic(0, "Epic 2", "Empty epic");
        manager.createEpic(epic2);
        System.out.println("Создан пустой эпик с ID: " + epic2.getId());

        System.out.println("Тестируем историю просмотров:");
        System.out.println("Запрошена задача 1 (ID: " + task1.getId() + ")");
        manager.getTask(task1.getId());
        printHistory(manager);

        System.out.println("Запрошен эпик 1 (ID: " + epic1.getId() + ")");
        manager.getEpic(epic1.getId());
        printHistory(manager);

        System.out.println("Запрошена подзадача 2 (ID: " + subtask2.getId() + ")");
        manager.getSubtask(subtask2.getId());
        printHistory(manager);

        System.out.println("Повторно запрошена задача 1 (ID: " + task1.getId() + ")");
        manager.getTask(task1.getId());
        printHistory(manager);

        System.out.println("Удаляем задачу 1 (ID: " + task1.getId() + ")");
        manager.deleteTask(task1.getId());
        printHistory(manager);

        System.out.println("Удаляем эпик 1 (ID: " + epic1.getId() + ") с подзадачами");
        manager.deleteEpic(epic1.getId());
        printHistory(manager);
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