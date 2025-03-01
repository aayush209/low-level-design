package taskmanagement;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {

    private String title;

    @Setter(AccessLevel.NONE)
    private final String id; // assuming the task id can't be changed

    private String description;

    private Date dueDate;

    private int priority;

    private TaskStatus status;

    @Setter(AccessLevel.NONE)
    private final User assignedUser; // assuming the assigned user can't be changed

    public Task(String title, String id, String description, Date dueDate, int priority, User assignedUser) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.assignedUser = assignedUser;
    }
}
