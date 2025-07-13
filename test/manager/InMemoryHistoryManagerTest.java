package manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task testTask_1;
    private Task testTask_2;
    private Task testTask_3;

    @BeforeEach
    void testCreateObjectAndTask() {
        historyManager = Managers.getDefaultHistory();
        testTask_1 = new Task("nameTaskTest_1", "descriptionTaskTest_1", TaskStatus.NEW);
        testTask_1.setId(1);
        testTask_2 = new Task("nameTaskTest_2", "descriptionTaskTest_2", TaskStatus.IN_PROGRESS);
        testTask_2.setId(2);
        testTask_3 = new Task("nameTaskTest_3", "descriptionTaskTest_3", TaskStatus.DONE);
        testTask_3.setId(3);
    }

    @Test
    void shouldAddTaskToHistory() {
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_2);
        List<Task> history = historyManager.getHistory();

        assertFalse(history.isEmpty(), "История не должна быть пустой после добавления задачи");
        assertEquals(2, history.size(), "История должна содержать 2 задачи");
        assertEquals(testTask_1, history.get(0), "Добавленная задача должна совпадать с задачей в истории");
    }

    @Test
    void shouldAddTasksToHistory() {
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_2);
        historyManager.addTaskinHistory(testTask_3);

        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(testTask_1, history.get(0));
        assertEquals(testTask_2, history.get(1));
        assertEquals(testTask_3, history.get(2));
    }

    @Test
    void shouldRemoveDuplicatesWhenTaskAddedAgain() {
        // Добавляем задачи в историю
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_2);
        historyManager.addTaskinHistory(testTask_3);

        // Добавляем testTask_2 снова - он должен переместиться в конец
        historyManager.addTaskinHistory(testTask_2);

        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(testTask_1, history.get(0));
        assertEquals(testTask_3, history.get(1));
        assertEquals(testTask_2, history.get(2));

        // Добавляем testTask_1 снова - он должен переместиться в конец
        historyManager.addTaskinHistory(testTask_1);
        history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(testTask_3, history.get(0));
        assertEquals(testTask_2, history.get(1));
        assertEquals(testTask_1, history.get(2));
    }

    @Test
    void shouldRemoveTaskFromHistory() {
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_2);
        historyManager.addTaskinHistory(testTask_3);

        // Удаляем testTask_2
        historyManager.remove(testTask_2);

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size());
        assertFalse(history.contains(testTask_2));
        assertTrue(history.contains(testTask_1));
        assertTrue(history.contains(testTask_3));
    }

    @Test
    void shouldHandleEmptyHistory() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty());
    }

    @Test
    void shouldNotContainDuplicates() {
        // Добавляем tasteTask_1 несколько раз
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_1);

        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(testTask_1, history.get(0));
    }

    @Test
    void shouldMaintainInsertionOrderWithoutDuplicates() {
        // Добавляем задачи в разном порядке с дублированием
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_2);
        historyManager.addTaskinHistory(testTask_1);
        historyManager.addTaskinHistory(testTask_3);
        historyManager.addTaskinHistory(testTask_2);

        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(testTask_1, history.get(0));
        assertEquals(testTask_3, history.get(1));
        assertEquals(testTask_2, history.get(2));
    }
}