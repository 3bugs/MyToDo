package th.ac.dusit.dbizcom.mytodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import th.ac.dusit.dbizcom.mytodo.adapter.TaskAdapter;
import th.ac.dusit.dbizcom.mytodo.db.Task;
import th.ac.dusit.dbizcom.mytodo.db.TaskRepository;

//https://www.simplifiedcoding.net/android-room-database-example/
//https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTasks();
    }

    private void getTasks() {
        new TaskRepository(MainActivity.this).getTasks(new TaskRepository.Callback() {
            @Override
            public void onGetTasks(List<Task> taskList) {
                TaskAdapter adapter = new TaskAdapter(MainActivity.this, taskList);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onAddTask() {
            }

            @Override
            public void onUpdateTask() {
            }
        });
    }
}
