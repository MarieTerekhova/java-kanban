package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Integer> subtasksList;

    public Epic(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        this.subtasksList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksList() {
        return new ArrayList<>(subtasksList);
    }

    public void addSubtask(int subtaskId) {
        if (!subtasksList.contains(subtaskId)) {
            subtasksList.add(subtaskId);
            {
            }
        }
    }

    public void removeSubtask(int subtaskId) {
        subtasksList.remove((Integer) subtaskId);
    }

    public void clearSubtasks() {
        subtasksList.clear();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Epic epic = (Epic) object;
        return Objects.equals(getSubtasksList(), epic.getSubtasksList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubtasksList());
    }

    @Override
    public String toString() {
        return "Epic: {" +
                "id: " + getId() + ", название: " + "'" + getNameTask() + "'"
                + ", описание: " + "'" + getDescriptionTask() + "'"
                + ", статус: " + "'" + getTaskStatus() + "'"
                + ", subtask: " + subtasksList + "}";
    }


}