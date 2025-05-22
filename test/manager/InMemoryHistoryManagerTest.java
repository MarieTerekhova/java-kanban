package manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task testTask;

    @BeforeEach
    void testCreateObjectAndTask() {
        historyManager = Managers.getDefaultHistory();
        testTask = new Task("nameTaskTest", "descriptionTaskTest", TaskStatus.NEW);
        testTask.setId(1);
    }

    @Test
    void shouldAddTaskToHistory() {
        historyManager.addTaskinHistory(testTask);
        List<Task> history = historyManager.getHistory();

        assertFalse(history.isEmpty(), "История не должна быть пустой после добавления задачи");
        assertEquals(1, history.size(), "История должна содержать 1 задачу");
        assertEquals(testTask, history.get(0), "Добавленная задача должна совпадать с задачей в истории");
    }

    @Test
    void shouldPreserveTaskStateInHistory() {
        historyManager.addTaskinHistory(testTask);

        // Модифицируем задачу
        Task modifiedTask = new Task(testTask.getId(), "Modified", "Modified desc", TaskStatus.IN_PROGRESS);

        // Добавляем измененную версию
        historyManager.addTaskinHistory(modifiedTask);

        List<Task> history = historyManager.getHistory();

        // Проверяем что история сохранила оригинальное состояние
        assertEquals(2, history.size(), "История должна содержать 2 записи");
        assertEquals(testTask, history.get(0), "Первая запись должна сохранять оригинальное состояние");
        assertEquals(modifiedTask, history.get(1), "Вторая запись должна содержать измененное состояние");

        // Проверяем неизменность полей оригинальной задачи в истории
        Task savedOriginal = history.get(0);
        assertEquals("nameTaskTest", savedOriginal.getNameTask());
        assertEquals("descriptionTaskTest", savedOriginal.getDescriptionTask());
        assertEquals(TaskStatus.NEW, savedOriginal.getTaskStatus());
    }
}