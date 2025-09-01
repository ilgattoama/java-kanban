import manager.InMemoryTaskManager;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        Task task1 = taskManager.createTask(new Task(0, "Купить продукты", "Молоко, яйца", Status.NEW));
        Task task2 = taskManager.createTask(new Task(0, "Позвонить врачу", "Записаться на приём", Status.NEW));

        Epic epic1 = taskManager.createEpic(new Epic(0, "Переезд", "Собрать и перевезти вещи"));
        Epic epic2 = taskManager.createEpic(new Epic(0, "Отпуск", "Подготовка к отдыху"));

        Subtask sub1 = taskManager.createSubtask(new Subtask(0, "Собрать коробки", "Упаковать всё", Status.NEW, epic1));
        Subtask sub2 = taskManager.createSubtask(new Subtask(0, "Упаковать кошку", "Кошка в переноске", Status.NEW, epic1));
        Subtask sub3 = taskManager.createSubtask(new Subtask(0, "Купить билеты", "Самолёт туда-обратно", Status.NEW, epic2));

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);

        sub1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(sub1);

        sub2.setStatus(Status.DONE);
        taskManager.updateSubtask(sub2);

        epic1.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);

        System.out.println("Обновлённые Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Обновлённые Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("Обновлённые Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        taskManager.deleteSubtask(sub1.getId());
        taskManager.deleteEpic(epic2.getId());

        System.out.println("После удаления подзадачи и эпика:");

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }
}