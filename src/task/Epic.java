package task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public List<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }
    public void addSubtask(int id) { subtaskIds.add(id); }
    public void removeSubtask(int id) { subtaskIds.remove((Integer) id); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        Epic epic = (Epic) o;
        return getId() == epic.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
