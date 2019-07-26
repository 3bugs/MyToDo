package th.ac.dusit.dbizcom.mytodo.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

public class TaskRepository {

    private Context mContext;

    public TaskRepository(Context context) {
        this.mContext = context;
    }

    public void getTasks(Callback callback) {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                return AppDatabase
                        .getInstance(mContext)
                        .taskDao()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<Task> taskList) {
                super.onPostExecute(taskList);
                callback.onGetTasks(taskList);
            }
        }

        new GetTasks().execute();
    }

    public void addTask(String title, String desc, Date dueDate, Callback callback) {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setTitle(title);
                task.setDesc(desc);
                task.setDueDate(dueDate);

                AppDatabase
                        .getInstance(mContext)
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.onAddTask();
            }
        }

        new SaveTask().execute();
    }

    public void updateTask(int id, String title, String desc, boolean finished, Date dueDate, Callback callback) {
        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setId(id);
                task.setTitle(title);
                task.setDesc(desc);
                task.setFinished(finished);
                task.setDueDate(dueDate);

                AppDatabase
                        .getInstance(mContext)
                        .taskDao()
                        .update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.onUpdateTask();
            }
        }

        new UpdateTask().execute();
    }

    public interface Callback {
        void onGetTasks(List<Task> taskList);

        void onAddTask();

        void onUpdateTask();
    }
}
