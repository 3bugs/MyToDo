package th.ac.dusit.dbizcom.mytodo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import th.ac.dusit.dbizcom.mytodo.db.Task;
import th.ac.dusit.dbizcom.mytodo.db.TaskRepository;
import th.ac.dusit.dbizcom.mytodo.etc.TimestampConverter;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mTitleEditText, mDescEditText, mDueDateEditText;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescEditText = findViewById(R.id.desc_edit_text);
        mDueDateEditText = findViewById(R.id.due_date_edit_text);

        mDueDateEditText.setOnClickListener(v -> {
            final DatePickerDialog.OnDateSetListener dateSetListener =
                    (view, year, monthOfYear, dayOfMonth) -> {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDueDateEditText();
                    };
            new DatePickerDialog(
                    AddTaskActivity.this,
                    dateSetListener,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        findViewById(R.id.save_button).setOnClickListener(v -> addTask());

        updateDueDateEditText();
    }

    private void updateDueDateEditText() {
        String formatDate = TimestampConverter.formatForUi(mCalendar.getTime());
        mDueDateEditText.setText(formatDate);
    }

    private void addTask() {
        if (validateForm()) {
            String title = mTitleEditText.getText().toString().trim();
            String desc = mDescEditText.getText().toString().trim();

            new TaskRepository(AddTaskActivity.this).addTask(
                    title, desc, mCalendar.getTime(), new TaskRepository.Callback() {
                        @Override
                        public void onGetTasks(List<Task> taskList) {
                        }

                        @Override
                        public void onAddTask() {
                            finish();
                        }

                        @Override
                        public void onUpdateTask() {
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
}
