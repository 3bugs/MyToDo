package th.ac.dusit.dbizcom.mytodo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import th.ac.dusit.dbizcom.mytodo.R;
import th.ac.dusit.dbizcom.mytodo.UpdateTaskActivity;
import th.ac.dusit.dbizcom.mytodo.db.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mContext;
    private List<Task> mTaskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.mContext = context;
        this.mTaskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task t = mTaskList.get(position);
        holder.titleTextView.setText(t.getTitle());
        holder.descTextView.setText(t.getDesc());
        holder.task = t;

        if (t.isFinished()) {
            holder.statusTextView.setText("Completed");
        } else {
            holder.statusTextView.setText("Not Completed");
        }
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Task task;
        TextView statusTextView, titleTextView, descTextView;

        TaskViewHolder(View itemView) {
            super(itemView);

            statusTextView = itemView.findViewById(R.id.status_text_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descTextView = itemView.findViewById(R.id.desc_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Task task = mTaskList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, UpdateTaskActivity.class);
            intent.putExtra("task", task);
            mContext.startActivity(intent);
        }
    }
}
