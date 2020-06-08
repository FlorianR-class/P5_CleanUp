package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class CleanUpDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CleanUpDatabase INSTANCE;

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static CleanUpDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CleanUpDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CleanUpDatabase.class, "CleanUpDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValuesTar = new ContentValues();
                contentValuesTar.put("id", 1L);
                contentValuesTar.put("name", "Projet Tartampion");
                contentValuesTar.put("color", 0xFFEADAD1);

                ContentValues contentValuesLuc = new ContentValues();
                contentValuesLuc.put("id", 2L);
                contentValuesLuc.put("name", "Projet Lucidia");
                contentValuesLuc.put("color", 0xFFB4CDBA);

                ContentValues contentValuesCir = new ContentValues();
                contentValuesCir.put("id", 3L);
                contentValuesCir.put("name", "Projet Circus");
                contentValuesCir.put("color", 0xFFA3CED2);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesTar);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesLuc);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesCir);
                Log.i("CleanUpDatabase", "prepopulateDatabase: Projects Inserted");
            }
        };
    }
}
