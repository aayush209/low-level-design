package taskmanagement;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskManager {
    // singleton TaskManager class
    private static volatile TaskManager instance;
    // to simulate we maintain 2 lists
    private final Map<String, Task> taskList;
    private final Map<String, List<Task>> userTasks;

    private TaskManager() {
        taskList = new ConcurrentHashMap<>();
        userTasks = new ConcurrentHashMap<>();
    }

    static TaskManager getInstance(){
        if(instance == null){
            synchronized (TaskManager.class){
                if(instance == null){
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    //create task
    public void createTask(Task task){
        taskList.put(task.getId(), task);
        assignTaskToUser(task.getAssignedUser(), task);
    }

    //update tasks
    public void updateTask(Task task2) {

    }

    //delete tasks
    public void deleteTask(String taskId) {
        Task task = taskList.remove(taskId);
        if(task != null){
            unassignTaskFromUser(task.getAssignedUser(), task);
        }
    }

    //search tasks
    public List<Task> searchTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : taskList.values()){
            if(task.getTitle().contains(keyword) || task.getDescription().contains(keyword)){
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    //filter tasks
    public List<Task> filterTasks(TaskStatus status, Date startDueDate, Date endDueDate, int priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for(Task task : taskList.values()){
            if(task.getStatus() == status &&
            task.getDueDate().compareTo(startDueDate) >=0 &&
                    task.getDueDate().compareTo(endDueDate) <= 0 &&
                    task.getPriority() == priority){
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    //mark task as completed
    public void markTaskAsCompleted(String taskId) {
        Task task = taskList.get(taskId);
        if(task != null){
            synchronized (task){
                task.setStatus(TaskStatus.COMPLETED);
            }
        }

    }
    //get user task history
    public List<Task> getTaskHistory(User user) {
        return new ArrayList<>(userTasks.getOrDefault(user.getId(), new ArrayList<>()));
    }

    //assign task to a user
    private void assignTaskToUser(User user, Task task) {
        //userTasks.computeIfAbsent(user.getId(), k -> new CopyOnWriteArrayList<>()).add(task);
        List<Task> tasks = userTasks.get(user.getId());

        // Check if the user has no tasks assigned yet
        if (tasks == null) {
            tasks = new CopyOnWriteArrayList<>();
            userTasks.put(user.getId(), tasks);
        }
        // Add the new task to the user's list of tasks
        tasks.add(task);
        log.info("Task {} assigned to user {} ", task, user.getName());
    }

    //unassign task from a user
    private void unassignTaskFromUser(User user, Task task){
        List<Task> tasks = userTasks.get(user.getId());
        if(tasks != null){
            tasks.remove(task);
        }
    }
}
