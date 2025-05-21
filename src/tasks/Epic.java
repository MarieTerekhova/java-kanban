package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksList;

    public Epic(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        this.subtasksList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksList() {
        //return new ArrayList<>(); -- перестал работать метод проверки статуса Эпика
        return subtasksList;
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
    public String toString() {
        return "Epic: {" +
                "id: " + getId() + ", название: " + "'" + getNameTask() + "'"
                + ", описание: " + "'" + getDescriptionTask() + "'"
                + ", статус: " + "'" + getTaskStatus() + "'"
                + ", subtask: " + subtasksList + "}";
    }
}