package manager;

import org.w3c.dom.Node;
import tasks.Task;

import java.util.List;

public interface HistoryManager {

    void addTaskinHistory(Task task);

    void remove(Task task);

    List<Task> getHistory();

}