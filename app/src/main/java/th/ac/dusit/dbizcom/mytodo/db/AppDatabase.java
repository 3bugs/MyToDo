package th.ac.dusit.dbizcom.mytodo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "task.db";
    private static AppDatabase mInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    )
                    .build();
        }
        return mInstance;
    }

    public abstract TaskDao taskDao();
}