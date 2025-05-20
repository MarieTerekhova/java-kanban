package tasks;

public class Subtask extends Task {

private int epicId;

    public Subtask(String nameTask, String descriptionTask, int epicId) {
        super(nameTask, descriptionTask);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "\nSubtask: {" +
                "id: " + getId() + ", название: " + "'" + getNameTask() + "'"
                + ", статус: " + "'" + getTaskStatus() + "'"
                + ", описание: " + "'" + getDescriptionTask() + "'"
                + ", epicId: " + epicId + "}";
    }
}
