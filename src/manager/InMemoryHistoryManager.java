package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> viewsHistory;
    public InMemoryHistoryManager () {
        this.viewsHistory = new ArrayList<>();
    }

    @Override
    public void addTaskinHistory(Task task) {
        if (viewsHistory.size() > 11) { // если в списке больше 10 задач, то удаляем первую
            viewsHistory.removeFirst();
        }
        viewsHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(viewsHistory);
    }
}