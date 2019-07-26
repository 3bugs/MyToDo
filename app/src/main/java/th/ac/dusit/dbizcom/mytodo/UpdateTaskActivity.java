package th.ac.dusit.dbizcom.mytodo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import th.ac.dusit.dbizcom.mytodo.db.AppDatabase;
import th.ac.dusit.dbizcom.mytodo.db.Task;
import th.ac.dusit.dbizcom.mytodo.db.TaskRepository;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText mTitleEditText, mDescEditText;
    private CheckBox mFinishedCheckBox;

    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescEditText = findViewById(R.id.desc_edit_text);
        mFinishedCheckBox = findViewById(R.id.finish_check_box);

        mTask = (Task) getIntent().getSerializableExtra("task");
        loadTask();

        findViewById(R.id.save_button).setOnClickListener(v -> {
            updateTask();
        });

        findViewById(R.id.delete_button).setOnClickListener(view -> new AlertDialog.Builder(UpdateTaskActivity.this)
                .setTitle("Are you sure?")
                .setPositiveButton("Yes", (dialogInterface, i) -> deleteTask())
                .setNegativeButton("No", (dialogInterface, i) -> {
                })
                .show());
    }

    private void loadTask() {
        mTitleEditText.setText(mTask.getTitle());
        mDescEditText.setText(mTask.getDesc());
        mFinishedCheckBox.setChecked(mTask.isFinished());
    }

    private void updateTask() {
        if (validateForm()) {
            final String title = mTitleEditText.getText().toString().trim();
            final String desc = mDescEditText.getText().toString().trim();
            final boolean finished = mFinishedCheckBox.isChecked();

            new TaskRepository(UpdateTaskActivity.this).updateTask(
                    mTask.getId(),
                    title,
                    desc,
                    finished,
                    Calendar.getInstance().getTime(),
                    new TaskRepository.Callback() {
                        @Override
                        public void onAddTask() {
                        }

                        @Override
                        public void onGetTasks(List<Task> taskList) {
                        }

                        @Override
                        public void onUpdateTask() {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
            );
        } else {
            Toast.makeText(getApplicationContext(), "กรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String title = mTitleEditText.getText().toString().trim();
        String desc = mDescEditText.getText().toString().trim();

        if (title.isEmpty()) {
            mTitleEditText.setError("กรอกชื่อ");
            mTitleEditText.requestFocus();
            valid = false;
        }
        if (desc.isEmpty()) {
            mTitleEditText.setError("กรอกรายละเอียด");
            mTitleEditText.requestFocus();
            valid = false;
        }
        return valid;
    }

    private void deleteTask() {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase
                        .getInstance(getApplicationContext())
                        .taskDao()
                        .delete(mTask);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        new DeleteTask().execute();
    }
}
