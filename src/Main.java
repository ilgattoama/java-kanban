public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Epic epic1 = taskManager.createEpic("Переезд", "Собрать вещи и упаковать для переезда", Status.NEW);
        Epic epic2 = taskManager.createEpic("Отпуск", "Подготовка к отпуску", Status.NEW);

        Subtask subtask1 = taskManager.createSubtask("Собрать коробки", "Упаковать все вещи", Status.NEW, epic1);
        Subtask subtask2 = taskManager.createSubtask("Упаковать кошку", "Поместить кошку в переноску", Status.NEW, epic1);

        Subtask subtask3 = taskManager.createSubtask("Забронировать билеты", "Купить билеты на самолет", Status.NEW, epic2);

        taskManager.printTasks();
        taskManager.printEpics();
        taskManager.printSubtasks();

        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        epic1.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);

        taskManager.printTasks();
        taskManager.printEpics();
        taskManager.printSubtasks();

        taskManager.deleteSubtask(subtask1.getId());
        taskManager.printSubtasks();
    }
}
