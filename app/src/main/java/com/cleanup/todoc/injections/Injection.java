package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.CleanUpDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context){
        CleanUpDatabase database = CleanUpDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        CleanUpDatabase database = CleanUpDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor() {return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}