package manager;
import tasks.Task;
import java.util.List;

public interface HistoryManager {

  void addTaskinHistory(Task task);

   List<Task> getHistory();
}