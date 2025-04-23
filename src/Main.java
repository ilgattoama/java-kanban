public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Epic epic1 = taskManager.createEpic(new Epic(0, "Переезд", "Собрать вещи и упаковать для переезда"));
        Epic epic2 = taskManager.createEpic(new Epic(0, "Отпуск", "Подготовка к отпуску"));

        Subtask subtask1 = taskManager.createSubtask(new Subtask(0, "Собрать коробки", "Упаковать все вещи", Status.NEW, epic1));
        Subtask subtask2 = taskManager.createSubtask(new Subtask(0, "Упаковать кошку", "Поместить кошку в переноску", Status.NEW, epic1));

        Subtask subtask3 = taskManager.createSubtask(new Subtask(0, "Забронировать билеты", "Купить билеты на самолет", Status.NEW, epic2));

        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        epic1.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);

        taskManager.deleteSubtask(subtask1.getId());
    }
}
