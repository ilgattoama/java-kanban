import manager.TaskManager;
import manager.Managers;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        System.out.println("Создаем задачи:");
        Task task1 = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(0, "Task 2", "Description 2", Status.NEW);
        int task1Id = manager.createTask(task1);
        int task2Id = manager.createTask(task2);
        System.out.println("Созданы задачи с ID: " + task1Id + ", " + task2Id);

        System.out.println("Создаем эпик с подзадачами:");
        Epic epic1 = new Epic(0, "Epic 1", "Epic description");
        int epic1Id = manager.createEpic(epic1);

        Subtask subtask1 = new Subtask(0, "Subtask 1", "Desc 1", Status.NEW, epic1Id);
        Subtask subtask2 = new Subtask(0, "Subtask 2", "Desc 2", Status.IN_PROGRESS, epic1Id);
        Subtask subtask3 = new Subtask(0, "Subtask 3", "Desc 3", Status.DONE, epic1Id);

        int subtask1Id = manager.createSubtask(subtask1);
        int subtask2Id = manager.createSubtask(subtask2);
        int subtask3Id = manager.createSubtask(subtask3);
        System.out.println("Создан эпик с ID: " + epic1Id + " и подзадачами: "
                + subtask1Id + ", " + subtask2Id + ", " + subtask3Id);

        System.out.println("Создаем пустой эпик:");
        Epic epic2 = new Epic(0, "Epic 2", "Empty epic");
        int epic2Id = manager.createEpic(epic2);
        System.out.println("Создан пустой эпик с ID: " + epic2Id);

        System.out.println("Тестируем историю просмотров:");
        System.out.println("Запрошена задача 1 (ID: " + task1Id + ")");
        manager.getTask(task1Id);
        printHistory(manager);

        System.out.println("Запрошен эпик 1 (ID: " + epic1Id + ")");
        manager.getEpic(epic1Id);
        printHistory(manager);

        System.out.println("Запрошена подзадача 2 (ID: " + subtask2Id + ")");
        manager.getSubtask(subtask2Id);
        printHistory(manager);

        System.out.println("Повторно запрошена задача 1 (ID: " + task1Id + ")");
        manager.getTask(task1Id);
        printHistory(manager);

        System.out.println("Удаляем задачу 1 (ID: " + task1Id + ")");
        manager.deleteTask(task1Id);
        printHistory(manager);

        System.out.println("Удаляем эпик 1 (ID: " + epic1Id + ") с подзадачами");
        manager.deleteEpic(epic1Id);
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