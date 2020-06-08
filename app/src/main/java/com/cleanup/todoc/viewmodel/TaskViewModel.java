package com.cleanup.todoc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;
    //DATA
    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init(long projectId){
        if (this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<Project> getProject(long projectId) { return this.currentProject;  }

    // -------------
    // FOR TASK
    // -------------

//    public LiveData<List<Task>> getTasks(long projectId) { return taskDataSource.getTasks(projectId); }

    public LiveData<List<Task>> getAllTasks() { return taskDataSource.getAllTask(); }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }
}
