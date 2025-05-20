package tasks;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksList;

    public Epic(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        this.subtasksList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void addSubtask(int subtaskId) {
        subtasksList.add(subtaskId);
    }

    @Override
    public String toString() {
        return "\nEpic: {" +
                "id: " + getId() + ", название: " + "'" + getNameTask() + "'"
                + ", описание: " + "'" + getDescriptionTask() + "'"
                + ", статус: " + "'" + getTaskStatus() + "'"
                + ", subtask: " + subtasksList + "}";
    }
}