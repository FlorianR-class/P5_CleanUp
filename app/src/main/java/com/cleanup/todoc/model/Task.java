package com.cleanup.todoc.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Comparator;

@Entity(indices = @Index("projectId"),
        foreignKeys = @ForeignKey(entity = Project.class,
                parentColumns =  "id",
                childColumns =  "projectId"))

public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long projectId;
    @NonNull
    private String name;
    private long creationTimestamp;

    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    public long getId() {
        return id;
    }
    private void setId(long id) {
        this.id = id;
    }
    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public long getProjectId() { return projectId; }

    @Nullable
    public Project getProject() {
        return Project.getProjectById(projectId);
    }

    @NonNull
    public String getName() {
        return name;
    }

    private void setName(@NonNull String name) {
        this.name = name;
    }

    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }


    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
